package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.ViewPercentTrueBreakoutFib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPercentTrueBreakoutFibRepository extends JpaRepository<ViewPercentTrueBreakoutFib, String> {

}
