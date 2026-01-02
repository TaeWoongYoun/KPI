package com.example.KPI.dto;

public class KpiDto {

    private int be_val;
    private int now_val;
    private double kpi;
    private String api_key;

    public int getBe_val() {
        return be_val;
    }
    public void setBe_val(int be_val) {
        this.be_val = be_val;
    }

    public int getNow_val() {
        return now_val;
    }
    public void setNow_val(int now_val) {
        this.now_val = now_val;
    }

    public double getKpi() {
        return kpi;
    }
    public void setKpi(double kpi) {
        this.kpi = kpi;
    }
    
    public String getApi_key() {
        return api_key;
    }
    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

}
