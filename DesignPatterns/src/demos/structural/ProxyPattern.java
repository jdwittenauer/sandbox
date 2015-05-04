package demos.structural;

import common.interfaces.*;
import common.structural.proxy.*;

public class ProxyPattern {
    public static void run() {
        System.out.println("Running proxy pattern demo...");

        Image image = new ProxyImage("test_10mb.jpg");

        // image will be loaded from disk
        image.display();
        System.out.println("");

        // image will not be loaded from disk
        image.display();

        System.out.println("");
    }
}
