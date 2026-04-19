package com.example.schedule.controller;

import com.example.schedule.model.Room;
import com.example.schedule.repository.GroupRepository;
import com.example.schedule.repository.RoomRepository;
import com.example.schedule.repository.TeacherRepository;
import com.example.schedule.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;

    public ScheduleController(ScheduleService scheduleService,
                              GroupRepository groupRepository,
                              TeacherRepository teacherRepository,
                              RoomRepository roomRepository){
        this.scheduleService = scheduleService;
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("schedules", scheduleService.getAll());
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "dashboard";
    }

    @GetMapping("/sort/group")
    public String sortByGroup(Model model) {
        model.addAttribute("schedules", scheduleService.sortByGroup());
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "dashboard";
    }

    @GetMapping("/sort/teacher")
    public String sortByTeacher(Model model) {
        model.addAttribute("schedules", scheduleService.sortByTeacher());
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "dashboard";
    }

    @GetMapping("/sort/room")
    public String sortByRoom(Model model) {
        model.addAttribute("schedules", scheduleService.sortByRoom());
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "dashboard";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long groupId,
                      @RequestParam Long teacherId,
                      @RequestParam Long roomId) {
        scheduleService.createEmpty(groupId, teacherId, roomId);
        return "redirect:/";
    }
}
