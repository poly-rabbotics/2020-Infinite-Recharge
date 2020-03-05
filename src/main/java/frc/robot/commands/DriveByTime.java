package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Drive;

public class DriveByTime extends Command {
    public static final String NAME = "drive by time";
    private Drive drive;
    private double time, speed;
    public DriveByTime(double timeInSeconds, double speed, int periodInMillis, boolean verbose) {
        super(NAME, periodInMillis, verbose);
        this.drive = Robot.drive;
        this.time = timeInSeconds;
        this.speed = speed;
    }
    public DriveByTime(double timeInSeconds, double speed, int periodInMillis) {
        this(timeInSeconds, speed, periodInMillis, false);
    }
    @Override
    protected void onStart() {
        super.onStart();
        lockSubsystem(drive);
    }
    @Override
    protected void whileRunning() {
        drive.setDriveForward(speed);
        drive.move();
    }
    @Override
    protected boolean isFinished() {
        return getTime() >= time || subsystemTaken(drive);
    }
    @Override
    protected void onFinish() {
        super.onFinish();
        drive.setDriveForward(0);
    }
}