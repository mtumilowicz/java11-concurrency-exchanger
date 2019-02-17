import org.junit.Test;

import java.util.concurrent.Exchanger;

/**
 * Created by mtumilowicz on 2019-02-17.
 */
public class Simulation {
    
    @Test
    public void simulation() throws InterruptedException {
        var restaurant = new Exchanger<Gift>();
        var michal = new Person("michal", restaurant);
        var ania = new Person("ania", restaurant);
        
        michal.start();
        ania.start();
        
        michal.join();
        ania.join();
    }
}
