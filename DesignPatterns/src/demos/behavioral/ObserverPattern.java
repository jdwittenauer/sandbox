package demos.behavioral;

import common.behavioral.observer.*;

public class ObserverPattern {
    public static void run() {
        System.out.println("Running observer pattern demo...");

        Subject subject = new Subject();

        new HexaObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);

        System.out.println("");
    }
}
