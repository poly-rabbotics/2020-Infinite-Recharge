package frc.robot.subsystems;

public abstract class Subsystem {
    boolean locked;
    public abstract void run();
    public abstract void reset();
    public void lock() {
        locked = true;
    }
    public boolean getLocked() {
        return locked;
    }
    public void unlock() {
        locked = false;
    }
}