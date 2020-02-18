package frc.robot.commands;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.Timer;

public class DriveByTime extends Command {
    private Drive drive;
    private double time, speed;
    private double startTime;
    public DriveByTime(Drive drive, double timeInSeconds, double speed, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.drive = drive;
        this.time = timeInSeconds;
    }
    public DriveByTime(Drive drive, double timeInSeconds, double speed, String name, int periodInMillis) {
        this(drive, timeInSeconds, speed, name, periodInMillis, false);
    }
    protected void onStart() {
        this.startTime = Timer.getFPGATimestamp();
    }
    protected void whileRunning() {
        drive.setDriveForward(speed);
    }
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= time;
    }
    @Override
    protected void onFinish() {
        super.onFinish();
        drive.setDriveForward(0);
    }
}