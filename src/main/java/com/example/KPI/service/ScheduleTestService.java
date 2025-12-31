package com.example.KPI.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleTestService {
    private int workCnt = 100;
    private int todayWorkCnt = 120;

    public String Lv1Service() {
        double sendData = sendDataCalculate();
        String result = "Lv1 Send - Rate: " + sendData + "%";
        System.out.println(result);
        return result;
    }

    public String Lv2Service() {
        LocalDateTime today = LocalDateTime.now();
        String result = "Lv2 Send - Prev: " + workCnt + ", Current: " + todayWorkCnt + ", Date: " + today;
        System.out.println(result);
        return result;
    }

    public double sendDataCalculate() {
        double upRate = ((double)(todayWorkCnt - workCnt) / workCnt) * 100;
        return upRate;
    }
}