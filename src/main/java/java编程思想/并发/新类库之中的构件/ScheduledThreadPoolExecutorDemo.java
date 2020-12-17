package java编程思想.并发.新类库之中的构件;//: concurrency/ScheduledThreadPoolExecutorDemo.java
// Rewriting innerclasses/GreenhouseController.java
// to use a ScheduledThreadPoolExecutor.
// {Args: 5000}

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在第10章中介绍过可以应用于假想温室的控制系统的示例，它可以控制各种设施的开关，或者是对它们进行调节。这可以被看作是一种并发问题，每个期望的温室事件都是一个在预定时间运行的任务。ScheduledThreadPoolExecutor提供了解决该问题的服务。通过使用schedule()（运行一次任务）或者scheduleAtFixedRate()（每隔规则的时间重复执行任务），你可以将Runnable对象设置为在将来的某个时刻执行。将下面的程序与在第10章中使用的方式相比，就会注意到，当你使用像ScheduledThreadPoolExecutor这样的预定义工具时，要简单许多：
 * 这个版本重新组织了代码，并且添加了新的特性：收集温室内的温度和湿度读数。DataPoint可以持有并显示单个的数据段，而CollectData是被调度的任务，它在每次运行时，都可以产生仿真数据，并将其添加到Greenhouse的List<DataPoint>中。注意，volatile和synchronized在适当的场合都得到了应用，以防止任务之间的互相干涉。在持有DataPoint的List中的所有方法都是synchronized的，这是因为在List被创建时，使用了java.util.Collections实用工具synchronizedList()。
 */
public class ScheduledThreadPoolExecutorDemo {
    private volatile boolean light = false;
    private volatile boolean water = false;
    // 恒温器；自动调温器
    private String thermostat = "Day";

    public synchronized String getThermostat() {
        return thermostat;
    }

    public synchronized void setThermostat(String value) {
        thermostat = value;
    }

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);

    public void schedule(Runnable event, long delay) {
        scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
    }

    public void repeat(Runnable event, long initialDelay, long period) {
        scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    class LightOn implements Runnable {
        public void run() {
            // Put hardware control code here to
            // physically turn on the light.
            System.out.println("Turning on lights");
            light = true;
        }
    }

    class LightOff implements Runnable {
        public void run() {
            // Put hardware control code here to
            // physically turn off the light.
            System.out.println("Turning off lights");
            light = false;
        }
    }

    class WaterOn implements Runnable {
        public void run() {
            // Put hardware control code here.
            System.out.println("Turning greenhouse water on");
            water = true;
        }
    }

    class WaterOff implements Runnable {
        public void run() {
            // Put hardware control code here.
            System.out.println("Turning greenhouse water off");
            water = false;
        }
    }

    class ThermostatNight implements Runnable {
        public void run() {
            // Put hardware control code here.
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        public void run() {
            // Put hardware control code here.
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        public void run() {
            System.out.println("Bing!");
        }
    }

    class Terminate implements Runnable {
        public void run() {
            System.out.println("Terminating");
            scheduler.shutdownNow();
            // Must start a separate task to do this job,
            // since the scheduler has been shut down:
            new Thread() {
                public void run() {
                    for (DataPoint d : data)
                        System.out.println(d);
                }
            }.start();
        }
    }

    // New feature: data collection
    static class DataPoint {
        final Calendar time;
        final float temperature;
        // n. [气象] 湿度；湿气
        final float humidity;

        public DataPoint(Calendar d, float temp, float hum) {
            time = d;
            temperature = temp;
            humidity = hum;
        }

        public String toString() {
            return time.getTime() +
                    String.format(
                            " temperature: %1$.1f humidity: %2$.2f",
                            temperature, humidity);
        }
    }

    private Calendar lastTime = Calendar.getInstance();

    { // Adjust date to the half hour
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 00);
    }

    private float lastTemp = 65.0f;
    // n. 方向；指导；趋势；用法说明
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random rand = new Random(47);
    List<DataPoint> data = Collections.synchronizedList(new ArrayList<DataPoint>());

    class CollectData implements Runnable {
        public void run() {
            System.out.println("Collecting data");
            synchronized (ScheduledThreadPoolExecutorDemo.this) {
                // Pretend the interval is longer than it is:
                lastTime.set(Calendar.MINUTE,
                        lastTime.get(Calendar.MINUTE) + 30);
                // One in 5 chances of reversing the direction:
                if (rand.nextInt(5) == 4)
                    tempDirection = -tempDirection;
                // Store previous value:
                lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
                if (rand.nextInt(5) == 4)
                    humidityDirection = -humidityDirection;
                lastHumidity = lastHumidity +
                        humidityDirection * rand.nextFloat();
                // Calendar must be cloned, otherwise all
                // DataPoints hold references to the same lastTime.
                // For a basic object like Calendar, clone() is OK.
                data.add(new DataPoint((Calendar) lastTime.clone(),
                        lastTemp, lastHumidity));
            }
        }
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutorDemo gh = new ScheduledThreadPoolExecutorDemo();
        gh.schedule(gh.new Terminate(), 5000);
        // Former "Restart" class not necessary:
        gh.repeat(gh.new Bell(), 0, 1000);
        gh.repeat(gh.new ThermostatNight(), 0, 2000);
        gh.repeat(gh.new LightOn(), 0, 200);
        gh.repeat(gh.new LightOff(), 0, 400);
        gh.repeat(gh.new WaterOn(), 0, 600);
        gh.repeat(gh.new WaterOff(), 0, 800);
        gh.repeat(gh.new ThermostatDay(), 0, 1400);
        gh.repeat(gh.new CollectData(), 500, 500);
    }
} /* (Execute to see output) *///:~
