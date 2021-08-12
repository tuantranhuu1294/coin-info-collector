package net.cglcapital.coininfo.common.constant;

public enum PeriodTime {

    TWENTY_FOUR_HOURS("24h"),
    SEVEN_DAYS("7d"),
    THIRTY_DAYS("30d"),
    NINETY_DAYS("90d"),
    FIFTY_TWO_WEEKS("52w"),
    ALL_TIME("All Time");

    private String period;


    PeriodTime(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }
}
