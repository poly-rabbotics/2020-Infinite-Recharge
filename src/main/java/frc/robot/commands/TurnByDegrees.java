package frc.robot.commands;
import frc.robot.subsystems.Drive;

public class TurnByDegrees extends Command {
    static final int REQUIRED_CYCLES_OF_LOW_ERROR = 5;

    private Drive drive;
    private double degrees;
    private int lowErrorCount;
    private double acceptableError;

    public TurnByDegrees(Drive drive, double degrees, int acceptableError, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.drive = drive;
        this.degrees = degrees;
        this.acceptableError = acceptableError;
        lowErrorCount = 0;
    }
    public TurnByDegrees(Drive drive, double degrees, int acceptableError, String name, int periodInMillis) {
        this(drive, degrees, acceptableError, name, periodInMillis, false);
    }
    protected void onStart() {
        drive.setRotationalSetpoint(degrees);
    }
    protected void whileRunning() {
        if(drive.getError() <= acceptableError) {
            lowErrorCount++;
        }
        else {
            lowErrorCount = 0;
        }
    }
    protected boolean isFinished() {
        return lowErrorCount >= REQUIRED_CYCLES_OF_LOW_ERROR;
    }
}