package com.example.KPI.controller;

import com.example.KPI.dto.KpiDto;
import com.example.KPI.service.KpiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class KpiController {
    @PostMapping("/kpi")

    public String KPI(@RequestBody KpiDto kpiDto) {
        KpiService kpiService = new KpiService();

        boolean lv1Result = kpiService.Lv1Service(kpiDto);
        boolean lv2Result = kpiService.Lv2Service(kpiDto);

        String sendData = "Lv1: " + lv1Result + ", Lv2: " + lv2Result;

        return sendData;
    }
}
