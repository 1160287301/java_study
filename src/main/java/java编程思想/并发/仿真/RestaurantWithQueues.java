//: concurrency/restaurant2/RestaurantWithQueues.java
// {Args: 5}
package java编程思想.并发.仿真;

import java编程思想.枚举类型.使用接口组织枚举.Course;
import java编程思想.枚举类型.使用接口组织枚举.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java编程思想.net.mindview.util.Print.print;

// This is given to the waiter, who gives it to the chef:
class Order { // (A data-transfer object)
    private static int counter = 0;
    private final int id = counter++;
    private final Customer1 Customer1;
    private final WaitPerson waitPerson;
    private final Food food;

    public Order(Customer1 cust, WaitPerson wp, Food f) {
        Customer1 = cust;
        waitPerson = wp;
        food = f;
    }

    public Food item() {
        return food;
    }

    public Customer1 getCustomer1() {
        return Customer1;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    public String toString() {
        return "Order: " + id + " item: " + food +
                " for: " + Customer1 +
                " served by: " + waitPerson;
    }
}

// This is what comes back from the chef:
class Plate {
    private final Order order;
    private final Food food;

    public Plate(Order ord, Food f) {
        order = ord;
        food = f;
    }

    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    public String toString() {
        return food.toString();
    }
}

class Customer1 implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final WaitPerson waitPerson;
    // Only one course at a time can be received:
    private SynchronousQueue<Plate> placeSetting =
            new SynchronousQueue<Plate>();

    public Customer1(WaitPerson w) {
        waitPerson = w;
    }

    public void
    deliver(Plate p) throws InterruptedException {
        // Only blocks if Customer1 is still
        // eating the previous course:
        placeSetting.put(p);
    }

    public void run() {
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                waitPerson.placeOrder(this, food);
                // Blocks until course has been delivered:
                print(this + "eating " + placeSetting.take());
            } catch (InterruptedException e) {
                print(this + "waiting for " +
                        course + " interrupted");
                break;
            }
        }
        print(this + "finished meal, leaving");
    }

    public String toString() {
        return "Customer1 " + id + " ";
    }
}

class WaitPerson implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    BlockingQueue<Plate> filledOrders =
            new LinkedBlockingQueue<Plate>();

    public WaitPerson(Restaurant rest) {
        restaurant = rest;
    }

    public void placeOrder(Customer1 cust, Food food) {
        try {
            // Shouldn't actually block because this is
            // a LinkedBlockingQueue with no size limit:
            restaurant.orders.put(new Order(cust, this, food));
        } catch (InterruptedException e) {
            print(this + " placeOrder interrupted");
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Blocks until a course is ready
                Plate plate = filledOrders.take();
                print(this + "received " + plate +
                        " delivering to " +
                        plate.getOrder().getCustomer1());
                plate.getOrder().getCustomer1().deliver(plate);
            }
        } catch (InterruptedException e) {
            print(this + " interrupted");
        }
        print(this + " off duty");
    }

    public String toString() {
        return "WaitPerson " + id + " ";
    }
}

class Chef implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    private static Random rand = new Random(47);

    public Chef(Restaurant rest) {
        restaurant = rest;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Blocks until an order appears:
                Order order = restaurant.orders.take();
                Food requestedItem = order.item();
                // Time to prepare order:
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order, requestedItem);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            print(this + " interrupted");
        }
        print(this + " off duty");
    }

    public String toString() {
        return "Chef " + id + " ";
    }
}

class Restaurant implements Runnable {
    private List<WaitPerson> waitPersons =
            new ArrayList<WaitPerson>();
    private List<Chef> chefs = new ArrayList<Chef>();
    private ExecutorService exec;
    private static Random rand = new Random(47);
    BlockingQueue<Order>
            orders = new LinkedBlockingQueue<Order>();

    public Restaurant(ExecutorService e, int nWaitPersons,
                      int nChefs) {
        exec = e;
        for (int i = 0; i < nWaitPersons; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }
        for (int i = 0; i < nChefs; i++) {
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // A new Customer1 arrives; assign a WaitPerson:
                WaitPerson wp = waitPersons.get(
                        rand.nextInt(waitPersons.size()));
                Customer1 c = new Customer1(wp);
                exec.execute(c);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            print("Restaurant interrupted");
        }
        print("Restaurant closing");
    }
}

public class RestaurantWithQueues {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec, 5, 2);
        exec.execute(restaurant);
        if (args.length > 0) // Optional argument
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        else {
            print("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
} /* Output: (Sample)
WaitPerson 0 received SPRING_ROLLS delivering to Customer1 1
Customer1 1 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer1 0
Customer1 0 eating SPRING_ROLLS
WaitPerson 0 received BURRITO delivering to Customer1 1
Customer1 1 eating BURRITO
WaitPerson 3 received SPRING_ROLLS delivering to Customer1 2
Customer1 2 eating SPRING_ROLLS
WaitPerson 1 received SOUP delivering to Customer1 3
Customer1 3 eating SOUP
WaitPerson 3 received VINDALOO delivering to Customer1 0
Customer1 0 eating VINDALOO
WaitPerson 0 received FRUIT delivering to Customer1 1
...
*///:~
