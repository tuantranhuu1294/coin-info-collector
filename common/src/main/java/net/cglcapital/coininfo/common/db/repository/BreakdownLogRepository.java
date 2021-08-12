package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.BreakdownLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BreakdownLogRepository extends JpaRepository<BreakdownLog, String> {

    BreakdownLog findFirstByCodeAndPeriodTimeOrderByBreakdownAtDesc(String code, String periodTime);

    List<BreakdownLog> findAllByBreakdownAtBetween(LocalDateTime from, LocalDateTime to);
}
