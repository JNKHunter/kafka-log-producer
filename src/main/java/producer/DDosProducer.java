package producer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 6/4/17.
 */
public class DDosProducer extends AbstractProducer {


    private final int NUMBER_OF_ZOMBIES = 1000;
    private final int ATTACK_HOST = 0;
    private List<String> botnetIps;
    private Long count;


    public DDosProducer(String bootstrapServers, String topicName,
                           int initialDelay, int period, TimeUnit timeUnit, int numberOfHosts) {
        super(bootstrapServers, topicName, initialDelay, period, timeUnit, numberOfHosts);
    }

    @Override
    protected void init(){
        super.init();
        botnetIps = new ArrayList<>();
        count = 1L;
        // Generate a number of botnet zombies
        for(int i = 0; i < NUMBER_OF_ZOMBIES; i++) {
            StringBuilder builder = new StringBuilder();
            builder.append(getRandomIp());
            botnetIps.add(builder.toString());
        }
    }

    @Override
    protected void generateKeyPair() {
        count += 1;
        if(count % NUMBER_OF_ZOMBIES == 0) {
            //Create a random IP simulating a normal request.
            curKey = getRandomHost();
            curVal =  getRandomIp();
        } else {
            //Simulation of first host under attack
            curKey = ATTACK_HOST;
            //Choose a ip randomly from the zombie list
            curVal = botnetIps.get(random.nextInt(NUMBER_OF_ZOMBIES));
        }
    }
}
