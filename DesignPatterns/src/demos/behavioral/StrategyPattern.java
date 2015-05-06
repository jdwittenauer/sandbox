package demos.behavioral;

import common.behavioral.strategy.*;

public class StrategyPattern {
    public static void run() {
        System.out.println("Running strategy pattern demo...");

        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubtract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));

        System.out.println("");
    }
}
