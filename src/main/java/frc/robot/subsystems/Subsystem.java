package frc.robot.subsystems;

public abstract class Subsystem {
    String lock;//The value of this variable is the command that has control over this subsystem
    public abstract void run();
    public abstract void reset();
    public void lock(String command) {
        lock = command;
    }
    public String getLock() {
        return lock;
    }
    public boolean getLocked() {
        return lock == "";
    }
    public void unlock() {
        lock = "";
    }
}