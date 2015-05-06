package demos.behavioral;

import common.behavioral.state.*;

public class StatePattern {
    public static void run() {
        System.out.println("Running state pattern demo...");

        Context context = new Context();

        StartState startState = new StartState();
        startState.doAction(context);

        System.out.println(context.getState().toString());

        StopState stopState = new StopState();
        stopState.doAction(context);

        System.out.println(context.getState().toString());

        System.out.println("");
    }
}
