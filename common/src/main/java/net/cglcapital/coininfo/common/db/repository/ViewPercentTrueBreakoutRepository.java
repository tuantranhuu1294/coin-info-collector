package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentTrueBreakout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPercentTrueBreakoutRepository extends JpaRepository<ViewPercentTrueBreakout, String> {

}
