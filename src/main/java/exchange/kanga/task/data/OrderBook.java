package exchange.kanga.task.data;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderBook {

    String ticker_id;
    Timestamp timestamp;
    List<Offer>  bids;
    List<Offer>  asks;
}
