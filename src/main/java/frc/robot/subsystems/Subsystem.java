package frc.robot.subsystems;
/**
 * Defines a part of the robot that performs one specific function.
 */
public abstract class Subsystem {
    String lock;//The value of this variable is the command that has control over this subsystem. It is a flag that lets Commands avoid interfering with each other if a higher-priority command has control over the Subsystem.
    boolean isManual;
    /**
     * This is the method that should be placed in teleopPeriodic and nowhere else.
     * run() contains no complicated logic, it just calls the appropriate functionality
     * defined in manualRun() or advancedRun().
    */
    public void run() {//Called from Robot. Please do not override this method.
        if(getIsManual) {
            manualRun();
        }
        else {
            advancedRun();
        }
    }
    /**
     * Forces this Subsystem to run in either manual or advanced mode.
    */
    public void setIsManual(boolean isManual) {
        this.isManual = isManual;
    }
    /**
     * Gets whether this subsystem should be run in manual mode or not. 
     */
    public boolean getIsManual() {
        return isManual;
    }
    /**
     * The most desirable (but perhaps not simplest) functionality of this subsystem
    */
    protected abstract void advancedRun();
    /**
     * The simplest effective functionality of this subsystem.
    */
    protected abstract void manualRun();
    /**
     * Reset any PID controllers or other things that could cause unexpected behavior
     * once a subsystem is re-activated. This should be called in teleopInit, autonomousInit,
     * testInit.
    */
    public abstract void reset();
    /**
     * Labels this subsystem as having been claimed by a given Command so as to prevent other
     * Commands fron interfering with it.
     */
    public void lock(String command) {
        lock = command;
    }
    /**
     * @return the name of the Command that has claimed a lock on this Subsystem.
    */
    public String getLock() {
        return lock;
    }
    /**
     * @return whether or not any Command currently has a lock on this Subsystem.
    */
    public boolean getLocked() {
        return lock == "";
    }
    /**
     * End any lock that any Command may have on this subsystem.
    */
    public void unlock() {
        lock = "";
    }
}
