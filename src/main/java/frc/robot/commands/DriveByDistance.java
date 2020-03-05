package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Drive;
import frc.robot.Robot;

public class DriveByDistance extends Command {
    public static final String NAME = "drive by distance";
    public static final double TRANSLATION_ALLOWABLE_ERROR = 0.5;
    private Drive drive;
    double distance;
    double startPosition;
    double maxTimeInSeconds;
    double leftSetpoint, rightSetpoint;
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
        
        //if(!drive.getLocked()) {
            lockSubsystem(drive);
            startPosition = drive.getPosition();
            leftSetpoint = drive.getLeftPosition() + distance;
            rightSetpoint = drive.getRightPosition() + distance;
        //}  
    }
    @Override
    public void whileRunning() {
        System.out.println(drive.getLeftPosition());
        System.out.println(leftSetpoint);
        drive.setTranslationalSetpoint(leftSetpoint, rightSetpoint);
    }
    @Override
    public boolean isFinished() {
        return (Math.abs(drive.getLeftPosition() - leftSetpoint) + Math.abs(drive.getRightPosition() - rightSetpoint)) / 2 < TRANSLATION_ALLOWABLE_ERROR //Within 6 inches of correct position,
            || getTime() > maxTimeInSeconds //or it has been enough time
            || subsystemTaken(drive); //or the subsystem is claimed by another command
    }
    @Override
    public void onFinish() {
        super.onFinish();
        drive.unlock();
    }
}