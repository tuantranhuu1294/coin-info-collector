package net.cglcapital.coininfo.common.db.repository;

import net.cglcapital.coininfo.common.db.domain.jpa.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, String> {

    Optional<PriceHistory> findFirstByCodeOrderByOpenTimeDesc(String code);

    /*@Query(value = "select * from price_histories where code = ?1 order by open_time desc limit 1",
    nativeQuery = true)
    PriceHistory findByCodeOrderByOpenTimeLimitOne(String code);*/
}
