package frc.robot.subsystems.shooter;

public class ShooterPreset {
    private double meanSpeed, speedRatio;
    private boolean shallowAnglePosition;
    public ShooterPreset(double meanSpeed, double speedRatio, boolean shallowAnglePosition) {
        this.meanSpeed = meanSpeed;
        this.speedRatio = speedRatio;
        this.shallowAnglePosition = shallowAnglePosition;
    }
    public double getMeanSpeed() {
        return meanSpeed;
    }
    public double getSpeedRatio() {
        return speedRatio;
    }
    public boolean getShallowAnglePosition() {
        return shallowAnglePosition;
    }
    public String getString() {
        return "Mean speed: " + meanSpeed + "\nSpeed ratio: " + speedRatio + "\nShallow angle configuration: " + shallowAnglePosition;
    }
}