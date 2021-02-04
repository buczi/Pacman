package util;

/**
 * Thread which wait given amount of milliseconds and after that time notifies master object
 */
public class Counter extends Thread {
    public Counter(float time, Alarm alarm) {
        this.time = time;
        this.alarm = alarm;
        this.start();
    }

    @Override
    public void run(){
        try {
                sleep((long)time);
        }
        catch(InterruptedException e){
            System.out.println("Counter failed");
        }
        alarm.counterNotify();
    }
    private final float time;
    private final Alarm alarm;
}
