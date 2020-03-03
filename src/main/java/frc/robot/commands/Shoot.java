package frc.robot.commands;
import frc.robot.subsystems.shooter.*;
import frc.robot.subsystems.ConveyorBelt;
import frc.robot.Robot;

public class Shoot extends Command {
    public static String NAME = "shoot";
    Shooter shooter;
    ShooterPreset preset;
    ConveyorBelt conveyorBelt;
    public Shoot(ShooterPreset preset, int periodInMillis, boolean verbose) {
        super(NAME, periodInMillis, verbose);
        this.shooter = Robot.shooter;
        this.preset = preset;
        this.conveyorBelt = Robot.conveyorBelt;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(conveyorBelt.getLock() == IntakeBall.NAME || !conveyorBelt.getLocked()) {
            lockSubsystem(conveyorBelt);
        }
        shooter.setPreset(preset);
    }
    @Override
    protected void whileRunning() {
        if(shooter.getOkayToShoot(getVerbose())) {
            conveyorBelt.lock(NAME);
            conveyorBelt.moveForShoot();
        }
        else {
            conveyorBelt.unlock();
        }
    }
    @Override
    protected boolean isFinished() {
        return conveyorBelt.getNumberOfBalls() == 0 || subsystemTaken(conveyorBelt);
    }
}