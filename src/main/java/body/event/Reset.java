package body.event;

public class Reset {
    static private boolean kill = false;

    public static synchronized void killSwitch(){
        kill = !kill;
    }

    public static synchronized boolean checkKillSwitch(){
        return kill;
    }

}
