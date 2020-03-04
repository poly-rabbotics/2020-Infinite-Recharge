package frc.robot.subsystems;

public abstract class Subsystem {
    String lock;//The value of this variable is the command that has control over this subsystem
    public void run() {//Called from Robot. Please do not override this method.
        if(getIsManual) {
            manualRun();
        }
        else {
            advancedRun();
        }
    }
    public void getIsManual() { //Override if there should be an additional way to make this subsystem manual
        return MechanismsJoystick.isManual();
    }
    public abstract void advancedRun(); //
    public abstract void manualRun();
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
