package com.example.scheduler.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.service.observer.ScheduleNotifier;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import com.example.scheduler.service.jobs.CheckNewScheduleJob;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class CheckNewScheduleJobTest {

    private final ExerciseScheduleRepository exerciseScheduleRepository = mock(ExerciseScheduleRepository.class);
    private final ScheduleNotifier scheduleNotifier = mock(ScheduleNotifier.class);
    private final CheckNewScheduleJob checkNewScheduleJob = new CheckNewScheduleJob(exerciseScheduleRepository, scheduleNotifier);

    @Test
    void execute_NotifiesObserversForNewSchedules() {
        // Arrange
        ExerciseSchedule schedule = new ExerciseSchedule(1L, 1L, 1L, LocalTime.of(10, 0));
        when(exerciseScheduleRepository.findAll()).thenReturn(List.of(schedule));

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        ArgumentCaptor<ExerciseSchedule> scheduleCaptor = ArgumentCaptor.forClass(ExerciseSchedule.class);
        verify(scheduleNotifier, times(1)).notifyObservers(scheduleCaptor.capture());
        assertThat(scheduleCaptor.getValue()).isEqualTo(schedule);
    }

    @Test
    void execute_DoesNothingWhenNoSchedulesExist() {
        // Arrange
        when(exerciseScheduleRepository.findAll()).thenReturn(List.of());

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        verify(scheduleNotifier, never()).notifyObservers(any());
    }
}