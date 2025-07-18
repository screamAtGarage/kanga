package exchange.kanga.task.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spread")
public class TaskRest {
//    @GetMapping("")


    @PostMapping("/calculate")
    public String calculateRank() {
        return "ok";
    }


}
