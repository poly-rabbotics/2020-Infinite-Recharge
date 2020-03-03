package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.ControlPanel;

public class TurnPanelToRequestedColor extends Command {
    private static final String NAME = "turn to requested color";
    private static final int MIN_CORRECT_COLOR_COUNT = 5;

    private ControlPanel controlPanelSubsystem;
    private int correctColorCount;

    public TurnPanelToRequestedColor(boolean verbose) {
        super(NAME, 20, verbose);
        controlPanelSubsystem = Robot.controlPanel;
        correctColorCount = 0;
    }
    @Override
    protected void onStart() {
        lockSubsystem(controlPanelSubsystem);
    }
    @Override
    protected void whileRunning() {
        controlPanelSubsystem.spinSlow();
        if(controlPanelSubsystem.atCorrectColor()) {
            correctColorCount++;
        }
        else {
            correctColorCount = 0;
        }
    }
    @Override
    protected boolean isFinished() {
        return correctColorCount >= MIN_CORRECT_COLOR_COUNT ||
                subsystemTaken(controlPanelSubsystem);
    }
}