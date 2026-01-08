package com.example.KPI.dto;

import java.util.List;

public class KpiResponseDto {

    private String companyName;
    private List<KpiItemDto> kpiItems;
    private boolean sendResult;
    private String message;

    public KpiResponseDto() {}

    public KpiResponseDto(String companyName, List<KpiItemDto> kpiItems, boolean sendResult, String message) {
        this.companyName = companyName;
        this.kpiItems = kpiItems;
        this.sendResult = sendResult;
        this.message = message;
    }

    // Get 모음
    public String getCompanyName() {
        return companyName;
    }

    public List<KpiItemDto> getKpiItems() {
        return kpiItems;
    }

    public boolean isSendResult() {
        return sendResult;
    }

    public String getMessage() {
        return message;
    }
}