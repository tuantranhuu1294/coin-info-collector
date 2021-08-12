package net.cglcapital.coininfo.common.db.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakoutFollowingListRepo extends CrudRepository<BreakoutFollowingList, String> {
}
