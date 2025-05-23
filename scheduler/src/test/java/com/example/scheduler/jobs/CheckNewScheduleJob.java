package com.example.scheduler.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import com.example.scheduler.service.jobs.CheckNewScheduleJob;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.quartz.*;
import org.slf4j.Logger;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


class CheckNewScheduleJobTest {

    private final ExerciseScheduleRepository exerciseScheduleRepository = mock(ExerciseScheduleRepository.class);
    private final Scheduler scheduler = mock(Scheduler.class);
    private final Logger logger = mock(Logger.class);
    private final CheckNewScheduleJob checkNewScheduleJob;

    public CheckNewScheduleJobTest() {
        checkNewScheduleJob = new CheckNewScheduleJob(exerciseScheduleRepository, scheduler);
        checkNewScheduleJob.setLogger(logger); // Inject mocked logger
    }

    @Test
    void execute_SchedulesNewJob() throws SchedulerException {
        // Arrange
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0));
        when(exerciseScheduleRepository.findAll()).thenReturn(List.of(schedule));
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        ArgumentCaptor<JobDetail> jobDetailCaptor = ArgumentCaptor.forClass(JobDetail.class);
        ArgumentCaptor<Trigger> triggerCaptor = ArgumentCaptor.forClass(Trigger.class);
        verify(scheduler, times(1)).scheduleJob(jobDetailCaptor.capture(), triggerCaptor.capture());

        JobDetail capturedJobDetail = jobDetailCaptor.getValue();
        Trigger capturedTrigger = triggerCaptor.getValue();

        assertThat(capturedJobDetail.getKey().getName()).isEqualTo("job_1");
        assertThat(capturedJobDetail.getJobDataMap().getLong("exerciseId")).isEqualTo(1L);
        assertThat(capturedTrigger.getKey().getName()).startsWith("trigger_1-");
    }

    @Test
    void execute_DoesNotScheduleExistingJob() throws SchedulerException {
        // Arrange
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0));
        when(exerciseScheduleRepository.findAll()).thenReturn(List.of(schedule));
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(true);

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        verify(scheduler, never()).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    void execute_HandlesSchedulerException() throws SchedulerException {
        // Arrange
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0));
        when(exerciseScheduleRepository.findAll()).thenReturn(List.of(schedule));
        when(scheduler.checkExists(any(JobKey.class))).thenThrow(new SchedulerException("Test exception"));

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        verify(logger, times(1)).error(contains("Error scheduling job for exercise ID: 1"), any(SchedulerException.class));
    }
}