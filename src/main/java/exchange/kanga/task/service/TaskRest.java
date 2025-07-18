package exchange.kanga.task.service;

import exchange.kanga.task.data.Rank;
import exchange.kanga.task.impl.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spread")
public class TaskRest {

    @Autowired
    Calculation calculation;


    @PostMapping("/calculate")
    @ResponseBody
    public ResponseEntity<String> calculateRank() {
        calculation.calcRank();
        return ResponseEntity.status(HttpStatus.OK).body("done");
    }

    @GetMapping("/ranking")
    @ResponseBody
    public  ResponseEntity<Rank> showRank() {
        var rank = calculation.getRank();
        if(rank == null ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {

            return ResponseEntity.status(HttpStatus.OK).body(rank);
        }

    }


}
