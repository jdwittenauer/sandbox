package demos.behavioral;

import common.behavioral.template.*;

public class TemplatePattern {
    public static void run() {
        System.out.println("Running template pattern demo...");

        Game game = new Cricket();
        game.play();

        System.out.println();

        game = new Football();
        game.play();

        System.out.println("");
    }
}
