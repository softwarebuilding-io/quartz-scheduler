package io.softwarebuilding.fusionplex.jobs;

import io.softwarebuilding.fusionplex.component.ApplicationContextProvider;
import io.softwarebuilding.fusionplex.service.ClientService;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UpdateLatestMoviesJob extends QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateLatestMoviesJob.class);

    @Override
    protected void executeInternal(@NotNull final JobExecutionContext context) throws JobExecutionException {
        LOG.info("Updating latest movies");

        final ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        final ClientService clientService = applicationContext.getBean(ClientService.class);

        clientService.updateLatestPlayingMovies();

        LOG.info("Finalize updating latest movies");
    }
}
