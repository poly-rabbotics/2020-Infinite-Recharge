package frc.robot.commands;
import frc.robot.subsystems.shooter.*;
import frc.robot.subsystems.ConveyorBelt;
import frc.robot.Robot;

public class Shoot extends Command {
    Shooter shooter;
    ShooterPreset preset;
    ConveyorBelt conveyorBelt;
    public Shoot(ShooterPreset preset, String name, int periodInMillis, boolean verbose) {
        super(name, periodInMillis, verbose);
        this.shooter = Robot.shooter;
        this.preset = preset;
        this.conveyorBelt = Robot.conveyorBelt;
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