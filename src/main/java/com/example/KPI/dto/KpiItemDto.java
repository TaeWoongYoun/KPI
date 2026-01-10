package com.example.KPI.dto;

public class KpiItemDto {

    private String companyName;
    private String certKey;
    private String kpiFldCd;
    private String kpiDtlCd;
    private String kpiDtlNm;
    private String unit;
    private String period;
    private double previousValue;
    private double currentValue;
    private double targetRate;
    private double achieveRate;

    // get set 모음
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

    public double getPreviousValue() {
        return previousValue;
    }
    public void setPreviousValue(double previousValue) {
        this.previousValue = previousValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getTargetRate() {
        return targetRate;
    }
    public void setTargetRate(double targetRate) {
        this.targetRate = targetRate;
    }

    public double getAchieveRate() {
        return achieveRate;
    }
    public void setAchieveRate(double achieveRate) {
        this.achieveRate = achieveRate;
    }
}