package common.creational.builder;

import common.interfaces.*;

public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
