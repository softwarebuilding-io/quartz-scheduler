package io.softwarebuilding.fusionplex.entity;

import io.softwarebuilding.fusionplex.enums.ScheduledJobs;
import jakarta.persistence.*;

import java.io.Serial;
import java.util.UUID;

@Entity
@Table(name = "scheduler_jobs")
public class SchedulerJobs extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -2714190094079700578L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "scheduled_jobs", nullable = false, columnDefinition = "smallint")
    private ScheduledJobs scheduledJobs;

    @Column(name = "job_group", length = 70, nullable = false)
    private String jobGroup;
    
    @Column(name = "cron_job", nullable = false, columnDefinition = "smallint")
    private boolean cronJob;

    public UUID getId() {
        return this.id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public ScheduledJobs getScheduledJobs() {
        return this.scheduledJobs;
    }

    public void setScheduledJobs(final ScheduledJobs scheduledJobs) {
        this.scheduledJobs = scheduledJobs;
    }

    public String getJobGroup() {
        return this.jobGroup;
    }

    public void setJobGroup(final String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public boolean isCronJob() {
        return this.cronJob;
    }

    public void setCronJob(final boolean cronJob) {
        this.cronJob = cronJob;
    }
}
