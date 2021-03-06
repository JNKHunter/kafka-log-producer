package producer;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by John on 6/12/17.
 */
public class ProducerTest {
    private DDosProducer dDosProducer;
    private NormalProducer normalProducer;
    private int partition = 0;

    @Before
    public void setUp() throws Exception {
        dDosProducer = new DDosProducer("0.0.0.0:9092", "topic",
                0,1, TimeUnit.SECONDS, 5);

        normalProducer = new NormalProducer("0.0.0.0:9092", "topic",
                0,1, TimeUnit.SECONDS, 5);
    }

    @Test
    public void testDDosKeyValPair() {
        dDosProducer.generateKeyPair();
        String key = dDosProducer.hostIps[dDosProducer.getCurKey()];
        String val = dDosProducer.getCurVal();

        assertEquals(partition + "," + key + "," + val, dDosProducer.getMessage(partition));
    }

    @Test
    public void testNormalKeyValPair() {
        normalProducer.generateKeyPair();
        String key = dDosProducer.hostIps[normalProducer.getCurKey()];
        String val = normalProducer.getCurVal();

        assertEquals(partition + "," + key + "," + val, normalProducer.getMessage(partition));
    }
}