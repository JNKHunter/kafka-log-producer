/**
 * Created by John on 6/3/17.
 * App takes two command line args.
 * arg[0]: address and port # of Kafka bootstrapServers
 * arg[1]: topic name to send messages to
 *
 */
public class TypicalMessageApp {
    public static void main(String[] args) {
        String bootstrapServers;
        String topic;
        if(args.length == 0) {
            bootstrapServers = "0.0.0.0:9092";
            topic = "test";
        }else{
            bootstrapServers = args[0];
            topic = args[1];
        }

        TypicalProducer producer = new TypicalProducer(bootstrapServers, topic);
    }
}
