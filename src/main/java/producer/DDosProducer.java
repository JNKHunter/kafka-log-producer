package producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/4/17.
 */
public class DDosProducer extends AbstractProducer {


    private int numberOfZombies;
    private List<String> botnetIps;
    private Long count;


    public DDosProducer(String bootstrapServers, String topicName,
                           int initialDelay, int period, TimeUnit timeUnit, int partitions, int numberOfHosts) {
        super(bootstrapServers, topicName, initialDelay, period, timeUnit, partitions, numberOfHosts);
    }

    @Override
    protected void init(){
        super.init();
        numberOfZombies = 100;
        botnetIps = new ArrayList<>();
        count = 1L;
        // Generate a number of botnet zombies
        for(int i = 0; i < numberOfZombies; i++) {
            StringBuilder builder = new StringBuilder();
            builder.append(getRandomIp());
            botnetIps.add(builder.toString());
        }
    }

    @Override
    protected void generateKeyPair() {
        count += 1;
        if(count % numberOfZombies == 0) {
            //Create a random IP simulating a normal request.
            curKey = getRandomHost();
            curVal =  getRandomIp();
        } else {
            //First host is getting ddos'd
            curKey = 0;
            //Choose a ip randomly from the zombie list
            curVal = botnetIps.get(random.nextInt(numberOfZombies));
        }
    }

}
