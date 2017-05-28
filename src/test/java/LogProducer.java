import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Created by John on 5/28/17.
 */
public class LogProducer {
    private String topicName = "test";
    private String value = "hello from my custom producer";
    private Producer<String, String> producer;

    private Properties props;

    public LogProducer() {
        topicName = "test";
        value = "hello from my custom producer";

        props = new Properties();
        props.setProperty("bootstrap.servers", "172.31.74.41:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }
}
