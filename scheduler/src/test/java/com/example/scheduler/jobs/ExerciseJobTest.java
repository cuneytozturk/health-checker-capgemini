package com.example.scheduler.jobs;

import com.example.scheduler.service.jobs.ExerciseJob;
import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobDataMap;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class ExerciseJobTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ExerciseJob exerciseJob = new ExerciseJob(restTemplate);

    @Test
    void executeJob_SendsNotification(){
        //arrange
        JobExecutionContext context = mock(JobExecutionContext.class);
        JobDetail jobDetail = mock(JobDetail.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("exerciseId", 1L);
        jobDataMap.put("time", "2023-10-01T10:00:00");
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);

        // act
        exerciseJob.execute(context);

        // assert
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    void executeJob_HandlesInvalidData() throws Exception {
        // Arrange
        JobExecutionContext context = mock(JobExecutionContext.class);
        JobDetail jobDetail = mock(JobDetail.class);
        JobDataMap jobDataMap = new JobDataMap();
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);

        // Act
        exerciseJob.execute(context);

        // Assert
        verify(restTemplate, never()).getForObject(anyString(), eq(String.class));
    }
}