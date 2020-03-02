package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class DriveByTime extends Command {
    private Drive drive;
    private double time, speed;
    private double startTime;
    public DriveByTime(double timeInSeconds, double speed, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.drive = Robot.drive;
        this.time = timeInSeconds;
        this.speed = speed;
    }
    public DriveByTime(double timeInSeconds, double speed, String name, int periodInMillis) {
        this(timeInSeconds, speed, name, periodInMillis, false);
    }
    protected void onStart() {
        super.onStart();
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