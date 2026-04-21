package com.example.schedule.repository;

import com.example.schedule.model.Schedule;
import com.example.schedule.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
           select s
           from Schedule s
           join fetch s.group
           join fetch s.teacher
           join fetch s.room
           where s.day = :day
           order by s.lesson asc
           """)
    List<Schedule> findAllByDayWithDetails(@Param("day") ScheduleDay day);

    @Query("""
           select s
           from Schedule s
           join fetch s.group
           join fetch s.teacher
           join fetch s.room
           where s.id = :id
           """)
    Optional<Schedule> findByIdWithDetails(@Param("id") Long id);
}