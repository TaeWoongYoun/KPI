package com.example.KPI.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleTestService {
    private int be_val = 100;
    private int now_val = 120;

    public String Lv1Service() {
        double sendData = sendDataCalculate();
        String result = "Lv1 Send - Rate: " + sendData + "%";
        System.out.println(result);
        return result;
    }

    public String Lv2Service() {
        LocalDateTime today = LocalDateTime.now();
        String result = "Lv2 Send - Prev: " + be_val + ", Current: " + now_val + ", Date: " + today;
        System.out.println(result);
        return result;
    }

    public double sendDataCalculate() {
        double upRate = ((double)(now_val - be_val) / be_val) * 100;
        return upRate;
    }
}