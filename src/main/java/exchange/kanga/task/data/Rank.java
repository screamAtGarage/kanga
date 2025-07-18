package exchange.kanga.task.data;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.TreeMap;

@Data
public class Rank {

    Instant timestamp;
    TreeMap<String, List<Market>> ranking = new TreeMap<>();

    public Rank() {
        timestamp = Instant.now();
    }
}
