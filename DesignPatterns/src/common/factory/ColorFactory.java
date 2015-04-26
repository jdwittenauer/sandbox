package common.factory;

import common.interfaces.*;

public class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if(color == null){
            return null;
        }

        if(color.equalsIgnoreCase("RED")) {
            return new Red();
        }
        else if(color.equalsIgnoreCase("GREEN")) {
            return new Green();
        }
        else if(color.equalsIgnoreCase("BLUE")) {
            return new Blue();
        }

        return null;
    }
}
