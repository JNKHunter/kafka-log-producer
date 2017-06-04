package producer;

import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/4/17.
 */
public class DDosProducer {

    public DDosProducer(String bootstrapServers, String topicName,
                           int initialDelay, int period, TimeUnit timeUnit) {
        super(bootstrapServers, topicName, initialDelay, period, timeUnit);
    }

    
}
