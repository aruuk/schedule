package com.example.schedule.service;

import com.example.schedule.ScheduleApplication;
import com.example.schedule.model.Group;
import com.example.schedule.model.Room;
import com.example.schedule.model.Schedule;
import com.example.schedule.model.Teacher;
import com.example.schedule.repository.GroupRepository;
import com.example.schedule.repository.RoomRepository;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           GroupRepository groupRepository,
                           TeacherRepository teacherRepository,
                           RoomRepository roomRepository) {
        this.scheduleRepository = scheduleRepository;
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> sortByGroup(){
        return scheduleRepository.findAllByOrderByGroup();
    }

    public List<Schedule> sortByTeacher(){
        return scheduleRepository.findAllByOrderByTeacher();
    }

    public List<Schedule> sortByRoom(){
        return scheduleRepository.findAllByOrderByRoom();
    }

    public void createEmpty(Long groupId, Long teacherId, Long roomId) {
        Group group = groupRepository.findById(groupId)
                        .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Teacher teacher = teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        Room room = roomRepository.findById(roomId)
                        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        scheduleRepository.save(new Schedule());

        Schedule schedule = new Schedule();
        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setRoom(room);
        scheduleRepository.save(schedule);
    }
}
