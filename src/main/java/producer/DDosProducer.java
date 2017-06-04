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

    private Random random;
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
        random = new Random();
        count = 1L;
        // Generate a number of botnet zombies
        for(int i = 0; i < numberOfZombies; i++) {
            StringBuilder builder = new StringBuilder();
            builder.append(random.nextInt(256) + "." +
                    random.nextInt(256) + "." +
                    random.nextInt(256) + "." +
                    random.nextInt(256));

            botnetIps.add(builder.toString());
        }
    }

    @Override
    protected String generate() {
        StringBuilder builder = new StringBuilder();
        if(count % numberOfZombies == 0) {
            //Create a random IP simulating a normal request.
            builder.append(random.nextInt(256) + "." +
                    random.nextInt(256) + "." +
                    random.nextInt(256) + "." +
                    random.nextInt(256));
        } else {
            //Choose a ip randomly from the zombie list
            builder.append("BOT " + botnetIps.get(random.nextInt(100)));
        }


        builder.append(" - - [");
        builder.append(LocalDateTime.now().minusSeconds(1)
                .format(DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss")));
        builder.append("] GET / HTTP/1.0 200 1783");
        count += 1;
        return builder.toString();
    }
}
