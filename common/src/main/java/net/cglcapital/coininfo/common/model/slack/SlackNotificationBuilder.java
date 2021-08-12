package net.cglcapital.coininfo.common.model.slack;

import net.cglcapital.coininfo.common.constant.PeriodTime;
import net.cglcapital.coininfo.common.db.domain.dto.CoinDTO;
import net.cglcapital.coininfo.common.db.domain.dto.StatisticDTO;
import net.cglcapital.coininfo.common.model.MorningReport;
import net.cglcapital.coininfo.common.model.Quote;

import java.util.ArrayList;
import java.util.List;

import static net.cglcapital.coininfo.common.model.slack.Accessory.AccessoryType.BUTTON;
import static net.cglcapital.coininfo.common.model.slack.Accessory.ActionId.BUTTON_ACTION;
import static net.cglcapital.coininfo.common.model.slack.Text.TextType.MRKDWN;
import static net.cglcapital.coininfo.common.model.slack.Text.TextType.PLAIN_TEXT;

public class SlackNotificationBuilder {

    private static final String BINANCE_TRADE_URL = "https://www.binance.com/en/trade/%s_usdt";
    private static final String BREAKOUT_NOTIFICATION = "Breakout Notification";
    private static final String BREAKDOWN_NOTIFICATION = "Breakdown Notification";
    private static final String BREAKOUT_MESSAGE_TEMP = ":white_check_mark: *%s* breakout *%s* peak at price *%s* :point_up::point_up:";
    private static final String BREAKDOWN_MESSAGE_TEMP = ":white_check_mark: *%s* breakdown *%s* trough at price *%s* :point_down::point_down:";
    private static final String MORNING_REPORT_NOTIFICATION = "Cafe Sáng :coffee::coffee::coffee:";
    private static final String MORNING_INTRO_TITLE = "Chuyên mục _\"Dậy sớm để thành công\"_";
    private static final String MORNING_REPORT_QUOTE_TEMP = " _\"%s\"_ - *%s*";
    private static final String TITLE_REPORT = ":mag_right: Một chút thống kê 24h qua:";

    public static SlackMessagePayload buildMorningReportMessage(MorningReport morningReport, Quote quote) {
        List<BlockElement> blocks = new ArrayList<>();

        // build header block
        HeaderBlockElement headerBlock = new HeaderBlockElement();
        headerBlock.setText(new Text(PLAIN_TEXT, MORNING_REPORT_NOTIFICATION, true));
        blocks.add(headerBlock);
        blocks.add(new DividerBlockElement());

        // build introduce msg block
        SectionBlockElement sectionIntroBlock = buildIntroSectionBlock();
        blocks.add(sectionIntroBlock);

        // build quote section block
        SectionBlockElement sectionQuoteBlock = buildQuoteSectionBlock(quote);
        blocks.add(sectionQuoteBlock);
        blocks.add(new DividerBlockElement());

        // build title section report block
        SectionBlockElement titleSectionReportBlock = buildSectionTitleReportBlock();
        blocks.add(titleSectionReportBlock);

        // build report block
        SectionBlockElement sectionReportBlock = buildSectionReportBlock(morningReport);
        blocks.add(sectionReportBlock);

        // build report block
        ImageBlockElement imageBlock = buildImageBlock();
        blocks.add(imageBlock);

        return new SlackMessagePayload(blocks);
    }

    private static SectionBlockElement buildSectionTitleReportBlock() {
        SectionBlockElement sectionBlockElement = new SectionBlockElement();
        sectionBlockElement.setText(new Text(MRKDWN, TITLE_REPORT, null));
        return sectionBlockElement;
    }

    public static final String COVER_IMAGE = "https://assets.losspreventionmedia.com/uploads/2019/08/cryptocurrency-bitcoin-1280x720.jpg";

    private static ImageBlockElement buildImageBlock() {
        ImageBlockElement imgBlockElem = new ImageBlockElement();
        imgBlockElem.setImageUrl(COVER_IMAGE);
        imgBlockElem.setAltText("BTC");
        return imgBlockElem;
    }

