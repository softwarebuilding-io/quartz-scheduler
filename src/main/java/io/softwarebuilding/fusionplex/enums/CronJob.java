package io.softwarebuilding.fusionplex.enums;

public enum CronJob {

    NO(0, false, "No"),
    YES(1, true, "Yes");

    private final int id;

    private final boolean cronJob;

    private final String description;

    CronJob(final int id, final boolean cronJob, final String description) {
        this.id = id;
        this.cronJob = cronJob;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public boolean isCronJob() {
        return this.cronJob;
    }

    public String getDescription() {
        return this.description;
    }

    public static CronJob convertToCronJob(final String cronJob) {
        final boolean isCronJob = Boolean.parseBoolean(cronJob);

        if (isCronJob) {
            return CronJob.YES;
        }

        return CronJob.NO;
    }
}
