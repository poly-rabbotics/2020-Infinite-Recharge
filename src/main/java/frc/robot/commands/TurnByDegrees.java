package frc.robot.commands;
import frc.robot.subsystems.Drive;

public class TurnByDegrees extends Command {
    static final int REQUIRED_CYCLES_OF_LOW_ERROR = 5;

    private Drive drive;
    private double degrees;
    private int lowErrorCount;
    private double acceptableError;
    private double abs(double num) {
        return num > 0 ? num : -num;
    }
    public TurnByDegrees(Drive drive, double degrees, double acceptableError, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.drive = drive;
        this.degrees = degrees;
        this.acceptableError = acceptableError;
        lowErrorCount = 0;
    }
    public TurnByDegrees(Drive drive, double degrees, double acceptableError, String name, int periodInMillis) {
        this(drive, degrees, acceptableError, name, periodInMillis, false);
    }
    protected void onStart() {
        super.onStart();
        drive.setRotationalSetpoint(degrees);
    }
    protected void whileRunning() {
        if(abs(drive.getError()) <= acceptableError) {
            System.out.println(drive.getError());
            lowErrorCount++;
        }
        else {
            lowErrorCount = 0;
        }
    }
    protected boolean isFinished() {
        System.out.println(lowErrorCount);
        return lowErrorCount >= REQUIRED_CYCLES_OF_LOW_ERROR;
    }
}