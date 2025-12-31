package com.example.KPI.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleTestService {
    private int workCnt = 100;
    private int todayWorkCnt = 120;

    public void Lv1Service() {
        double sendData = sendDataCalculate();
    }

    public void Lv2Service() {
        LocalDateTime today = LocalDateTime.now();
    }

    public double sendDataCalculate() {
        double upRate = (todayWorkCnt - workCnt) / workCnt * 100;
        return upRate;
    }
}