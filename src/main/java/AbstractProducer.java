import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 5/28/17.
 */
public abstract class AbstractProducer {
    private String topicName;
    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private Properties props;
    private Random r = new Random();
    private Runnable runnable;

    /**
     * String bootstrapServers: ip and port number of kafka brokers
     * String topicName: kafka topic name to publish to
     * int initialDelay: amount of time to wait before starting producer
     * int period: number of time units between record production
     * TimeUnit timeUnit: unit of time (seconds, milliseconds etc)
     */
    public AbstractProducer(String bootstrapServers, String topicName,
                            int initialDelay, int period, TimeUnit timeUnit) {
        this.topicName = topicName;
        props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        producer = new KafkaProducer<>(props);

        runnable = new Runnable() {
            @Override
            public void run() {
                producer.send(new ProducerRecord(topicName, generate()));
            }
        };

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable,initialDelay,period, timeUnit);
    }

    abstract String generate();

    public void closeProducer() {
        producer.close();
    }
}