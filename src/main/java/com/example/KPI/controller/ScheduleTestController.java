package com.example.KPI.controller;

import com.example.KPI.service.ScheduleTestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class ScheduleTestController {

    private final ScheduleTestService service;

    public ScheduleTestController(ScheduleTestService service) {
        this.service = service;
    }

    // 10초마다 자동 실행
    @Scheduled(fixedRate = 10000)
    public void testSchedule() {
        service.Lv1Service();
        service.Lv2Service();
    }

    @GetMapping("/kpi")
    public String showKPI() {
        String lv1 = service.Lv1Service();
        String lv2 = service.Lv2Service();
        return lv1 + "<br>" + lv2;
    }
}