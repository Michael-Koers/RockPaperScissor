package rockpaperscissor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class API {

    private MeterRegistry meterRegistry;

    private static final String[] RockPaperScissorArray = new String[]{"Rock", "Paper", "Scissor"};
    private final Counter rockCount;
    private final Counter paperCount;
    private final Counter scissorCount;

    private final String rockCounterName = "java_rock_counts";
    private final String paperCounterName = "java_paper_counts";
    private final String scissorCounterName = "java_scissor_counts";

    public API(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        rockCount = this.meterRegistry.counter(rockCounterName);
        paperCount = this.meterRegistry.counter(paperCounterName);
        scissorCount = this.meterRegistry.counter(scissorCounterName);
    }

    @GetMapping("/rockpaperscissor")
    public String rockPaperScissor() {
        Random rnd = new Random();

        int index = rnd.nextInt(RockPaperScissorArray.length);
        String result = RockPaperScissorArray[index];

        if (result.equalsIgnoreCase("rock")) {
            this.meterRegistry.counter("java_rock_counts").increment();
            rockCount.increment();
        } else if (result.equalsIgnoreCase("paper")) {
            paperCount.increment();
        } else {
            scissorCount.increment();
        }

        return result;
    }

}

