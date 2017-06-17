package producer;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by John on 6/12/17.
 */
public class ProducerTest {
    DDosProducer dDosProducer;
    NormalProducer normalProducer;

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
        int key = dDosProducer.getCurKey();
        String val = dDosProducer.getCurVal();
        int partition = 0;

        assertEquals(partition + "," + key + "|" + val, dDosProducer.getKeyValPair(partition));
    }

    @Test
    public void testNormalKeyValPair() {
        normalProducer.generateKeyPair();
        int key = normalProducer.getCurKey();
        String val = normalProducer.getCurVal();
        int partition = 0;

        assertEquals(partition + "," + key + "|" + val, normalProducer.getKeyValPair(partition));
    }
}