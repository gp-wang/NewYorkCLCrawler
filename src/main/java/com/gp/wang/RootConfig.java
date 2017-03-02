package com.gp.wang;

import com.gp.wang.domain.ItemRepository;
import com.gp.wang.reader.CLItemReader;
import com.gp.wang.reader.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by gwang on 10/13/2016.
 */
@Configuration
@EnableTransactionManagement
public class RootConfig {


    @Autowired
    ItemRepository itemRepository;

    @Autowired
    DataSource dataSource;





    @Bean
    public SchedulerFactoryBean readerScheduler() {
        final SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(

                clItemReaderJobDetail().getObject()
                );
        schedulerFactoryBean.setTriggers(

                clItemReaderJobTrigger().getObject()
                );
        return schedulerFactoryBean;
    }



    @Bean
    public ItemReader clItemReader() {
        return new CLItemReader(itemRepository);
    }

    @Bean
	public MethodInvokingJobDetailFactoryBean clItemReaderJobDetail() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("clItemReader");
		obj.setTargetMethod("readData");
		return obj;
	}

    @Bean
    public SimpleTriggerFactoryBean clItemReaderJobTrigger() {
        final SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(clItemReaderJobDetail().getObject());
        trigger.setRepeatInterval(180000); //longer time because it is selenium based.
        //trigger.setRepeatInterval(1000);
        return trigger;
    }



}
