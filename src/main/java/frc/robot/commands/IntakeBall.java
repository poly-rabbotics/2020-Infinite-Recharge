package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.subsystems.ConveyorBelt;

/**
 * PLEASE IGNORE, this command may not be necessary.
 * Purpose: actively keep the robot the same place where it started.
 */

public class IntakeBall extends Command {
    private ConveyorBelt conveyorBelt;
    private int numberOfBalls;
    public IntakeBall(String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        conveyorBelt = Robot.conveyorBelt;
        numberOfBalls = conveyorBelt.getNumberOfBalls();
    }
    @Override
    protected void onStart() {
        conveyorBelt.lock("intake ball");
    }
    @Override
    protected void whileRunning() {
        conveyorBelt.moveForIntake();
    }
    @Override
    protected boolean isFinished() {
        return (conveyorBelt.getNumberOfBalls() > numberOfBalls || numberOfBalls > 4) 
                || conveyorBelt.getLock() != "intake ball";
    }
    @Override
    protected void onFinish() {
        conveyorBelt.unlock();
    }
}