    private static SectionBlockElement buildSectionReportBlock(MorningReport report) {
        SectionBlockElement sectionInfoBlock = new SectionBlockElement();

        List<Text> fields = new ArrayList<>();
        Text allTimeBreakout = new Text(PLAIN_TEXT, ":bulb: All time breakout: " +
            report.getAllTimeBreakoutList().toString().replace("[", "").replace("]", ""), true);
        fields.add(allTimeBreakout);

        Text fiftyTwoWeeksBreakout = new Text(PLAIN_TEXT, ":bulb: 52w breakout: " +
            report.getFiftyTwoWeeksBreakoutList().toString().replace("[", "").replace("]", ""), true);
        fields.add(fiftyTwoWeeksBreakout);

        Text ninetyDaysBreakout = new Text(PLAIN_TEXT, ":bulb: 90d breakout: " +
            report.getNinetyDaysBreakoutList().toString().replace("[", "").replace("]", ""), true);
        fields.add(ninetyDaysBreakout);

        Text thirtyDaysBreakout = new Text(PLAIN_TEXT, ":bulb: 30d breakout: " +
            report.getThirtyDaysBreakoutList().toString().replace("[", "").replace("]", ""), true);
        fields.add(thirtyDaysBreakout);

        Text sevenDaysBreakout = new Text(PLAIN_TEXT, ":bulb: 7d breakout: " +
            report.getSevenDaysBreakoutList().toString().replace("[", "").replace("]", ""), true);
        fields.add(sevenDaysBreakout);

        sectionInfoBlock.setFields(fields);
        return sectionInfoBlock;
    }

    private static SectionBlockElement buildIntroSectionBlock() {
        SectionBlockElement sectionMsgBlock = new SectionBlockElement();
        sectionMsgBlock.setText(new Text(MRKDWN, MORNING_INTRO_TITLE, null));
        return sectionMsgBlock;
    }

    private static SectionBlockElement buildQuoteSectionBlock(Quote quote) {
        SectionBlockElement sectionMsgBlock = new SectionBlockElement();
        sectionMsgBlock.setText(new Text(MRKDWN, String.format(MORNING_REPORT_QUOTE_TEMP, quote.getContent(),
            quote.getAuthor()), null));
        return sectionMsgBlock;
    }

    public static SlackMessagePayload buildBreakdownMessage(CoinDTO coinDTO, PeriodTime periodTime, float currentPrice) {
        List<BlockElement> blocks = new ArrayList<>();

        // build header block
        HeaderBlockElement headerBlock = new HeaderBlockElement();
        headerBlock.setText(new Text(PLAIN_TEXT, BREAKDOWN_NOTIFICATION, true));
        blocks.add(headerBlock);
        blocks.add(new DividerBlockElement());

        // build section message notification block
        SectionBlockElement sectionMsgBlock = buildBreakdownSectionMsgBlock(coinDTO, periodTime, currentPrice);
        blocks.add(sectionMsgBlock);

        blocks.add(new DividerBlockElement());

        // build section coin info block
        SectionBlockElement sectionInfoBlock = buildSectionInfoBlock(coinDTO);
        blocks.add(sectionInfoBlock);

        return new SlackMessagePayload(blocks);
    }

    public static SlackMessagePayload buildBreakoutMessage(CoinDTO coinDTO, PeriodTime periodTime, float currentPrice) {
        List<BlockElement> blocks = new ArrayList<>();

        // build header block
        HeaderBlockElement headerBlock = new HeaderBlockElement();
        headerBlock.setText(new Text(PLAIN_TEXT, BREAKOUT_NOTIFICATION, true));
        blocks.add(headerBlock);
        blocks.add(new DividerBlockElement());

        // build section message notification block
        SectionBlockElement sectionMsgBlock = buildBreakoutSectionMsgBlock(coinDTO, periodTime, currentPrice);
        blocks.add(sectionMsgBlock);

        blocks.add(new DividerBlockElement());

        // build section coin info block
        SectionBlockElement sectionInfoBlock = buildSectionInfoBlock(coinDTO);
        blocks.add(sectionInfoBlock);

        return new SlackMessagePayload(blocks);
    }

