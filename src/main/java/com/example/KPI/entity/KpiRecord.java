package com.example.KPI.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class KpiRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDateTime createdAt;

    // JPA 필수 기본 생성자
    public KpiRecord() {
    }

    //시간 설정
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    //이번에도 get set 모음
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}