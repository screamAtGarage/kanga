package exchange.kanga.task.impl;

import exchange.kanga.task.data.Market;
import exchange.kanga.task.data.*;
import exchange.kanga.task.service.ExternalRest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalculationTest {

    private ExternalRest externalRest;
    private Calculation calculation;

    @BeforeEach
    public void setup() {
        externalRest = mock(ExternalRest.class);

        List<Pair> testPairs = List.of(
           new Pair("TRUMP_USDT", "TRUMP" ,"USDT"),
           new Pair("OMG_USDT","OMG","USDT"),
           new Pair("ETH_EUR","ETH","EUR")
        );

        when(externalRest.getPairs()).thenReturn(testPairs);

        when(externalRest.getOrderBook("TRUMP_USDT")).thenReturn(createOrderBook("TRUMP_USDT", 102.0, 100.0));
        when(externalRest.getOrderBook("OMG_USDT")).thenReturn(createOrderBook("OMG_USDT", 110.0, 100.0));
        when(externalRest.getOrderBook("ETH_EUR")).thenReturn(createOrderBook("ETH_EUR", null, null));


        calculation = new Calculation(externalRest);
    }

    @Test
    public void testCalcRank_GroupedCorrectly() {
        calculation.calcRank();
        Rank rank = calculation.getRank();
        assertNotNull(rank);

        Map<String, List<Market>> grouped = rank.getRanking();

        assertEquals(1, grouped.get("group1").size());
        assertEquals("TRUMP_USDT", grouped.get("group1").get(0).getMarket());

        assertEquals(1, grouped.get("group2").size());
        assertEquals("OMG_USDT", grouped.get("group2").get(0).getMarket());

        assertEquals(1, grouped.get("group3").size());
        assertEquals("ETH_EUR", grouped.get("group3").get(0).getMarket());
    }


    private OrderBook createOrderBook(String tickerId, Double askPrice, Double bidPrice) {
        OrderBook ob = new OrderBook();
        ob.setTicker_id(tickerId);
        if (askPrice != null) {
            ob.setAsks(List.of(new Offer(askPrice, 1)));
        } else {
            ob.setAsks(Collections.emptyList());
        }
        if (bidPrice != null) {
            ob.setBids(List.of(new Offer(bidPrice, 1)));
        } else {
            ob.setBids(Collections.emptyList());
        }
        return ob;
    }
}
