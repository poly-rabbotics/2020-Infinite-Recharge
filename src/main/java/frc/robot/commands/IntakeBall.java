package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.subsystems.ConveyorBelt;

/**
 * PLEASE IGNORE, this command may not be necessary.
 * Purpose: actively keep the robot the same place where it started.
 */

public class IntakeBall extends Command {
    private static final int MAX_NUMBER_OF_BALLS = 5;
    private String name;
    public static final int PERIOD = 20;

    private ConveyorBelt conveyorBelt;
    private int startingNumberOfBalls;
    public IntakeBall(boolean verbose) {
        super("intake ball" + Robot.conveyorBelt.getNumberOfBalls(), PERIOD, verbose);
        name = getName();
        conveyorBelt = Robot.conveyorBelt;
        startingNumberOfBalls = conveyorBelt.getNumberOfBalls();
    }
    @Override
    protected void onStart() {
        conveyorBelt.lock(name);
    }
    @Override
    protected void whileRunning() {
        conveyorBelt.moveForIntake();
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.getNumberOfBalls() > startingNumberOfBalls 
                || conveyorBelt.getNumberOfBalls() >= MAX_NUMBER_OF_BALLS 
                || conveyorBelt.getLock() != name;
    }
    @Override
    protected void onFinish() {
        conveyorBelt.unlock();
    }
}