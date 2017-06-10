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
    protected String generateValue() {
        StringBuilder builder = new StringBuilder();
        builder.append(getRandomHost());
        builder.append(",");
        builder.append(getRandomIp());
        return builder.toString();
    }
}
