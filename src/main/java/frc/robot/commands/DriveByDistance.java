package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Drive;
import frc.robot.Robot;

public class DriveByDistance extends Command {
    public static final String NAME = "drive by distance";

    private Drive drive;
    double distance;
    double startPosition;
    double maxTimeInSeconds;
    public DriveByDistance(double distance, double maxTimeInSeconds, int periodInMillis, boolean verbose) {
        super(NAME, periodInMillis, verbose);
        this.drive = Robot.drive;
        this.distance = distance;
        this.maxTimeInSeconds = maxTimeInSeconds;
        this.startPosition = -1; //Garbage value
    }
    public DriveByDistance(double distance, double maxTimeInSeconds, int periodInMillis) {
        this(distance, maxTimeInSeconds, periodInMillis, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        
        if(!drive.getLocked()) {
            lockSubsystem(drive);
            startPosition = drive.getPosition();
            drive.setTranslationalSetpoint(distance);
        }  
    }
    @Override
    public void whileRunning() {
        //do nothing; CAN motor controllers are doing everything
    }
    @Override
    public boolean isFinished() {
        return Math.abs(drive.getPosition() - startPosition) < 0.5 //Within 6 inches of correct position,
            || getTime() > maxTimeInSeconds //or it has been enough time
            || subsystemTaken(drive); //or the subsystem is claimed by another command
    }
    @Override
    public void onFinish() {
        super.onFinish();
        drive.unlock();
    }
}