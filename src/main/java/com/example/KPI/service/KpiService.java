package com.example.KPI.service;

import com.example.KPI.dto.KpiDto;

// calculator 함수 외부로 빼기
public class KpiService {
    public boolean Lv1Service(KpiDto kpiDto) {
        double upRate = ((double)(kpiDto.getNow_val() - kpiDto.getBe_val()) / kpiDto.getBe_val()) * 100;
        System.out.println(upRate);
        return true;
    }
    public boolean Lv2Service(KpiDto kpiDto) {
        String result = "Lv2 Send - Prev: " + kpiDto.getBe_val() + ", Current: " + kpiDto.getNow_val();
        System.out.println(result);
        return false;
    }
}