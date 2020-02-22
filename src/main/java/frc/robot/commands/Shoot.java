package frc.robot.commands;
import frc.robot.subsystems.shooter.*;
import frc.robot.subsystems.ConveyorBelt;

public class Shoot extends Command {
    Shooter shooter;
    ShooterPreset preset;
    ConveyorBelt conveyorBelt;
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
            conveyorBelt.moveForShoot();
        }
        else {
            conveyorBelt.storeBalls();
        }
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.getNumberOfBalls() == 0;
    }
}