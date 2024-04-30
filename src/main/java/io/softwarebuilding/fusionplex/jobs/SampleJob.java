package io.softwarebuilding.fusionplex.jobs;

import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SampleJob extends QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(SampleJob.class);

    @Override
    protected void executeInternal(@NotNull final JobExecutionContext context) throws JobExecutionException {
        LOG.info("Running sample job");


        LOG.info("Finalize sample job");
    }
}
