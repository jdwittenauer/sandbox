package demos.behavioral;

import common.behavioral.mediator.*;

public class MediatorPattern {
    public static void run() {
        System.out.println("Running mediator pattern demo...");

        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi ohn!");
        john.sendMessage("Hello Robert!");

        System.out.println("");
    }
}
