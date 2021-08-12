package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.BreakoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BreakoutLogRepository extends JpaRepository<BreakoutLog, String> {

    BreakoutLog findFirstByCodeAndPeriodTimeOrderByBreakoutAtDesc(String code, String periodTime);

    List<BreakoutLog> findAllByBreakoutAtBetween(LocalDateTime from, LocalDateTime to);
}
