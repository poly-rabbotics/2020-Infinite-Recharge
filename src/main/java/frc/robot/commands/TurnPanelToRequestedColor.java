package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.ControlPanel;

public class TurnPanelToRequestedColor extends Command {
    private static final String NAME = "turn to requested color";
    private static final int MIN_CORRECT_COLOR_COUNT = 5;

    private ControlPanel controlPanelMechanism;
    private int correctColorCount;

    public TurnPanelToRequestedColor(boolean verbose) {
        super(NAME, 20, verbose);
        controlPanelMechanism = Robot.controlPanel;
        correctColorCount = 0;
    }
    @Override
    protected void onStart() {
        Robot.controlPanel.lock(NAME);
    }
    @Override
    protected void whileRunning() {
        Robot.controlPanel.spinSlow();
        if(Robot.controlPanel.atCorrectColor()) {
            correctColorCount++;
        }
        else {
            correctColorCount = 0;
        }
    }
    @Override
    protected boolean isFinished() {
        return correctColorCount >= MIN_CORRECT_COLOR_COUNT ||
                controlPanelMechanism.getLock() != NAME;
    }
}