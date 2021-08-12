package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.TblPriceDataLow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TblPriceDataLowRepository extends JpaRepository<TblPriceDataLow, String> {

    @Modifying
    @Query(
            value = "TRUNCATE TABLE tbl_price_data_low",
            nativeQuery = true
    )
    void truncateTbl();

}
