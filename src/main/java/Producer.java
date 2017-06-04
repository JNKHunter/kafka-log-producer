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
public class Producer<T extends MessageGenerator> implements Runnable {
    private String topicName;
    private String bootstrapServers;
    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private Properties props;
    private Random r = new Random();
    private T generator;

    Class<T> clazz;

    Producer(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        generator = clazz.newInstance();
    }

    public Producer(String bootstrapServers, String topicName) {
        this.topicName = topicName;
        props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        producer = new KafkaProducer<>(props);
        Random r = new Random();

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this,0,1, TimeUnit.MILLISECONDS);
    }

    public void run(){
        producer.send(new ProducerRecord<>(topicName, generator.generate()));
    }

    public void closeProducer() {
        producer.close();
    }

    /*public static void main(String[] args) throws Exception {
        String bootstrapServers;
        if(args.length == 0) {
            bootstrapServers = "0.0.0.0:9292";
        }else{
            bootstrapServers = args[0];
        }

        Producer testProducer = new Producer(bootstrapServers);

    }*/
}
