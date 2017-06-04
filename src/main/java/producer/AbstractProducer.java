package producer;

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
 * TODO: Generation should not be the concern of this class.
 * Possibly break the generation functionality out to another class.
 */
public abstract class AbstractProducer {
    private String topicName;
    private String bootstrapServers;
    private int initialDelay;
    private int period;
    private TimeUnit timeUnit;
    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private Properties props;
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
        this.bootstrapServers = bootstrapServers;
        this.initialDelay = initialDelay;
        this.timeUnit = timeUnit;
        this.period = period;

        init();

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this.runnable,this.initialDelay,this.period, this.timeUnit);
    }

    protected abstract String generate();

    /** Hook for making any initializations before we run the executor. Override as needed. **/
    protected void init(){

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
    };

    public void closeProducer() {
        producer.close();
    }
}