package net.cglcapital.coininfo.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MorningReport {

    private List<String> allTimeBreakoutList;
    private List<String> fiftyTwoWeeksBreakoutList;
    private List<String> ninetyDaysBreakoutList;
    private List<String> thirtyDaysBreakoutList;
    private List<String> sevenDaysBreakoutList;
}
