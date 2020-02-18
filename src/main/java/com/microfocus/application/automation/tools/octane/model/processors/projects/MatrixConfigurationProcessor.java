/*
 * Certain versions of software and/or documents ("Material") accessible here may contain branding from
 * Hewlett-Packard Company (now HP Inc.) and Hewlett Packard Enterprise Company.  As of September 1, 2017,
 * the Material is now offered by Micro Focus, a separately owned and operated company.  Any reference to the HP
 * and Hewlett Packard Enterprise/HPE marks is historical in nature, and the HP and Hewlett Packard Enterprise/HPE
 * marks are the property of their respective owners.
 * __________________________________________________________________
 * MIT License
 *
 * (c) Copyright 2012-2019 Micro Focus or one of its affiliates.
 *
 * The only warranties for products and services of Micro Focus and its affiliates
 * and licensors ("Micro Focus") are set forth in the express warranty statements
 * accompanying such products and services. Nothing herein should be construed as
 * constituting an additional warranty. Micro Focus shall not be liable for technical
 * or editorial errors or omissions contained herein.
 * The information contained herein is subject to change without notice.
 * ___________________________________________________________________
 */

package com.microfocus.application.automation.tools.octane.model.processors.projects;

import com.microfocus.application.automation.tools.octane.tests.build.BuildHandlerUtils;
import hudson.matrix.MatrixConfiguration;
import hudson.model.Job;
import hudson.tasks.Builder;

import java.util.List;
import java.util.Set;

/**
 * Implementation for discovery/provisioning of an internal phases/steps of the specific MatrixConfiguration Job in context of Matrix Plugin
 */
class MatrixConfigurationProcessor extends AbstractProjectProcessor<MatrixConfiguration> {

	MatrixConfigurationProcessor(Job project) {
		super((MatrixConfiguration) project);
	}

	@Override
	protected void buildStructureInternal(Set<Job> processedJobs) {
		//  Internal phases
		//
		super.processBuilders(this.job.getBuilders(), this.job, processedJobs);

		//  Post build phases
		//
		super.processPublishers(this.job, processedJobs);
	}

	@Override
	public List<Builder> tryGetBuilders() {
		return job.getBuilders();
	}

	@Override
	public String getTranslatedJobName() {
		if (job.getParent().getParent().getClass().getName().equals(JobProcessorFactory.FOLDER_JOB_NAME)) {
			String parentJobName = job.getParent().getFullName();    // e.g: myFolder/myJob
			return BuildHandlerUtils.translateFolderJobName(parentJobName) + "/" + job.getName();
		} else {
			return job.getFullName();
		}
	}
}
