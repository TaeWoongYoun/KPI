package com.example.KPI.controller;

import com.example.KPI.dto.KpiDto;
import com.example.KPI.service.KpiService;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 테스트용
//    @GetMapping("/kpi/test")
//    public String testKpi() throws Exception {
//        KpiDto kpiDto = new KpiDto();
//        kpiDto.setBe_val(100);
//        kpiDto.setNow_val(120);
//        kpiDto.setTarget_rate(20.0);
//        kpiDto.setApi_key("test-key");
//
//        KpiService kpiService = new KpiService();
//        kpiService.sendKpi(kpiDto);
//
//        return "테스트 완료";
//    }
}