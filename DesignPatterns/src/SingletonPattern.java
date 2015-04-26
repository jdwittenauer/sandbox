import common.singleton.*;

public class SingletonPattern {
    public static void run() {
        System.out.println("Running singleton pattern demo...");

        SingleObject singleton = SingleObject.getInstance();
        singleton.showMessage();

        System.out.println("");
    }
}
