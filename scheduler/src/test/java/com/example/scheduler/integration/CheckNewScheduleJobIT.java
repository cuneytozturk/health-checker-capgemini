package com.example.scheduler.integration;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import com.example.scheduler.service.jobs.CheckNewScheduleJob;
import com.example.scheduler.service.observer.JobSchedulerObserver;
import com.example.scheduler.service.observer.ScheduleNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CheckNewScheduleJobIT extends BaseIntegrationIT {

    @Autowired
    private ExerciseScheduleRepository exerciseScheduleRepository;

    @Mock
    private ScheduleNotifier scheduleNotifier;

    private CheckNewScheduleJob checkNewScheduleJob;

    @BeforeEach
    void setUp() {
        checkNewScheduleJob = new CheckNewScheduleJob(exerciseScheduleRepository, scheduleNotifier);

        // Register a mock observer
        scheduleNotifier.addObserver(Mockito.mock(JobSchedulerObserver.class));
    }

    @Test
    void testCheckNewScheduleJobNotifiesNewSchedules() {
        //Arrange: Insert test data
        ExerciseSchedule oldSchedule = new ExerciseSchedule();
        oldSchedule.setCreatedDate(LocalDateTime.now().minusDays(1));
        oldSchedule.setUserId(1L);
        exerciseScheduleRepository.save(oldSchedule);

        ExerciseSchedule newSchedule = new ExerciseSchedule();
        newSchedule.setCreatedDate(LocalDateTime.now());
        newSchedule.setUserId(1L);
        exerciseScheduleRepository.save(newSchedule);

        // Act: Execute the job
        checkNewScheduleJob.execute(null);

        // Assert: Verify that the notifier was called with the new schedule
        Mockito.verify(scheduleNotifier, Mockito.times(1)).notifyObservers(Mockito.argThat(schedule -> schedule.getId().equals(newSchedule.getId())));
        // and not the old schedule
        Mockito.verify(scheduleNotifier, Mockito.never()).notifyObservers(Mockito.argThat(schedule -> schedule.getId().equals(oldSchedule.getId())));


        List<ExerciseSchedule> allSchedules = exerciseScheduleRepository.findAll();
        assertThat(allSchedules).hasSize(2); // Ensure both schedules exist in the database
    }
}