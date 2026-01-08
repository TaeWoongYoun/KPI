package com.example.KPI.controller;

import com.example.KPI.dto.KpiRequestDto;
import com.example.KPI.dto.KpiResponseDto;
import com.example.KPI.service.KpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KpiController {

    private final KpiService kpiService;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @PostMapping("/kpi")
    public ResponseEntity<KpiResponseDto> sendKpi(@RequestBody KpiRequestDto requestDto) {

        kpiService.sendKpi(requestDto);

        KpiResponseDto responseDto = new KpiResponseDto(
                requestDto.getCompanyName(),
                requestDto.getKpiItems(),
                true,
                "전송됨"
        );

        return ResponseEntity.ok(responseDto);
    }
}