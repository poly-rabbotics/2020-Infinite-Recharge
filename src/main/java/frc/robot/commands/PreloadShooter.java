package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.subsystems.ConveyorBelt;

/**
 * PLEASE IGNORE, this command may not be necessary.
 * Purpose: actively keep the robot the same place where it started.
 */

public class PreloadShooter extends Command {
    public static String NAME = "preload shooter";
    private ConveyorBelt conveyorBelt;
    public PreloadShooter(int periodInMillis, boolean verbose) {
        super(NAME, periodInMillis, verbose);
        conveyorBelt = Robot.conveyorBelt;
    }
    public PreloadShooter(boolean verbose) {
        super(NAME, 20, verbose);
        conveyorBelt = Robot.conveyorBelt;
    }
    @Override
    protected void onStart() {
        lockSubsystem(conveyorBelt);
    }
    @Override
    protected void whileRunning() {
        conveyorBelt.moveForIntake();
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.needsToStop() || subsystemTaken(conveyorBelt);
    }
    @Override
    protected void onFinish() {
        conveyorBelt.unlock();
    }
}