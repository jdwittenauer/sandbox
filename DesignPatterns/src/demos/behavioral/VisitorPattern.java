package demos.behavioral;

import common.behavioral.visitor.*;
import common.interfaces.*;

public class VisitorPattern {
    public static void run() {
        System.out.println("Running visitor pattern demo...");

        ComputerPart computer = new Computer();
        computer.accept(new ComputerPartDisplayVisitor());

        System.out.println("");
    }
}
