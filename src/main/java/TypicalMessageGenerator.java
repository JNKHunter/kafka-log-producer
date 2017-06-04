import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Created by John on 6/3/17.
 */
public class TypicalMessageGenerator implements MessageGenerator {

    private Random random = new Random();

    @Override
    public String generate() {
        
        StringBuilder builder = new StringBuilder();
        builder.append(random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256));
        builder.append(" - - [");
        builder.append(LocalDateTime.now().minusSeconds(1).format(DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss")));
        builder.append("] GET / HTTP/1.0 200 1783");
        return builder.toString();
    }
}