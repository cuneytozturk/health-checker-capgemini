package com.example.scheduler.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.service.observer.JobSchedulerObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobScheduleObserverTest {
    @Mock
    private Scheduler scheduler;

    private JobSchedulerObserver jobSchedulerObserver;

    @BeforeEach
    void setUp() {
        jobSchedulerObserver = new JobSchedulerObserver(scheduler);
    }


    @Test
    void onNewSchedule_SchedulesJobWhenNotExists() throws SchedulerException {
        // Arrange
        LocalDateTime createdDate = LocalDateTime.now().minusMinutes(5);
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0), createdDate);
        JobKey jobKey = new JobKey("job_" + schedule.getId());
        when(scheduler.checkExists(jobKey)).thenReturn(false);

        // Act
        jobSchedulerObserver.onNewSchedule(schedule);

        // Assert
        ArgumentCaptor<JobDetail> jobDetailCaptor = ArgumentCaptor.forClass(JobDetail.class);
        ArgumentCaptor<Trigger> triggerCaptor = ArgumentCaptor.forClass(Trigger.class);
        verify(scheduler, times(1)).scheduleJob(jobDetailCaptor.capture(), triggerCaptor.capture());

        JobDetail capturedJobDetail = jobDetailCaptor.getValue();
        Trigger capturedTrigger = triggerCaptor.getValue();

        assert capturedJobDetail.getKey().equals(jobKey);
        assert capturedJobDetail.getJobDataMap().getLong("exerciseId") == schedule.getExerciseId();
        assert capturedJobDetail.getJobDataMap().getString("time").equals(schedule.getTime().toString());
        assert capturedTrigger.getKey().getName().startsWith("trigger_" + schedule.getExerciseId());
    }

    @Test
    void onNewSchedule_DoesNotScheduleJobWhenExists() throws SchedulerException {
        // Arrange
        LocalDateTime createdDate = LocalDateTime.now().minusMinutes(5);
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0), createdDate);
        JobKey jobKey = new JobKey("job_" + schedule.getId());
        when(scheduler.checkExists(jobKey)).thenReturn(true);

        // Act
        jobSchedulerObserver.onNewSchedule(schedule);

        // Assert
        verify(scheduler, never()).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    void onNewSchedule_HandlesSchedulerException() throws SchedulerException {
        // Arrange
        LocalDateTime createdDate = LocalDateTime.now().minusMinutes(5);
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0), createdDate);
        JobKey jobKey = new JobKey("job_" + schedule.getId());
        when(scheduler.checkExists(jobKey)).thenThrow(new SchedulerException("Scheduler error"));

        // Act
        jobSchedulerObserver.onNewSchedule(schedule);

        // Assert
        verify(scheduler, never()).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }
}