    private static SectionBlockElement buildBreakoutSectionMsgBlock(CoinDTO coinDTO, PeriodTime periodTime, float currentPrice) {
        SectionBlockElement sectionMsgBlock = new SectionBlockElement();
        sectionMsgBlock.setText(new Text(MRKDWN, String.format(BREAKOUT_MESSAGE_TEMP, coinDTO.getCode().toUpperCase(),
            periodTime.getPeriod(), currentPrice), null));

        Accessory sessionAccessory = new Accessory(BUTTON);
        sessionAccessory.setText(new Text(PLAIN_TEXT, "Long", true));
        sessionAccessory.setValue("click_me_123");
        sessionAccessory.setUrl(String.format(BINANCE_TRADE_URL, coinDTO.getCode()));
        sessionAccessory.setActionId(BUTTON_ACTION);
        sectionMsgBlock.setAccessory(sessionAccessory);

        return sectionMsgBlock;
    }

    private static SectionBlockElement buildBreakdownSectionMsgBlock(CoinDTO coinDTO, PeriodTime periodTime, float currentPrice) {
        SectionBlockElement sectionMsgBlock = new SectionBlockElement();
        sectionMsgBlock.setText(new Text(MRKDWN, String.format(BREAKDOWN_MESSAGE_TEMP, coinDTO.getCode().toUpperCase(),
            periodTime.getPeriod(), currentPrice), null));

        Accessory sessionAccessory = new Accessory(BUTTON);
        sessionAccessory.setText(new Text(PLAIN_TEXT, "Short", true));
        sessionAccessory.setValue("click_me_123");
        sessionAccessory.setUrl(String.format(BINANCE_TRADE_URL, coinDTO.getCode()));
        sessionAccessory.setActionId(BUTTON_ACTION);
        sectionMsgBlock.setAccessory(sessionAccessory);

        return sectionMsgBlock;
    }

    private static SectionBlockElement buildSectionInfoBlock(CoinDTO coinDTO) {
        SectionBlockElement sectionInfoBlock = new SectionBlockElement();
        List<Text> fields = new ArrayList<>();
        StatisticDTO coinStat = coinDTO.getStatistic();
        Text marketRank = new Text(PLAIN_TEXT, ":bulb: Market Rank: #" + coinStat.getMarketRank(), true);
        fields.add(marketRank);

        Text marketCap = new Text(PLAIN_TEXT, ":bulb: Market Cap: " + coinStat.getMarketCap(), true);
        fields.add(marketCap);

        Text sevenDayLowHigh = new Text(PLAIN_TEXT, ":bulb: 7d Low/High: " + coinStat.getSevenDayLow() + "/" + coinStat.getSevenDayHigh(), true);
        fields.add(sevenDayLowHigh);

        Text thirtyDayLowHigh = new Text(PLAIN_TEXT, ":bulb: 30d Low/High: " + coinStat.getThirtyDayLow() + "/" + coinStat.getThirtyDayHigh(), true);
        fields.add(thirtyDayLowHigh);

        Text ninetyDayLowHigh = new Text(PLAIN_TEXT, ":bulb: 90d Low/High: " + coinStat.getNinetyDayLow() + "/" + coinStat.getNinetyDayHigh(), true);
        fields.add(ninetyDayLowHigh);

        Text fiftyWeekLowHigh = new Text(PLAIN_TEXT, ":bulb: 52w Low/High: " + coinStat.getFiftyTwoWeekLow() + "/" + coinStat.getFiftyTwoWeekHigh(), true);
        fields.add(fiftyWeekLowHigh);

        Text allTimeHigh = new Text(PLAIN_TEXT, ":bulb: All Time High: " + coinStat.getAllTimeHigh(), true);
        fields.add(allTimeHigh);

        Text allTimeLow = new Text(PLAIN_TEXT, ":bulb: All Time Low: " + coinStat.getAllTimeLow(), true);
        fields.add(allTimeLow);

        sectionInfoBlock.setFields(fields);
        return sectionInfoBlock;
    }
}

