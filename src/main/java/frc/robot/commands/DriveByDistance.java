package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Drive;
import frc.robot.Robot;

public class DriveByDistance extends Command {
    private Drive drive;
    double distance;
    double startPosition;
    double startTime; //time in seconds
    double maxTimeInSeconds;
    public DriveByDistance(double distance, double maxTimeInSeconds, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.drive = Robot.drive;
        this.distance = distance;
        this.maxTimeInSeconds = maxTimeInSeconds;
        this.startPosition = -1; //Garbage value
        this.startTime = -1; //Garbage value
    }
    public DriveByDistance(Drive drive, double distance, double maxTimeInSeconds, String name, int periodInMillis) {
        this(distance, maxTimeInSeconds, name, periodInMillis, false);
    }
    @Override
    public void start() {
        super.start();
        startPosition = drive.getPosition();
        startTime = Timer.getFPGATimestamp();
        drive.lock();
        drive.setTranslationalSetpoint(distance);
    }
    @Override
    public void whileRunning() {
        //do nothing; CAN motor controllers are doing everything
    }
    @Override
    public boolean isFinished() {
        return Math.abs(drive.getPosition() - startPosition) < 0.5 //Within 6 inches of correct position,
            || Timer.getFPGATimestamp() - startTime > maxTimeInSeconds; //or it has been enough time
    }
    @Override
    public void onFinish() {
        super.onFinish();
        drive.unlock();
    }
}