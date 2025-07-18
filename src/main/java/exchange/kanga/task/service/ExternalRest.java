package exchange.kanga.task.service;

import exchange.kanga.task.configuration.Kanga;
import exchange.kanga.task.data.OrderBook;
import exchange.kanga.task.data.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

@Service
public class ExternalRest {
    private static final Logger log = LogManager.getLogger(ExternalRest.class);

    private final RestClient restClient;

    @Autowired


    public ExternalRest(Kanga kanga) {
        this.restClient = RestClient.create(URI.create(kanga.getUrl()));
        var r = getPairs();
        var ob = getOrderBook("BTC_USD");
    }

    public List<Pair> getPairs() {
        return restClient.get()
                .uri("/pairs")
                .retrieve().body(new ParameterizedTypeReference<List<Pair>>() {});
    }

    public OrderBook getOrderBook(String market) {
        return restClient.get()
                .uri("/orderbook/{market}", market)
                .retrieve().body(new ParameterizedTypeReference<OrderBook>() {});
    }
}
