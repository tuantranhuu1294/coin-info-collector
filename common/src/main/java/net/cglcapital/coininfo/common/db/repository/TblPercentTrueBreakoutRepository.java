package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentTrueBreakout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TblPercentTrueBreakoutRepository extends JpaRepository<TblPercentTrueBreakout, String> {

    @Modifying
    @Query(
            value = "TRUNCATE TABLE tbl_percent_true_breakout",
            nativeQuery = true
    )
    void truncateTbl();
}
