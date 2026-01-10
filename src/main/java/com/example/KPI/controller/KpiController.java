package com.example.KPI.controller;

import com.example.KPI.dto.KpiRequestDto;
import com.example.KPI.dto.KpiResponseDto;
import com.example.KPI.service.KpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KpiController {

    private final KpiService kpiService;
    private KpiResponseDto lastResult;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @PostMapping("/kpi")
    public ResponseEntity<KpiResponseDto> sendKpi(@RequestBody KpiRequestDto requestDto) {

        kpiService.sendKpi(requestDto);

        lastResult = new KpiResponseDto(
                requestDto.getCompanyName(),
                requestDto.getKpiItems(),
                true,
                "전송 완료"
        );

        return ResponseEntity.ok(lastResult);
    }

    @GetMapping("/kpi/result")
    public ResponseEntity<KpiResponseDto> getResult() {
        if (lastResult == null) {
            return ResponseEntity.ok(new KpiResponseDto(null, null, false, "전송 내역 없음"));
        }
        return ResponseEntity.ok(lastResult);
    }
}