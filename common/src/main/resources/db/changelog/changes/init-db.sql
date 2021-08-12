-- create table breakout_logs to store coins' breakdown logging
create table if not exists breakout_logs
(
    id int auto_increment
        primary key,
    code varchar(10) null,
    period_time varchar(255) null,
    breakout_at datetime(6) null
);

create table if not exists breakdown_logs
(
    id int auto_increment
    primary key,
    code varchar(10) null,
    period_time varchar(255) null,
    breakdown_at datetime(6) null
);

create table if not exists coins
(
    code varchar(10) not null
        primary key,
    name varchar(255) null,
    cmc_url_symbol varchar(255) null
);

create table price_histories
(
    id                           int auto_increment
        primary key,
    code                         varchar(10) not null,
    open_price                   float       null,
    close_price                  float       null,
    high_price                   float       null,
    low_price                    float       null,
    volume                       float       null,
    open_time                    datetime(6) null,
    close_time                   datetime(6) null,
    quote_asset_volume           float       null,
    number_of_trades             bigint      null,
    taker_buy_quote_asset_volume float       null,
    taker_buy_base_asset_volume  float       null,
    create_at                    datetime(6) null,
    update_at                    datetime(6) null
);

create table if not exists statistics
(
    code varchar(10) null,
    twenty_four_hour_low float null,
    twenty_four_hour_high float null,
    market_rank int null,
    market_cap float null,
    seven_day_low float null,
    seven_day_high float null,
    fifty_two_week_low float null,
    fifty_two_week_high float null,
    thirty_day_low float null,
    thirty_day_high float null,
    ninety_day_low float null,
    ninety_day_high float null,
    all_time_low float null,
    all_time_high float null,
    create_at datetime(6) null,
    update_at datetime(6) null,
    constraint statistics_coins_code_fk
        foreign key (code) references coins (code)
);
