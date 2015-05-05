package demos.behavioral;

import common.behavioral.iterator.*;
import common.interfaces.*;

public class IteratorPattern {
    public static void run() {
        System.out.println("Running iterator pattern demo...");

        NameRepository namesRepository = new NameRepository();

        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            String name = (String)iter.next();
            System.out.println("Name : " + name);
        }

        System.out.println("");
    }
}
