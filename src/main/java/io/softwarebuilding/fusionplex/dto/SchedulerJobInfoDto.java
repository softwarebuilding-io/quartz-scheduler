package io.softwarebuilding.fusionplex.dto;

import io.softwarebuilding.fusionplex.enums.CronJob;
import io.softwarebuilding.fusionplex.enums.JobStatus;
import io.softwarebuilding.fusionplex.enums.ScheduledJobs;
import io.softwarebuilding.fusionplex.error.FusionPlexException;
import io.softwarebuilding.fusionplex.validator.annotation.ValidCronExpression;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SchedulerJobInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4753075867231279773L;

    private UUID id;

    @NotEmpty(message = "Job name is required")
    private String jobName;

    @NotEmpty(message = "Job group is required")
    private String jobGroup;

    @NotEmpty(message = "Job description is required")
    private String description;

    @ValidCronExpression(message = "Invalid cron expression")
    private String cronExpression;

    @NotNull(message = "Please select a job class")
    private ScheduledJobs jobClass;

    private JobStatus jobStatus;

    private CronJob cronJob;

    private LocalTime startTime;

    private Long repeatInterval;

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

    public ScheduledJobs getJobClass() {
        return this.jobClass;
    }

    public void setJobClass(final ScheduledJobs jobClass) {
        this.jobClass = jobClass;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(final JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public CronJob getCronJob() {
        return this.cronJob;
    }

    public void setCronJob(final CronJob cronJob) {
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

    public List<String> getJobClassName() {
        return ScheduledJobs.getJobClassName();
    }
}
