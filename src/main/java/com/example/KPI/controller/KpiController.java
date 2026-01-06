package com.example.KPI.controller;

import com.example.KPI.dto.KpiDto;
import com.example.KPI.service.KpiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KpiController {

    @PostMapping("/kpi")
    public String KPI(@RequestBody KpiDto kpiDto) throws Exception {
        KpiService kpiService = new KpiService();

        kpiService.sendKpi(kpiDto);

        String sendData = "전송 완료";

        return sendData;
    }
}