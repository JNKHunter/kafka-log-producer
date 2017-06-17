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
                          int initialDelay, int period, TimeUnit timeUnit, int numberOfHosts) {
        super(bootstrapServers, topicName, initialDelay, period, timeUnit, numberOfHosts);
    }

    @Override
    protected void generateKeyPair() {
        curKey = getRandomHost();
        curVal = getRandomIp();
    }
}
