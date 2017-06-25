import producer.DDosProducer;
import producer.NormalProducer;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/3/17.
 */
public class TypicalMessageApp {
    protected  static ResourceBundle defaults = ResourceBundle.getBundle("Defaults");

    public static void main(String[] args) {
        String bootstrapServers;
        String topic;
        String type;
        int numberOfHosts;

        if(args.length == 0) {
            bootstrapServers = defaults.getString("defaults.boostrapServers");
            topic = defaults.getString("defaults.topic");
            type = defaults.getString("defaults.trafficType");
            numberOfHosts = Integer.parseInt(defaults.getString("defaults.numberOfHosts"));
        }else{
            bootstrapServers = args[0];
            topic = args[1];
            type = args[2];
            numberOfHosts = Integer.parseInt(args[3]);
        }
        
        if(type.equals(defaults.getString("defaults.trafficType"))) {
            NormalProducer producer = new NormalProducer(bootstrapServers, topic,
                    0, 1, TimeUnit.MICROSECONDS, numberOfHosts);
        } else {
            DDosProducer ddosProducer = new DDosProducer(bootstrapServers, topic,
                    0,1, TimeUnit.MICROSECONDS, numberOfHosts);
        }
    }
}
