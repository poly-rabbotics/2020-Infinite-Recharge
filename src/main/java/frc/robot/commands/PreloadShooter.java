package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.subsystems.ConveyorBelt;

/**
 * PLEASE IGNORE, this command may not be necessary.
 * Purpose: actively keep the robot the same place where it started.
 */

public class PreloadShooter extends Command {
    private ConveyorBelt conveyorBelt;
    public PreloadShooter(String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        conveyorBelt = Robot.conveyorBelt;
    }
    public PreloadShooter(boolean verbose) {
        super("preload shooter", 20, verbose);
        conveyorBelt = Robot.conveyorBelt;
    }
    @Override
    protected void onStart() {
        conveyorBelt.lock("preload shooter");
    }
    @Override
    protected void whileRunning() {
        conveyorBelt.moveForIntake();
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.needsToStop() || conveyorBelt.getLock() != "preload shooter";
    }
    @Override
    protected void onFinish() {
        conveyorBelt.unlock();
    }
}