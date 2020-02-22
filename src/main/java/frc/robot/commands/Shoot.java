package frc.robot.commands;
import frc.robot.subsystems.shooter.*;

public class Shoot extends Command {
    Shooter shooter;
    ShooterPreset preset;
    public Shoot(Shooter shooter, ConveyorBelt conveyorBelt, ShooterPreset preset, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.shooter = shooter;
        this.preset = preset;
        this.conveyorBelt = conveyorBelt;
    }
    @Override
    protected void onStart() {
        super.onStart();
        shooter.setPreset(preset);
    }
    @Override
    protected void whileRunning() {
        if(shooter.getOkayToShoot(getVerbose())) {
            conveyorBelt.run();
        }
        else {
            conveyorBelt.stop();
        }
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.getBallCount() == 0;
    }
}