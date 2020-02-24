package frc.robot.commands;
import frc.robot.subsystems.Drive;

public class Stop extends Command {
    private Drive drive;
    public Stop(String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
    }
    @Override
    protected void onStart() {
    }
    @Override
    protected void whileRunning() {}
    @Override
    protected boolean isFinished() {
        return true;
    }
}