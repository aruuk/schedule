package com.example.schedule.service;

import com.example.schedule.model.*;
import com.example.schedule.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final GroupRepository groupRepo;
    private final TeacherRepository teacherRepo;
    private final RoomRepository roomRepo;

    public ScheduleService(ScheduleRepository scheduleRepo,
                           GroupRepository groupRepo,
                           TeacherRepository teacherRepo,
                           RoomRepository roomRepo) {
        this.scheduleRepo = scheduleRepo;
        this.groupRepo = groupRepo;
        this.teacherRepo = teacherRepo;
        this.roomRepo = roomRepo;
    }

    @Transactional(readOnly = true)
    public List<Schedule> getByDay(ScheduleDay day) {
        return scheduleRepo.findAllByDayWithDetails(day);
    }

    @Transactional(readOnly = true)
    public Schedule getById(Long id) {
        return scheduleRepo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found: " + id));
    }

    @Transactional
    public void save(Long groupId, Long teacherId, Long roomId, ScheduleDay day, int lesson, String subject) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found: " + groupId));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        Schedule schedule = new Schedule();
        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setRoom(room);
        schedule.setDay(day);
        schedule.setLesson(lesson);
        schedule.setSubject(subject);

        scheduleRepo.save(schedule);
    }

    @Transactional
    public void update(Long id, Long groupId, Long teacherId, Long roomId, ScheduleDay day, int lesson, String subject) {
        Schedule schedule = getById(id);

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found: " + groupId));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setRoom(room);
        schedule.setDay(day);
        schedule.setLesson(lesson);
        schedule.setSubject(subject);

        scheduleRepo.save(schedule);
    }

    @Transactional
    public void delete(Long id) {
        scheduleRepo.deleteById(id);
    }
}