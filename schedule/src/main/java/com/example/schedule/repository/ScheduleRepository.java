package com.example.schedule.repository;

import com.example.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByGroup();
    List<Schedule> findAllByOrderByTeacher();
    List<Schedule> findAllByOrderByRoom();
}