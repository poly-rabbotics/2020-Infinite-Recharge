package frc.robot.commands;

import java.security.ProtectionDomain;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class Turn4Rotations extends Command {
    private double startRotations;
    public Turn4Rotations(boolean verbose) {
        super("Turn 4 rotations", 20, verbose);
    }
    @Override
    protected void onStart() {
        //Robot.controlPanel.setLock("turn 4 rotations")
        //startRotations = Robot.controlPanel.getPanelRotations();
    }
    @Override
    protected void whileRunning() {
        //Robot.controlPanel.spinFast();
    }
    @Override
    protected boolean isFinished() {
        return true;
        //return Robot.controlPanel.getRotations();
    }
}