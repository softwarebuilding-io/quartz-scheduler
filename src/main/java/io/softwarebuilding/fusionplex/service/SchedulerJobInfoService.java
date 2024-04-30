package io.softwarebuilding.fusionplex.service;

import io.softwarebuilding.fusionplex.component.JobSchedulerCreator;
import io.softwarebuilding.fusionplex.dto.SchedulerJobInfoDto;
import io.softwarebuilding.fusionplex.entity.SchedulerJobInfo;
import io.softwarebuilding.fusionplex.enums.CronJob;
import io.softwarebuilding.fusionplex.enums.JobStatus;
import io.softwarebuilding.fusionplex.enums.ScheduledJobs;
import io.softwarebuilding.fusionplex.error.FusionPlexException;
import io.softwarebuilding.fusionplex.repository.SchedulerJobInfoRepository;
import io.softwarebuilding.fusionplex.util.FusionPlexUtil;
import org.modelmapper.ModelMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchedulerJobInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerJobInfoService.class);

    final Map<String, Object> jobParameters;

    private final SchedulerJobInfoRepository schedulerJobInfoRepository;

    private final SchedulerFactoryBean schedulerFactoryBean;

    private final ApplicationContext applicationContext;

    private final JobSchedulerCreator schedulerCreator;

    private final Scheduler scheduler;

    @Autowired
    public SchedulerJobInfoService(
            final SchedulerJobInfoRepository schedulerJobInfoRepository,
            final SchedulerFactoryBean schedulerFactoryBean,
            final ApplicationContext applicationContext,
            final JobSchedulerCreator schedulerCreator) {
        this.schedulerJobInfoRepository = schedulerJobInfoRepository;
        this.schedulerFactoryBean = schedulerFactoryBean;
        this.applicationContext = applicationContext;
        this.schedulerCreator = schedulerCreator;
        this.scheduler = schedulerFactoryBean.getScheduler();
        this.jobParameters = new HashMap<>();
    }

    public List<SchedulerJobInfoDto> findAll() {
        return this.schedulerJobInfoRepository.findAll().stream()
                .map(schedulerJobInfo -> this.getModelMapper()
                        .map(schedulerJobInfo, SchedulerJobInfoDto.class)).toList();
    }

    public SchedulerJobInfoDto findById(final UUID id) {
        return this.schedulerJobInfoRepository.findById(id)
                .map(schedulerJobInfo -> {
                    final SchedulerJobInfoDto dto = this.getModelMapper().map(schedulerJobInfo, SchedulerJobInfoDto.class);
                    dto.setCronJob(CronJob.convertToCronJob(String.valueOf(schedulerJobInfo.isCronJob())));
                    dto.setJobClass(ScheduledJobs.getClassName(schedulerJobInfo.getJobClass()));

                    return dto;
                })
                .orElseThrow(() -> new FusionPlexException("Scheduler Job Not Found"));
    }

    public void startScheduler() {
        try {

            if (!this.scheduler.isStarted()) {

                final Scheduler newScheduler = this.schedulerFactoryBean.getScheduler();
                newScheduler.start();
            }

        } catch (final SchedulerException exception) {
            LOG.error(exception.getMessage(), exception);
            throw new FusionPlexException("Error starting scheduler", exception);
        }
    }

    public void shutdownScheduler() {
        try {

            if (this.scheduler.isStarted()) {

                this.scheduler.shutdown();
            }

        } catch (final SchedulerException exception) {
            LOG.error(exception.getMessage(), exception);
            throw new FusionPlexException("Error encountered while attempting to shutdown scheduler" + exception.getMessage());
        }
    }

    public void unscheduleJob(final UUID jobId) {
        final SchedulerJobInfo schedulerJobInfo = this.findEntityById(jobId);

        try {
            schedulerJobInfo.setJobStatus(JobStatus.UNSCHEDULED);

            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }

            final TriggerKey triggerKey = this.getTriggerKey(schedulerJobInfo);
            this.scheduler.unscheduleJob(triggerKey);
            this.schedulerJobInfoRepository.save(schedulerJobInfo);

            LOG.info(">>>>> jobName = [{}]{}", schedulerJobInfo.getJobName(),
                    schedulerJobInfo.getJobStatus().getDescription());

        } catch (final SchedulerException exception) {
            throw new FusionPlexException("Error encountered while attempting to unschedule the job:" + exception.getMessage());
        }
    }

    public void pauseJob(final UUID jobId) {
        final SchedulerJobInfo schedulerJobInfo = this.findEntityById(jobId);

        try {
            schedulerJobInfo.setJobStatus(JobStatus.PAUSED);

            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }

            final JobKey jobKey = this.getJobKey(schedulerJobInfo);
            this.scheduler.pauseJob(jobKey);
            this.schedulerJobInfoRepository.save(schedulerJobInfo);

            LOG.info(">>>>> jobName = [{}]{}", schedulerJobInfo.getJobName(),
                    schedulerJobInfo.getJobStatus().getDescription());

        } catch (final SchedulerException exception) {
            throw new FusionPlexException("Error encountered while attempting to pause the job: " + exception.getMessage());
        }
    }

    public void resumeJob(final UUID jobId) {
        final SchedulerJobInfo schedulerJobInfo = this.findEntityById(jobId);

        try {
            schedulerJobInfo.setJobStatus(JobStatus.RESUMED);

            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }

            final JobKey jobKey = this.getJobKey(schedulerJobInfo);
            this.scheduler.resumeJob(jobKey);
            this.schedulerJobInfoRepository.save(schedulerJobInfo);

            LOG.info(">>>>> jobName = [{}]{}", schedulerJobInfo.getJobName(),
                    schedulerJobInfo.getJobStatus().getDescription());

        } catch (final SchedulerException exception) {
            throw new FusionPlexException("Error encountered while attempting to resume the job: " + exception.getMessage());
        }
    }

    public void deleteJob(final UUID jobId) {
        final SchedulerJobInfo schedulerJobInfo = this.findEntityById(jobId);

        try {

            if (!this.scheduler.isStarted()) {
                this.scheduler.start();
            }

            final JobKey jobKey = this.getJobKey(schedulerJobInfo);
            final TriggerKey triggerKey = this.getTriggerKey(schedulerJobInfo);

            this.scheduler.unscheduleJob(triggerKey);

            this.scheduler.deleteJob(jobKey);
            this.schedulerJobInfoRepository.delete(schedulerJobInfo);

            LOG.info(">>>>> jobName = [{}] deleted", schedulerJobInfo.getJobName());

        } catch (final SchedulerException exception) {
            throw new FusionPlexException("Error encountered while attempting to delete the job: " + exception.getMessage());
        }
    }

    public void createOrUpdateJob(
            final SchedulerJobInfoDto dto) {
        this.validateSchedulerParameters(dto);

        final SchedulerJobInfo schedulerJobInfo = this.getModelMapper().map(dto, SchedulerJobInfo.class);

        schedulerJobInfo.setJobClass(dto.getJobClass().getClazz().getName());

        try {
            this.scheduleJob(schedulerJobInfo);

        } catch (final SchedulerException exception) {
            throw new FusionPlexException("Error encountered while attempting to create a new job: " + exception.getMessage());
        }
    }

    private void scheduleJob(final SchedulerJobInfo schedulerJobInfo) throws SchedulerException {

        final SchedulerJobInfo savedSchedulerJobInfo = schedulerJobInfo.getId() != null ? this.schedulerJobInfoRepository
                .findById(schedulerJobInfo.getId()).orElse(schedulerJobInfo) : schedulerJobInfo;

        if (savedSchedulerJobInfo.getId() != null) {
            savedSchedulerJobInfo.map(schedulerJobInfo);
        }

        savedSchedulerJobInfo.verifyIfCronJob();

        final TriggerKey triggerKey = TriggerKey
                .triggerKey(savedSchedulerJobInfo.getJobName(), savedSchedulerJobInfo.getJobGroup());

        if (this.scheduler.getTriggerState(triggerKey).equals(Trigger.TriggerState.ERROR)) {
            this.scheduler.resetTriggerFromErrorState(triggerKey);
        }

        final boolean isDurable = true;

        JobDetail jobDetail = ScheduledJobs.getJobDetail(
                schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup(),
                savedSchedulerJobInfo.getDescription(), schedulerJobInfo.getJobClass(), isDurable);

        final Trigger trigger = this.createTrigger(schedulerJobInfo);

        if (!this.scheduler.checkExists(jobDetail.getKey())) {
            jobDetail = this.createJobDetail(schedulerJobInfo, isDurable);

            this.scheduler.scheduleJob(jobDetail, trigger);
            schedulerJobInfo.setJobStatus(JobStatus.SCHEDULED);
        } else {
            this.scheduler.deleteJob(jobDetail.getKey());
            this.scheduler.scheduleJob(jobDetail, trigger);
            savedSchedulerJobInfo.setJobStatus(JobStatus.RESCHEDULED);
        }

        this.schedulerJobInfoRepository.save(schedulerJobInfo);

        LOG.info(">>>>> jobName = [{}]{}", schedulerJobInfo.getJobName(),
                schedulerJobInfo.getJobStatus().getDescription());
    }

    private JobKey getJobKey(final SchedulerJobInfo schedulerJobInfo) {
        return JobKey.jobKey(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup());
    }


    private TriggerKey getTriggerKey(final SchedulerJobInfo schedulerJobInfo) {
        return TriggerKey.triggerKey(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup());
    }

    private Trigger createTrigger(final SchedulerJobInfo schedulerJobInfo) {
        if (schedulerJobInfo.isCronJob()) {
            return this.schedulerCreator.createCronTrigger(schedulerJobInfo.getJobName(),
                    schedulerJobInfo.getJobGroup(), schedulerJobInfo.getDescription(), new Date(),
                    schedulerJobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {

            return this.schedulerCreator.createSimpleTrigger(schedulerJobInfo.getJobName(),
                    schedulerJobInfo.getJobGroup(), schedulerJobInfo.getDescription(),
                    FusionPlexUtil.localTimeToDate(schedulerJobInfo.getStartTime()),
                    schedulerJobInfo.getRepeatInterval(), schedulerJobInfo.getRepeatCount(),
                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
    }

    private JobDetail createJobDetail(final SchedulerJobInfo schedulerJobInfo, final boolean isDurable) {

        return this.schedulerCreator.createJob(ScheduledJobs.getClass(schedulerJobInfo.getJobClass()),
                isDurable, this.applicationContext, schedulerJobInfo.getJobName(),
                schedulerJobInfo.getJobGroup(), this.jobParameters);
    }

    private SchedulerJobInfo findEntityById(final UUID jobId) {
        return this.schedulerJobInfoRepository.findById(jobId)
                .orElseThrow(() -> new FusionPlexException("Scheduled job Not Found!"));
    }

    private void validateSchedulerParameters(final SchedulerJobInfoDto dto) {

        if (dto.getRepeatCount() != null && dto.getRepeatCount() < 0) {
            throw new FusionPlexException("Repeat count must be greater than zero");
        }

        if (dto.getRepeatInterval() != null && dto.getRepeatInterval() < 0) {
            throw new FusionPlexException("Repeat interval must be greater than zero");
        }

        if ((dto.getRepeatCount() != null || dto.getRepeatInterval() != null || dto.getStartTime() != null)
                && (dto.getCronExpression() != null && !dto.getCronExpression().isEmpty())) {
            throw new FusionPlexException("Cron jobs cannot have repeat interval, repeat count or start time");
        }

    }

    private ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
