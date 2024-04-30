package io.softwarebuilding.fusionplex.entity;

import io.softwarebuilding.fusionplex.enums.JobStatus;
import io.softwarebuilding.fusionplex.error.FusionPlexException;
import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "scheduler_job_info")
public class SchedulerJobInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1512929258946367969L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "job_name", length = 80, nullable = false, unique = true)
    private String jobName;

    @Column(name = "job_group", length = 80, nullable = false)
    private String jobGroup;

    @Column(name = "description", length = 120, nullable = false)
    private String description;

    @Column(name = "cron_expression", length = 40)
    private String cronExpression;

    @Column(name = "job_class", length = 80, nullable = false)
    private String jobClass;

    @Basic(optional = false)
    @Column(name = "job_status", nullable = false, columnDefinition = "smallint")
    private JobStatus jobStatus;

    @Basic(optional = false)
    @Column(name = "cron_job", nullable = false, columnDefinition = "smallint")
    private boolean cronJob;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "repeat_interval")
    private Long repeatInterval;

    @Column(name = "repeat_count")
    private Integer repeatCount;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(final String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(final String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(final String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(final String jobClass) {
        this.jobClass = jobClass;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(final JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public boolean isCronJob() {
        return cronJob;
    }

    public void setCronJob(final boolean cronJob) {
        this.cronJob = cronJob;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(final Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(final Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void map(final SchedulerJobInfo schedulerJobInfo) {
        if (this.id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (schedulerJobInfo.getJobName() != null && !schedulerJobInfo.getJobName().isEmpty()) {
            this.jobName = schedulerJobInfo.getJobName();
        }

        if (schedulerJobInfo.getJobGroup() != null && !schedulerJobInfo.getJobGroup().isEmpty()) {
            this.jobGroup = schedulerJobInfo.getJobGroup();
        }

        if (schedulerJobInfo.getDescription() != null && !schedulerJobInfo.getDescription().isEmpty()) {
            this.description = schedulerJobInfo.getDescription();
        }

        if (schedulerJobInfo.getCronExpression() != null && !schedulerJobInfo.getCronExpression().isEmpty()) {
            this.cronExpression = schedulerJobInfo.getCronExpression();
        }

        if (schedulerJobInfo.getCronExpression() != null && !schedulerJobInfo.getCronExpression().isEmpty()) {
            this.cronExpression = schedulerJobInfo.getCronExpression();
        }

        if (schedulerJobInfo.getJobClass() != null && !schedulerJobInfo.getJobClass().isEmpty()) {
            this.jobClass = schedulerJobInfo.getJobClass();
        }

        if (schedulerJobInfo.getJobStatus() != null) {
            this.jobStatus = schedulerJobInfo.getJobStatus();
        }

        if (schedulerJobInfo.isCronJob() != this.cronJob) {
            this.cronJob = schedulerJobInfo.isCronJob();
        }

        if (schedulerJobInfo.getStartTime() != null && !schedulerJobInfo.getStartTime().equals(this.startTime)) {
            this.startTime = schedulerJobInfo.getStartTime();
        }

        if (schedulerJobInfo.getRepeatInterval() != null
                && !schedulerJobInfo.getRepeatInterval().equals(this.repeatInterval)) {
            this.repeatInterval = schedulerJobInfo.getRepeatInterval();
        }

        if (schedulerJobInfo.getRepeatCount() != null
                && !schedulerJobInfo.getRepeatCount().equals(this.repeatCount)) {
            this.repeatCount = schedulerJobInfo.getRepeatCount();
        }
    }

    public void verifyIfCronJob() {
        if ( this.cronExpression != null && !this.cronExpression.isEmpty() ) {
            this.cronJob = true;
        } else {
            this.cronJob = false;
            this.cronExpression = null;
        }
    }
}
