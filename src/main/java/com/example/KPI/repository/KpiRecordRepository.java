package com.example.KPI.repository;

import com.example.KPI.entity.KpiRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KpiRecordRepository extends JpaRepository<KpiRecord, Long> {

    // 회사명으로 조회
    List<KpiRecord> findByCompanyName(String companyName);

    // 전체 조회
    List<KpiRecord> findAllByOrderByCreatedAtDesc();
}