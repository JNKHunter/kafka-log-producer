package producer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/4/17.
 */
public class DDosProducer extends AbstractProducer {


    private int numberOfZombies;
    private List<String> botnetIps;
    private Long count;


    public DDosProducer(String bootstrapServers, String topicName,
                           int initialDelay, int period, TimeUnit timeUnit) {
        super(bootstrapServers, topicName, initialDelay, period, timeUnit);

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
            curKey = hostsIds.get(0);
            //Choose a ip randomly from the zombie list
            curVal = botnetIps.get(random.nextInt(numberOfZombies));
        }
    }

}
