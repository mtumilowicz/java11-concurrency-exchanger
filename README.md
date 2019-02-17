# java11-concurrency-exchanger

# preface
* https://github.com/mtumilowicz/java11-concurrency-cyclicbarrier
* similar to barrier
* exchangers lets two threads wait for each other and exchange
some object (information)
* use case: from time to time some parts of application have
to exchange information

# java
* exchanger in java is represented by `Exchanger<V>`
* only one constructor: no args
* `V` is a type of exchanged object
* methods:
    * `V exchange(V x)` - waits for another thread to arrive at this exchange 
        point and then transfers the given object to it, receiving its object
        in return
    * `V exchange(V x, long timeout, TimeUnit unit)`
    
# project description
Two persons exchanging gifts on the meeting.
1. person
    ```
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
    ```
1. gift is as simple as it can be
    ```
    class Gift {
        final String name;
    
        Gift(String name) {
            this.name = name;
        }
    }
    ```
1. simulation
    ```
    var restaurant = new Exchanger<Gift>();
    var michal = new Person("michal", restaurant);
    var ania = new Person("ania", restaurant);
    
    michal.start();
    ania.start();
    
    michal.join();
    ania.join();
    ```
    may produce output
    ```
    michal is waiting for a gift...
    ania is waiting for a gift...
    ania received gift from michal
    michal received gift from ania
    ```