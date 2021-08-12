package net.cglcapital.coininfo.service.thirdparty;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.thirdparty.BaseGateway;
import net.cglcapital.coininfo.common.model.Quote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuoteServiceImpl extends BaseGateway implements QuoteService {

    @Value("${service.quotable.random-quote-url}")
    private String quotableUrl;

    @Override
    public Quote getRandomQuote() {
        log.info("Getting random quote...");
        ResponseEntity<Quote> responseEntity = jsonExchange(quotableUrl, HttpMethod.GET, prepareHeaders(), null, Quote.class);
        return responseEntity.getBody();
    }

    @Override
    protected String serviceName() {
        return "QuoteService";
    }
}
