package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentFailedBreakout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TblPercentFailedBreakoutRepository extends JpaRepository<TblPercentFailedBreakout, String> {

    @Modifying
    @Query(
            value = "TRUNCATE TABLE tbl_percent_failed_breakout",
            nativeQuery = true
    )
    void truncateTbl();
}
