package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.util.ArrayList;
import java.util.List;
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
    protected Random random;
    protected int numberOfHosts;
    protected int curKey;
    protected String curVal;
    private int numberOfPartitions = 1;
    private int count = 0;

    /**
     * String bootstrapServers: ip and port number of kafka brokers
     * String topicName: kafka topic name to publish to
     * int initialDelay: amount of time to wait before starting producer
     * int period: number of time units between record production
     * TimeUnit timeUnit: unit of time (seconds, milliseconds etc)
     */
    public AbstractProducer(String bootstrapServers, String topicName,
                            int initialDelay, int period, TimeUnit timeUnit, int numberOfHosts) {

        this.topicName = topicName;
        this.bootstrapServers = bootstrapServers;
        this.initialDelay = initialDelay;
        this.timeUnit = timeUnit;
        this.period = period;
        this.numberOfHosts = numberOfHosts;

        init();
        startExecutors();
    }
    
    protected abstract void generateKeyPair();

    public int getCurKey() {
        return curKey;
    }

    public String getCurVal() {
        return curVal;
    }

    public String getKeyValPair(int partition) {
        return partition + "," + curKey + "," + curVal;
    }

    protected void startExecutors(){
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this.runnable,this.initialDelay,this.period, this.timeUnit);
    }

    /** Hook for making any initializations before we run the executor. Override as needed. **/
    protected void init(){
        random = new Random();
        props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
        numberOfPartitions = producer.partitionsFor(topicName).size();

        runnable = () -> {
            count += 1;
            generateKeyPair();
            int partitionKey = ((curKey + count) % numberOfPartitions);
            producer.send(new ProducerRecord<String, String>(topicName,
                    partitionKey, Integer.toString(partitionKey),
                    getKeyValPair(partitionKey)));
        };
    };

    protected String getRandomIp() {
        return random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256) + "." +
                random.nextInt(256);
    }

    protected int getRandomHost() {
        return random.nextInt(numberOfHosts);
    }

    public void closeProducer() {
        producer.close();
    }
}