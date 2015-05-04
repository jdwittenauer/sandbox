package demos;

import common.command.*;

public class CommandPattern {
    public static void run() {
        System.out.println("Running command pattern demo...");

        Stock abcStock = new Stock();

        BuyStock buyStockOrder = new BuyStock(abcStock);
        SellStock sellStockOrder = new SellStock(abcStock);

        Broker broker = new Broker();
        broker.takeOrder(buyStockOrder);
        broker.takeOrder(sellStockOrder);

        broker.placeOrders();

        System.out.println("");
    }
}
