package com.example.KPI.dto;

import java.util.List;

public class KpiRequestDto {

    private String companyName;
    private List<KpiItemDto> kpiItems;

    // get set 모음
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<KpiItemDto> getKpiItems() {
        return kpiItems;
    }
    public void setKpiItems(List<KpiItemDto> kpiItems) {
        this.kpiItems = kpiItems;
    }
}