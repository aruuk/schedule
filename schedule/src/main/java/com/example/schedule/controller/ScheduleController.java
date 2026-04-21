package com.example.schedule.controller;

import com.example.schedule.model.*;
import com.example.schedule.repository.GroupRepository;
import com.example.schedule.repository.RoomRepository;
import com.example.schedule.repository.TeacherRepository;
import com.example.schedule.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final GroupRepository groupRepo;
    private final TeacherRepository teacherRepo;
    private final RoomRepository roomRepo;

    public ScheduleController(ScheduleService scheduleService,
                              GroupRepository groupRepo,
                              TeacherRepository teacherRepo,
                              RoomRepository roomRepo) {
        this.scheduleService = scheduleService;
        this.groupRepo = groupRepo;
        this.teacherRepo = teacherRepo;
        this.roomRepo = roomRepo;
    }

    @GetMapping("/")
    public String dashboard(@RequestParam(value = "day", required = false) ScheduleDay day, Model model) {
        ScheduleDay selectedDay = (day != null ? day : ScheduleDay.MONDAY);

        model.addAttribute("selectedDay", selectedDay);
        model.addAttribute("days", ScheduleDay.values());
        model.addAttribute("schedules", scheduleService.getByDay(selectedDay));
        model.addAttribute("groups", groupRepo.findAll());
        model.addAttribute("teachers", teacherRepo.findAll());
        model.addAttribute("rooms", roomRepo.findAll());

        return "dashboard";
    }

    @GetMapping("/group/add")
    public String showGroupForm() {
        return "add_group";
    }

    @PostMapping("/group/add")
    public String addGroup(@RequestParam String name) {
        Group group = new Group();
        group.setName(name);
        groupRepo.save(group);
        return "redirect:/";
    }

    @GetMapping("/teacher/add")
    public String showTeacherForm() {
        return "add_teacher";
    }

    @PostMapping("/teacher/add")
    public String addTeacher(@RequestParam String name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacherRepo.save(teacher);
        return "redirect:/";
    }

    @GetMapping("/room/add")
    public String showRoomForm() {
        return "add_room";
    }

    @PostMapping("/room/add")
    public String addRoom(@RequestParam String number) {
        Room room = new Room();
        room.setNumber(number);
        roomRepo.save(room);
        return "redirect:/";
    }

    @GetMapping("/schedule/add")
    public String showAddForm(Model model) {
        model.addAttribute("groups", groupRepo.findAll());
        model.addAttribute("teachers", teacherRepo.findAll());
        model.addAttribute("rooms", roomRepo.findAll());
        model.addAttribute("days", ScheduleDay.values());
        return "add_schedule";
    }

    @PostMapping("/schedule/save")
    public String saveLesson(@RequestParam Long groupId,
                             @RequestParam Long teacherId,
                             @RequestParam Long roomId,
                             @RequestParam ScheduleDay day,
                             @RequestParam int lesson,
                             @RequestParam String subject) {
        scheduleService.save(groupId, teacherId, roomId, day, lesson, subject);
        return "redirect:/?day=" + day;
    }

    @GetMapping("/schedule/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getById(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("groups", groupRepo.findAll());
        model.addAttribute("teachers", teacherRepo.findAll());
        model.addAttribute("rooms", roomRepo.findAll());
        model.addAttribute("days", ScheduleDay.values());
        return "edit_schedule";
    }

    @PostMapping("/schedule/update")
    public String updateLesson(@RequestParam Long id,
                               @RequestParam Long groupId,
                               @RequestParam Long teacherId,
                               @RequestParam Long roomId,
                               @RequestParam ScheduleDay day,
                               @RequestParam int lesson,
                               @RequestParam String subject) {
        scheduleService.update(id, groupId, teacherId, roomId, day, lesson, subject);
        return "redirect:/?day=" + day;
    }

    @PostMapping("/schedule/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        Schedule schedule = scheduleService.getById(id);
        ScheduleDay day = schedule.getDay();
        scheduleService.delete(id);
        return "redirect:/?day=" + day;
    }
}