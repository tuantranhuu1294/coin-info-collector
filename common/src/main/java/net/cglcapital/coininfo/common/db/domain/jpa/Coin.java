package net.cglcapital.coininfo.common.db.domain.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "coins")
public class Coin {

    @Id
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "cmc_url_symbol")
    private String cmcUrlSymbol;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "coin")
    private Statistic statistic;
}
