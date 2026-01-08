package com.example.KPI.service;

import com.example.KPI.dto.KpiItemDto;
import com.example.KPI.dto.KpiRequestDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class KpiService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${kpi.url-lv2}")
    private String urlLv2;

    @Value("${kpi.url-lv3}")
    private String urlLv3;

    // 증가율 계산
    public double calculateRate(double currentValue, double actualValue) {
        return ((actualValue - currentValue) / currentValue) * 100;
    }

    public void sendKpi(KpiRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        String trsDttm = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String ocrDttm = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        HttpHeaders headers = JsonHeader();

        for (KpiItemDto item : requestDto.getKpiItems()) {
            System.out.println("전송 중: " + item.getCompanyName() + " - " + item.getKpiDtlNm());

            double actualRate = calculateRate(item.getCurrentValue(), item.getActualValue());

            // Lv2 전송 (증가율)
            JSONObject lv2 = buildLv2(item, ocrDttm, trsDttm, actualRate);
            post(urlLv2, lv2, headers);

            // Lv3 전송 (실제값)
            JSONObject lv3 = buildLv3(item, ocrDttm, trsDttm);
            post(urlLv3, lv3, headers);
        }
    }

    // Lv2 (증가율)
    private JSONObject buildLv2(KpiItemDto item, String ocrDttm, String trsDttm, double actualRate) {

        double targetRate = calculateRate(item.getCurrentValue(), item.getTargetValue());

        JSONObject param = new JSONObject()
                .put("kpiCertKey", item.getCertKey())
                .put("ocrDttm", ocrDttm)
                .put("kpiFldCd", item.getKpiFldCd())
                .put("kpiDtlCd", item.getKpiDtlCd())
                .put("kpiDtlNm", item.getKpiDtlNm())
                .put("achrt", Double.toString(actualRate))
                .put("targetAchrt", targetRate)
                .put("trsDttm", trsDttm);

        return new JSONObject()
                .put("KPILEVEL2", new JSONArray().put(param));
    }

    // Lv3 (실제값)
    private JSONObject buildLv3(KpiItemDto item, String ocrDttm, String trsDttm) {
        JSONObject param = new JSONObject()
                .put("kpiCertKey", item.getCertKey())
                .put("ocrDttm", ocrDttm)
                .put("kpiFldCd", item.getKpiFldCd())
                .put("kpiDtlCd", item.getKpiDtlCd())
                .put("kpiDtlNm", item.getKpiDtlNm())
                .put("msmtVl", item.getActualValue())
                .put("unt", item.getUnit())
                .put("trsDttm", trsDttm);

        return new JSONObject()
                .put("KPILEVEL3", new JSONArray().put(param));
    }

    // HTTP 헤더
    public HttpHeaders JsonHeader() {
        HttpHeaders headers = new HttpHeaders();

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    // ㅖㅒㄴㅆ 전송
    private void post(String url, JSONObject body, HttpHeaders headers) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("POST: " + url);
            System.out.println("Response => " + response.getBody());
        } catch (Exception e) {
            System.out.println("전송 실패: " + url);
            System.out.println("ERROR!!!!!! :  " + e.getMessage());
        }
    }
}