package dk.sdu.mmmi.cbse.scoringsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@CrossOrigin
public class ScoringSystem {

    private Long totalscore = 0L;

    public static void main(String[] args) {
        SpringApplication.run(ScoringSystem.class, args);
    }

    @GetMapping("/score")
    public Long getScore() {
        return totalscore;
    }

    @PutMapping("score/update/{score}")
    public Long updateScore(@PathVariable(value = "score") Long score) {
        totalscore += score;
        return totalscore;
    }
}
