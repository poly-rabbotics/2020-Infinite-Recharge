package frc.robot.commands;
import frc.robot.subsystems.Drive;

public class DriveByDistance extends Command {
    private Drive drive;
    double distance;
    double startPosition;
    public DriveByDistance(Drive drive, double distance, double speed, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.drive = drive;
        this.distance = distance;
        this.startPosition = -1; //Garbage value
    }
    public DriveByDistance(Drive drive, double distance, double speed, String name, int periodInMillis) {
        this(drive, distance, speed, name, periodInMillis, false);
    }
    @Override
    public void start() {
        super.start();
        drive.setTranslationalSetpoint(distance);
        startPosition = drive.getPosition();
    }
}