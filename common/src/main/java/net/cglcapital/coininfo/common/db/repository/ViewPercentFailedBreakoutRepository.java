package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentFailedBreakout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPercentFailedBreakoutRepository extends JpaRepository<ViewPercentFailedBreakout, String> {

}
