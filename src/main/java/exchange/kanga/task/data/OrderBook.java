package exchange.kanga.task.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderBook {

    public String ticker_id;
    public Timestamp timestamp;

}
