package io.softwarebuilding.fusionplex.component;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Component
public class JobSchedulerCreator {

    public JobDetail createJob(
            final Class<? extends QuartzJobBean> jobClass,
            final boolean isDurable,
            final ApplicationContext context,
            final String jobName,
            final String jobGroup,
            final Map<String, ?> jobParameters) {

        final JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setName(jobName);
        factoryBean.setDurability(true);
        factoryBean.setGroup(jobGroup);

        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobName + jobGroup, jobClass.getName());
        factoryBean.setJobDataAsMap(jobDataMap);

        if (jobParameters != null) {
            factoryBean.setJobDataAsMap(jobParameters);
        }

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    public CronTrigger createCronTrigger(
            final String triggerName,
            final String triggerGroup,
            final String triggerDescription,
            final Date startTime,
            final String cronExpression,
            final int misFireInstruction ) {
        final CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName( triggerName );
        factoryBean.setGroup( triggerGroup );
        factoryBean.setDescription( triggerDescription );
        factoryBean.setStartTime( startTime );
        factoryBean.setCronExpression( cronExpression );
        factoryBean.setMisfireInstruction( misFireInstruction );
        factoryBean.setTimeZone( TimeZone.getTimeZone( "GMT" ) );

        try {
            factoryBean.afterPropertiesSet();
        } catch ( final ParseException e ) {

        }

        return factoryBean.getObject();
    }

    public SimpleTrigger createSimpleTrigger(
            final String triggerName,
            final String triggerGroup,
            final String triggerDescription,
            final Date startTime,
            final Long repeatInterval,
            final Integer repeatCount,
            final int misFireInstruction ) {
        final SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName( triggerName );
        factoryBean.setGroup( triggerGroup );
        factoryBean.setDescription( triggerDescription );
        factoryBean.setStartTime( startTime );
        factoryBean.setRepeatInterval( 0 );
        factoryBean.setRepeatCount( 0 );

        if ( repeatInterval != null ) {
            factoryBean.setRepeatInterval( repeatInterval );
        }

        if ( repeatCount != null ) {
            factoryBean.setRepeatCount( repeatCount );
        }

        factoryBean.setMisfireInstruction( misFireInstruction );
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }
}
