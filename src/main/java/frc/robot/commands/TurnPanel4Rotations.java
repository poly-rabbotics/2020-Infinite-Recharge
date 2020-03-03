package frc.robot.commands;

import frc.robot.Robot;

public class TurnPanel4Rotations extends Command {
    private static final String NAME = "turn panel 4 rotations";
    private double startRotations;
    public TurnPanel4Rotations(boolean verbose) {
        super(NAME, 20, verbose);
    }
    @Override
    protected void onStart() {
        Robot.controlPanel.lock(NAME);
        startRotations = Robot.controlPanel.getPanelRotations();
    }
    @Override
    protected void whileRunning() {
        Robot.controlPanel.spinFast();
    }
    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.controlPanel.getPanelRotations() - startRotations) >= 4 ||
            Robot.controlPanel.getLock() != NAME;
    }
}