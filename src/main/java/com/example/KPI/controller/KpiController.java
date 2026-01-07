package com.example.KPI.controller;

import com.example.KPI.dto.KpiDto;
import com.example.KPI.dto.KpiResponseDto;
import com.example.KPI.service.KpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KpiController {
//    생성자 주입식으로 수정
    private final KpiService kpiService;
    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }
//    생성자 주입식으로 수정
    @PostMapping("/kpi")
    public ResponseEntity<KpiResponseDto> KPI(@RequestBody KpiDto kpiDto) {

        kpiService.sendKpi(kpiDto);

        // ResponseDto 생성해서 리턴
        KpiResponseDto responseDto = new KpiResponseDto(
                kpiDto.getBe_val(),
                kpiDto.getNow_val(),
                kpiDto.getTarget_rate(),
                0.0,
                true,
                "전송 성공성공"
        );

        return ResponseEntity.ok(responseDto);
    }
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