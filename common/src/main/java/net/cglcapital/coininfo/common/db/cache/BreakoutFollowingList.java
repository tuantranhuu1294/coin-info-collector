package net.cglcapital.coininfo.common.db.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("BreakoutFollowingList")
public class BreakoutFollowingList implements Serializable {

    @Id
    private String listName;
    private List<String> listCoins;
}
