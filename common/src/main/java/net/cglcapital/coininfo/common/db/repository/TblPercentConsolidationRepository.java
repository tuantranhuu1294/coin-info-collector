package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.TblPercentConsolidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TblPercentConsolidationRepository extends JpaRepository<TblPercentConsolidation, String> {

    @Modifying
    @Query(
            value = "TRUNCATE TABLE tbl_percent_consolidation",
            nativeQuery = true
    )
    void truncateTbl();

}
