package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.ControlPanel;

public class TurnPanel4Rotations extends Command {
    private static final String NAME = "turn panel 4 rotations";
    private double startRotations;
    private ControlPanel controlPanelSubsystem;
    public TurnPanel4Rotations(boolean verbose) {
        super(NAME, 20, verbose);
        controlPanelSubsystem = Robot.controlPanel;
    }
    @Override
    protected void onStart() {
        lockSubsystem(controlPanelSubsystem);
        startRotations = controlPanelSubsystem.getPanelRotations();
    }
    @Override
    protected void whileRunning() {
        controlPanelSubsystem.spinFast();
    }
    @Override
    protected boolean isFinished() {
        return Math.abs(controlPanelSubsystem.getPanelRotations() - startRotations) >= 4 ||
            subsystemTaken(controlPanelSubsystem);
    }
}