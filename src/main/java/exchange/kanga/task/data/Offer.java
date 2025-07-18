package exchange.kanga.task.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class Offer {
    double price;
    double amount;
}
