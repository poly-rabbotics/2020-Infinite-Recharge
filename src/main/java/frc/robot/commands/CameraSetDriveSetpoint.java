package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.VisionLight;

public class CameraSetDriveSetpoint extends Command {
    public static final String NAME = "camera set drive setpoint"; //The name of this Command
    private static final int PERIOD = 10; //period in milliseconds
    private static final double TIME_FOR_RELAY_TO_SWITCH = 0.033; // Most relays are 0.005s to 0.020s
    private static final double TIME_FOR_PI_TO_GET = 0.167; //If the pi has 30 frames/second, this is 5 frames
    
    private Drive drive;
    private VisionLight light;
    private boolean finished;
    
    public CameraSetDriveSetpoint(boolean verbose) {
        super(NAME, PERIOD, verbose);
        this.drive = Robot.drive;
        this.light = Robot.light;
        finished = false;
    }
    /**
     * "Claims" (puts a lock on) the light and turns it on.
    */
    @Override
    protected void onStart() {
        super.onStart();
        lockSubsystem(light);
        light.turnOn();
    }
    /**
     * Turns the light off and "unclaims" it (removes the lock on it).
    */
    @Override
    protected void onFinish() {
        light.turnOff();
        light.unlock();
    }
    /**
     * Tells the drive to set its rotational setpoint based on camera data AFTER enough time has passed
     * for the Raspberry Pi to get a good reading.
    */
    @Override
    protected void whileRunning() {
        light.turnOn();
        if(getTime() > TIME_FOR_RELAY_TO_SWITCH + TIME_FOR_PI_TO_GET) {
            drive.cameraOrient();
            finished = true;
        }
    }
    /**
     * Stops trying to auto-orient if the command is finished OR this Command's lock on 
     * the light has been removed or replace by another lock.
    */
    @Override
    protected boolean isFinished() {
        return finished || subsystemTaken(light);
    }
}
