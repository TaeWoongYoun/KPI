package com.example.KPI.service;

import com.example.KPI.dto.KpiDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class KpiService {

    private RestTemplate restTemplate = new RestTemplate();
    private static final String CERT_KEY = "인증키값";
    private static final String URL = "외부API주소";

    // 증가율 계산
    public double calculateRate(int beVal, int nowVal) {
        return ((double)(nowVal - beVal) / beVal) * 100;
    }

    // 메인 전송 함수
    public void sendKpi(KpiDto kpiDto) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        String trsDttm = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String ocrDttm = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 증가율 계산
        double actualRate = calculateRate(kpiDto.getBe_val(), kpiDto.getNow_val());

        // Lv2 데이터 생성 (증가율)
        JSONObject lv2 = buildLv2(
                ocrDttm,
                trsDttm,
                actualRate,
                kpiDto.getTarget_rate()
        );

        // Lv3 데이터 생성 (실제 생산량)
        JSONObject lv3 = buildLv3(
                ocrDttm,
                trsDttm,
                kpiDto.getNow_val()
        );

//        System.out.println("계산된 증가율: " + actualRate + "%");
//
//        System.out.println("Lv2 데이터: " + lv2.toString());
//
//        System.out.println("Lv3 데이터: " + lv3.toString());

        // 전송 (헤더는 내일 구현)
        // post(URL, lv2, headers);
        // post(URL, lv3, headers);
    }

    // Lv2 바디 생성 (증가율)
    private JSONObject buildLv2(String ocrDttm, String trsDttm, double actualRate, double targetRate) throws Exception {
        JSONObject param = new JSONObject()
                .put("kpiCretKey", CERT_KEY)
                .put("ocrDttm", ocrDttm)
                .put("kpiFldCd", "P")
                .put("kpiDtlCd", "PB")
                .put("kpiDtlNm", "일 생산량 증가율")
                .put("achrt", Double.toString(actualRate))
                .put("targetAchrt", targetRate)
                .put("trsDttm", trsDttm);

        return new JSONObject()
                .put("KPILEVEL2", new JSONArray().put(param));
    }

    // Lv3 바디 생성 (실제 생산량)
    private JSONObject buildLv3(String ocrDttm, String trsDttm, int production) throws Exception {
        JSONObject param = new JSONObject()
                .put("kpiCretKey", CERT_KEY)
                .put("ocrDttm", ocrDttm)
                .put("kpiFldCd", "P")
                .put("kpiDtlCd", "PB")
                .put("kpiDtlNm", "일 생산량")
                .put("msmtVl", production)
                .put("unt", "EA")
                .put("trsDttm", trsDttm);

        return new JSONObject()
                .put("KPILEVEL3", new JSONArray().put(param));
    }

    // 전송 함수 (내일 헤더 추가 예정)
    private void post(String url, JSONObject body, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}