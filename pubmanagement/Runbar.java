package pubmanagement;

import utils.Gender;
import java.util.HashMap;
import java.util.Map;
// import java.util.List;
// import java.util.ArrayList;

public class Runbar {
    public static void main(String[] args) {
        // Create drinks
        Drink beer = new Drink("Beer", 5.00, 2.00, 3);
        Drink wine = new Drink("Wine", 7.00, 3.00, 4);
        Drink water = new Drink("Water", 1.00, 0.50);
        
        // Create a bar with a Patronne and Bartender
        Patronne marie = new Patronne("Marie", "Boss", 500.0, "Cheers!", "At Marie's Pub");
        Bartender paul = new Bartender("Paul", "Bart", 100.0, "Let's get this party started!");
        Bar bar = new Bar("At Marie's Pub", marie, paul);

        // Create and add servers
        Server alice = new Server("Alice", "Ally", 50.0, "Serving up!", Gender.FEMALE, 8);
        Server john = new Server("John", "Johnny", 60.0, "Here we go!", Gender.MALE, 6);
        bar.addServer(alice);
        bar.addServer(john);

        // Create clients
        Client client1 = new Client("Client1", "C1", 20.0, "Yay!", Gender.MALE, beer, water, "red");
        Client client2 = new Client("Client2", "C2", 10.0, "Woo!", Gender.FEMALE, wine, water, "gold");
        Client client3 = new Client("Client3", "C3", 15.0, "Hooray!", Gender.MALE, beer, water, "blue");
        
        // Add clients to the bar
        bar.addClient(client1);
        bar.addClient(client2);
        bar.addClient(client3);

        // Simulate serving drinks
        bar.serveDrink(client1, beer);
        bar.serveDrink(client2, wine);
        bar.serveDrink(client3, water);

        // Test drink methods
        client1.receiveDrink(water);  // Client1 receives water
        client2.receiveDrink(beer);    // Client2 tries to receive beer (should print that they can't)

        // Test paying for drinks
        client1.pay(5.00);  // Client1 pays for beer
        client2.pay(8.00);  // Client2 pays for wine
        client3.pay(1.00);  // Client3 pays for water

        // Simulate a general round
        bar.offerGeneralRound();

        // Test changing gender
        client1.changeGender(Gender.FEMALE, "pink");
        client1.introduce();  // Verify that the change has taken effect

        // Ban a client
        marie.banClient(client1, bar);
        bar.serveDrink(client1, beer); // Verify that the client cannot be served

        // Collect cash from the bartender
        marie.collectCashFromRegister(paul, 50.0);  // Collect if there is excess cash

        // Simulate stock management
        Map<Drink, Integer> order = new HashMap<>();
        order.put(beer, 30);  // Order 30 beers
        order.put(wine, 20);  // Order 20 wines

        // Supplier delivers drinks
        Supplier jack = new Supplier("Jack", "Supplier", 300.0, "Delivery time!");
        jack.receiveOrder(paul, bar, order);  // Jack delivers drinks

        // Check stock after delivery
        System.out.println("Current stock of beer: " + bar.getDrinkStock(beer));
        System.out.println("Current stock of wine: " + bar.getDrinkStock(wine));
    }
}
