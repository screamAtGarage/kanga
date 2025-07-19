package exchange.kanga.task.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair {

    String ticker_id;
    String base;
    String target;

}
