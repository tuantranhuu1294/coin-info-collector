package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentConsolidation;
import net.cglcapital.coininfo.common.db.domain.jpa.ViewPriceDataHigh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPriceDataHighRepository extends JpaRepository<ViewPriceDataHigh, String> {

}
