import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Created by mtumilowicz on 2019-02-17.
 */
public class Person extends Thread {

    private final String name;
    private final Exchanger<Gift> exchanger;

    Person(String name, Exchanger<Gift> exchanger) {
        this.name = name;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is waiting for a gift...");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(50) + 3);
            var receivedGift = exchanger.exchange(new Gift("from " + name));
            System.out.println(name + " received gift " + receivedGift.name);
        } catch (InterruptedException e) {
            // not used
        }
    }
}
