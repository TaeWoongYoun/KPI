package com.example.KPI.dto;

public class KpiItemDto {

    private String companyName;
    private String certKey;
    private String kpiFldCd;
    private String kpiDtlCd;
    private String kpiDtlNm;
    private String unit;
    private String period;
    private double targetValue;
    private double currentValue;
    private double actualValue;

    //get set 모음
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCertKey() {
        return certKey;
    }
    public void setCertKey(String certKey) {
        this.certKey = certKey;
    }

    public String getKpiFldCd() {
        return kpiFldCd;
    }
    public void setKpiFldCd(String kpiFldCd) {
        this.kpiFldCd = kpiFldCd;
    }

    public String getKpiDtlCd() {
        return kpiDtlCd;
    }
    public void setKpiDtlCd(String kpiDtlCd) {
        this.kpiDtlCd = kpiDtlCd;
    }

    public String getKpiDtlNm() {
        return kpiDtlNm;
    }
    public void setKpiDtlNm(String kpiDtlNm) {
        this.kpiDtlNm = kpiDtlNm;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTargetValue() {
        return targetValue;
    }
    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getActualValue() {
        return actualValue;
    }
    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }
}