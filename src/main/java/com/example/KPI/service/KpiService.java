package com.example.KPI.service;

import com.example.KPI.dto.KpiItemDto;
import com.example.KPI.dto.KpiRequestDto;
import com.example.KPI.entity.KpiRecord;
import com.example.KPI.repository.KpiRecordRepository;
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
    private final KpiRecordRepository kpiRecordRepository;

    @Value("${kpi.url-lv2}")
    private String urlLv2;

    @Value("${kpi.url-lv3}")
    private String urlLv3;

    public KpiService(KpiRecordRepository kpiRecordRepository) {
        this.kpiRecordRepository = kpiRecordRepository;
    }

    public String getUnitByFldCd(String kpiFldCd) {
        switch (kpiFldCd) {
            case "P": return "EA";
            case "Q": return "EA";
            case "D": return "%";
            case "C": return "원";
            default: return "EA";
        }
    }

    // 실적 달성률 계산
    public double calculateRate(double previousValue, double currentValue) {
        if (previousValue == 0) return 0;
        return ((currentValue - previousValue) / previousValue) * 100;
    }

    public void sendKpi(KpiRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        String trsDttm = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String ocrDttm = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        HttpHeaders headers = JsonHeader();

        for (KpiItemDto item : requestDto.getKpiItems()) {
            // 단위 자동 설정
            item.setUnit(getUnitByFldCd(item.getKpiFldCd()));

            // 실적 달성률 계산
            double achieveRate = calculateRate(item.getPreviousValue(), item.getCurrentValue());
            item.setAchieveRate(achieveRate);

            System.out.println("전송 중: " + item.getCompanyName() + " - " + item.getKpiDtlNm());

            // Lv2 전송
            JSONObject lv2 = buildLv2(item, ocrDttm, trsDttm, achieveRate);
            post(urlLv2, lv2, headers);

            // Lv3 전송
            JSONObject lv3 = buildLv3(item, ocrDttm, trsDttm);
            post(urlLv3, lv3, headers);

            // DB 저장
            saveRecord(item, achieveRate);
        }
    }

    // DB 저장
    private void saveRecord(KpiItemDto item, double achieveRate) {
        KpiRecord record = new KpiRecord();
        record.setCompanyName(item.getCompanyName());
        record.setCertKey(item.getCertKey());
        record.setKpiFldCd(item.getKpiFldCd());
        record.setKpiDtlCd(item.getKpiDtlCd());
        record.setKpiDtlNm(item.getKpiDtlNm());
        record.setUnit(item.getUnit());
        record.setPeriod(item.getPeriod());
        record.setPreviousValue(item.getPreviousValue());
        record.setCurrentValue(item.getCurrentValue());
        record.setTargetRate(item.getTargetRate());
        record.setAchieveRate(achieveRate);

        kpiRecordRepository.save(record);
        System.out.println("DB 저장 완료: " + item.getKpiDtlNm());
    }

    // 전체 기록 조회
    public List<KpiRecord> getAllRecords() {
        return kpiRecordRepository.findAllByOrderByCreatedAtDesc();
    }

    // Lv2
    private JSONObject buildLv2(KpiItemDto item, String ocrDttm, String trsDttm, double achieveRate) {
        JSONObject param = new JSONObject()
                .put("kpiCertKey", item.getCertKey())
                .put("ocrDttm", ocrDttm)
                .put("kpiFldCd", item.getKpiFldCd())
                .put("kpiDtlCd", item.getKpiDtlCd())
                .put("kpiDtlNm", item.getKpiDtlNm())
                .put("achrt", Double.toString(achieveRate))
                .put("targetAchrt", item.getTargetRate())
                .put("trsDttm", trsDttm);

        return new JSONObject().put("KPILEVEL2", new JSONArray().put(param));
    }

    // Lv3
    private JSONObject buildLv3(KpiItemDto item, String ocrDttm, String trsDttm) {
        JSONObject param = new JSONObject()
                .put("kpiCertKey", item.getCertKey())
                .put("ocrDttm", ocrDttm)
                .put("kpiFldCd", item.getKpiFldCd())
                .put("kpiDtlCd", item.getKpiDtlCd())
                .put("kpiDtlNm", item.getKpiDtlNm())
                .put("msmtVl", item.getCurrentValue())
                .put("unt", item.getUnit())
                .put("trsDttm", trsDttm);

        return new JSONObject().put("KPILEVEL3", new JSONArray().put(param));
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

    // POST 전송
    private void post(String url, JSONObject body, HttpHeaders headers) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("POST: " + url);
            System.out.println("Response => " + response.getBody());
        } catch (Exception e) {
            System.out.println("전송 실패: " + url);
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}