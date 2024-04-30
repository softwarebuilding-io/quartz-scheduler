package io.softwarebuilding.fusionplex.enums;

public enum JobStatus {

    SCHEDULED( 1, "Scheduled" ),
    RESCHEDULED( 2, "Rescheduled" ),
    SCHEDULED_AND_STARTED( 3, "Scheduled & Started" ),
    STARTED( 4, "Started" ),
    PAUSED( 5, "Paused" ),
    RESUMED( 6, "Resumed" ),
    UNSCHEDULED( 7, "Unscheduled" ),
    FAILED( 8, "Failed" );

    private final Integer id;

    private final String description;

    JobStatus(final Integer id, final String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
