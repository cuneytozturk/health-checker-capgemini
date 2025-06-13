package com.example.scheduler.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.service.observer.ScheduleNotifier;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import com.example.scheduler.service.jobs.CheckNewScheduleJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CheckNewScheduleJobTest {

    @Mock
    private ExerciseScheduleRepository exerciseScheduleRepository;

    @Mock
    private ScheduleNotifier scheduleNotifier;

    @InjectMocks
    private CheckNewScheduleJob checkNewScheduleJob;

    @Test
    void execute_NotifiesObserversForNewSchedules() {
        // Arrange
        ExerciseSchedule newSchedule = new ExerciseSchedule();
        newSchedule.setCreatedDate(LocalDateTime.now());
        newSchedule.setUserId(1L);

        ArgumentCaptor<LocalDateTime> timeCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        when(exerciseScheduleRepository.findByCreatedDateAfter(timeCaptor.capture())).thenReturn(List.of(newSchedule));

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        LocalDateTime capturedTime = timeCaptor.getValue();
        assertThat(capturedTime).isNotNull();
        assertThat(capturedTime).isBefore(LocalDateTime.now()); // Ensure the captured time is reasonable

        ArgumentCaptor<ExerciseSchedule> scheduleCaptor = ArgumentCaptor.forClass(ExerciseSchedule.class);
        verify(scheduleNotifier, times(1)).notifyObservers(scheduleCaptor.capture());
        assertThat(scheduleCaptor.getValue()).isEqualTo(newSchedule);
    }

    @Test
    void execute_DoesNothingWhenNoSchedulesExist() {
        // Arrange
        when(exerciseScheduleRepository.findByCreatedDateAfter(any())).thenReturn(List.of());

        // Act
        checkNewScheduleJob.execute(null);

        // Assert
        verify(scheduleNotifier, never()).notifyObservers(any());
    }
}