import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

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
public class LogProducer {
    private static String topicName = "test";
    private static Producer<String, String> producer;
    private static Random r = new Random();

    private Properties props;

    public LogProducer(String bootstrapServers) {

        props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        producer = new KafkaProducer<>(props);
        
        Random r = new Random();

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(LogProducer::sendMessage,0,1, TimeUnit.MILLISECONDS);
        //producer.close();
    }

    private static void sendMessage(){
        producer.send(new ProducerRecord<>(topicName, generateIp()));
    }

    private static String generateIp() {
        StringBuilder builder = new StringBuilder();
        builder.append(r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256));
        builder.append(" - - [");
        builder.append(LocalDateTime.now().minusSeconds(1).format(DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss")));
        builder.append("] GET / HTTP/1.0 200 1783");
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        String bootstrapServers;
        if(args.length == 0) {
            bootstrapServers = "0.0.0.0:9292";
        }else{
            bootstrapServers = args[0];
        }

        LogProducer testProducer = new LogProducer(bootstrapServers);

    }
}
