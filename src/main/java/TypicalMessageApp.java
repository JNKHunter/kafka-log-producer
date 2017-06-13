import producer.DDosProducer;
import producer.NormalProducer;

import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/3/17.
 * App takes two command line args.
 * arg[0]: address and port # of Kafka bootstrapServers
 * arg[1]: topic name to send messages to
 */
public class TypicalMessageApp {
    public static void main(String[] args) {
        String bootstrapServers;
        String topic;
        //"normal" or "ddos". Will add proper DI, but this will do for now.
        String type;
        int partitions;

        if(args.length == 0) {
            bootstrapServers = "0.0.0.0:9092";
            topic = "logs";
            type = "normal";
            partitions = 2;
        }else{
            bootstrapServers = args[0];
            topic = args[1];
            type = args[2];
            partitions = Integer.parseInt(args[3]);
        }
        
        if(type.equals("normal")) {
            NormalProducer producer = new NormalProducer(bootstrapServers, topic,
                    0, 50, TimeUnit.MICROSECONDS,partitions);
        } else {
            DDosProducer ddosProducer = new DDosProducer(bootstrapServers, topic,
                    0,50, TimeUnit.MICROSECONDS, partitions);
        }
    }
}
