package exchange.kanga.task.impl;

import exchange.kanga.task.data.*;
import exchange.kanga.task.service.ExternalRest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.*;

@ApplicationScope
@Component
public class Calculation {
    @Autowired
    ExternalRest externalRest;

    @Getter
    Rank rank;

    private final List<Pair> listPairs;


    public Calculation(ExternalRest externalRest) {
        this.externalRest = externalRest;
        this.listPairs = externalRest.getPairs();
    }

    public void calcRank() {
        Rank rank = new Rank();
        var g1 = new ArrayList<Market>();
        var g2 = new ArrayList<Market>();
        var g3 = new ArrayList<Market>();

        listPairs.parallelStream().forEach(p-> {
            OrderBook  orderBook = externalRest.getOrderBook(p.getTicker_id());
            Market market = processOrders(orderBook);
            if(market.getSpread().equals("N/A")){
                g3.add(market);
            } else if(Double.parseDouble(market.getSpread())<=2){
                g1.add(market);
            } else {
                g2.add(market);
            }
        });
        rank.getRanking().put("group3", g3);
        rank.getRanking().put("group2", g2);
        rank.getRanking().put("group1", g1);


        sortRank(rank);
        this.rank = rank;
    }

    private Market processOrders(OrderBook orderBook){
        Market market = new Market();
        market.setMarket(orderBook.getTicker_id());
        List<Offer> bids = orderBook.getBids();
        List<Offer> asks = orderBook.getAsks();
        if(!asks.isEmpty() || !bids.isEmpty()) {
            double a = Collections.max(asks, Comparator.comparingDouble(Offer::getPrice)).getPrice();
            double b = Collections.min(bids, Comparator.comparingDouble(Offer::getPrice)).getPrice();
            double spread = (a-b)/(0.5*(a+b))*100D;
            market.setSpread(String.format("%.2f", spread));
        } else {
            market.setSpread("N/A");

        }
        return market;
    }

    private void sortRank(Rank rank){
        for(Map.Entry<String,List<Market>> group : rank.getRanking().entrySet()){
            if(!group.getValue().isEmpty()) {
                group.getValue().sort(Comparator.comparing(Market::getMarket));
            }
        }
    }

}
