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

        kpiService.Lv1Service(kpiDto);
        kpiService.Lv2Service(kpiDto);

        String sendData = "";

        return sendData;
    }
}
