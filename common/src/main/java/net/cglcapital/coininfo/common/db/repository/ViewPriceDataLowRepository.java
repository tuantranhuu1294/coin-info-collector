package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.ViewPriceDataLow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPriceDataLowRepository extends JpaRepository<ViewPriceDataLow, String> {

}
