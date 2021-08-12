package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentTrueBreakoutFib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TblPercentTrueBreakoutFibRepository extends JpaRepository<TblPercentTrueBreakoutFib, String> {

    @Modifying
    @Query(
            value = "TRUNCATE TABLE tbl_percent_true_breakout_fib",
            nativeQuery = true
    )
    void truncateTbl();
}
