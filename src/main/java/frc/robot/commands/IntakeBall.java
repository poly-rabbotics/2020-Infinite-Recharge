package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.subsystems.ConveyorBelt;

/**
 * PLEASE IGNORE, this command may not be necessary.
 * Purpose: actively keep the robot the same place where it started.
 */

public class IntakeBall extends Command {
    private static final int MAX_NUMBER_OF_BALLS = 5;
    public static final int PERIOD = 20;
    public static final String NAME = "intake ball";

    private ConveyorBelt conveyorBelt;
    private int startingNumberOfBalls;
    public IntakeBall(boolean verbose) {
        super(NAME, PERIOD, verbose);
        conveyorBelt = Robot.conveyorBelt;
        startingNumberOfBalls = conveyorBelt.getNumberOfBalls();
    }
    @Override
    protected void onStart() {
        super.onStart();
        lockSubsystem(conveyorBelt);
    }
    @Override
    protected void whileRunning() {
        if(conveyorBelt.ballDetectedAtIntake()) {
            conveyorBelt.moveForIntake();
        }
        else {
            conveyorBelt.stop();
        }
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.getNumberOfBalls() > startingNumberOfBalls 
                || conveyorBelt.getNumberOfBalls() >= MAX_NUMBER_OF_BALLS 
                || subsystemTaken(conveyorBelt);
    }
    @Override
    protected void onFinish() {
        conveyorBelt.unlock();
    }
}