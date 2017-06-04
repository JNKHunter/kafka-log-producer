package producer;

import producer.AbstractProducer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/3/17.
 */
public class NormalProducer extends AbstractProducer {

    private Random random = new Random();

    public NormalProducer(String bootstrapServers, String topicName,
                          int initialDelay, int period, TimeUnit timeUnit) {
        super(bootstrapServers, topicName, initialDelay, period, timeUnit);
    }

    @Override
    protected String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256));
        builder.append(" - - [");
        builder.append(LocalDateTime.now().minusSeconds(1)
                .format(DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss")));
        builder.append("] GET / HTTP/1.0 200 1783");
        return builder.toString();
    }
}
