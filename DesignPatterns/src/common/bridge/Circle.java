package common.bridge;

import common.interfaces.*;

public class Circle extends Shape {
    private int radius, x, y;

    public Circle(int radius, int x, int y, DrawAPI drawAPI) {
        super(drawAPI);
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public void draw() {
        drawAPI.drawCircle(radius, x, y);
    }
}
