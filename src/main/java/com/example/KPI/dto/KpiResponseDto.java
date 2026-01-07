package com.example.KPI.dto;

public class KpiResponseDto {
    private int be_val;
    private int now_val;
    private double target_rate;
    private double now_rate;
    private boolean sendResult;
    private String message;

    public KpiResponseDto(int be_val, int now_val, double target_rate, double now_rate, boolean sendResult, String message) {
        this.be_val = be_val;
        this.now_val = now_val;
        this.target_rate = target_rate;
        this.now_rate = now_rate;
        this.sendResult = sendResult;
        this.message = message;
    }
}