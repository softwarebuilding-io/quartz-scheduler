package io.softwarebuilding.fusionplex.enums;

import io.softwarebuilding.fusionplex.error.FusionPlexException;
import io.softwarebuilding.fusionplex.jobs.SampleJob;
import io.softwarebuilding.fusionplex.jobs.UpdateLatestMoviesJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

public enum ScheduledJobs {

    UPDATE_LATEST_MOVIES(1, "UpdateLatestMovies", UpdateLatestMoviesJob.class),
    SAMPLE_JOB(2, "SampleJob", SampleJob .class);

    private final Integer id;

    private final String jobJame;

    private final Class<?> clazz;

    ScheduledJobs(final Integer id, final String jobJame, final Class<?> clazz) {
        this.id = id;
        this.jobJame = jobJame;
        this.clazz = clazz;
    }

    public int getId() {
        return this.id;
    }

    public String getJobJame() {
        return this.jobJame;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public static ScheduledJobs valueOf(final Integer value) {
        for (final ScheduledJobs job : ScheduledJobs.values()) {
            if (job.getId() == value) {
                return job;
            }
        }

        return null;
    }

    public static ScheduledJobs valueOfJobName(final String jobJame ) {

        for ( final ScheduledJobs job : ScheduledJobs.values() ) {
            if ( job.getJobJame().equalsIgnoreCase( jobJame ) ) {
                return job;
            }
        }
        throw new IllegalArgumentException("Unknown job name: " + jobJame);
    }

    public static ScheduledJobs getClassName( final String jobJame ) {
        for ( final ScheduledJobs job : ScheduledJobs.values() ) {
            if ( job.getClazz().getName().equalsIgnoreCase( jobJame ) ) {
                return job;
            }
        }
        throw new IllegalArgumentException("Unknown job name: " + jobJame);
    }

    public static List<String> getJobClassName() {
        List<String> jobClassName = new ArrayList<>();

        for (final ScheduledJobs jobs : ScheduledJobs.values()) {
            jobClassName.add(jobs.getJobJame());
        }

        return jobClassName;
    }

    public static JobDetail getJobDetail(
            final String jobName, final String jobGroup, final String jobDescription,
            final String jobClass, final boolean isDurable ) {
        return JobBuilder.newJob( ScheduledJobs.getClass( jobClass ) ).withIdentity( jobName, jobGroup )
                .withDescription( jobDescription ).storeDurably( isDurable ).build();
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends QuartzJobBean> getClass(final String jobClass) {
        try {
            return (Class<? extends QuartzJobBean>) Class.forName(jobClass);
        } catch (ClassNotFoundException exception) {
            throw new FusionPlexException("Could not find QuartzJobBean class", exception);
        }
    }
}
