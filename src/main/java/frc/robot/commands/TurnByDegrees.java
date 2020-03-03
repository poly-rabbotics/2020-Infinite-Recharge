package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Drive;

public class TurnByDegrees extends Command {
    public static final String NAME = "turn by degrees";

    private static final int REQUIRED_CYCLES_OF_LOW_ERROR = 5;

    private Drive drive;
    private double degrees;
    private int lowErrorCount;
    private double acceptableError;
    private double abs(double num) {
        return num > 0 ? num : -num;
    }
    public TurnByDegrees(double degrees, double acceptableError, int periodInMillis, boolean verbose) {
        super(NAME, periodInMillis, verbose);
        this.drive = Robot.drive;
        this.degrees = degrees;
        this.acceptableError = acceptableError;
        lowErrorCount = 0;
    }
    public TurnByDegrees(double degrees, double acceptableError, int periodInMillis) {
        this(degrees, acceptableError, periodInMillis, false);
    }
    protected void onStart() {
        super.onStart();
        lockSubsystem(drive);
        drive.setRotationalSetpoint(degrees);
    }
    protected void whileRunning() {
        if(abs(drive.getError()) <= acceptableError) {
            if(getVerbose()) {
                System.out.println(drive.getError());
            }
            lowErrorCount++;
        }
        else {
            lowErrorCount = 0;
        }
    }
    protected boolean isFinished() {
        if(getVerbose()) {
            System.out.println(lowErrorCount);
        }
        return lowErrorCount >= REQUIRED_CYCLES_OF_LOW_ERROR || subsystemTaken(drive);
    }
}