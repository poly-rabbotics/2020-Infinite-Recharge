package frc.robot.subsystems.shooter;

import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;
import frc.robot.controls.MechanismsJoystick.PresetNames;
import frc.robot.partdata.Falcon500Data;
import frc.robot.utils.KGains;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.AutoSubsystem;

public class Shooter implements AutoSubsystem {
    //TODO: Calibrate presets
    private static final ShooterPreset TARGET_ZONE_PRESET = new ShooterPreset(300, 0.3, false);
    private static ShooterPreset INITIATION_LINE_PRESET = new ShooterPreset(500, 0.5, true);
    private static final ShooterPreset TRENCH_RUN_CLOSE_PRESET = new ShooterPreset(2000, 1.75, true);

    private static final double ACCEPTABLE_PERCENT_ERROR = 1;

    private double desiredMeanRPM;
    private double topSpeedDividedByBottomSpeed;
    private boolean shallowAnglePosition;
    private KGains kGainsVelocity;
    private TalonSRX topMotor, bottomMotor;
    private DoubleSolenoid solenoid;
    private ShooterPreset preset;


    public Shooter() {
        topMotor = RobotMap.shooterTopMotor;
        bottomMotor = RobotMap.shooterBottomMotor;
        solenoid = RobotMap.shooterSolenoid;
    }
    /*private void adjustDesiredMeanRPM() {
        if(MechanismsJoystick.getChangeTopShooter() > 0.1 
          && desiredMeanRPM < Falcon500Data.freeRPM * 0.75) {
            desiredMeanRPM += Falcon500Data.freeRPM / 5000 
                    * Math.pow(MechanismsJoystick.getChangeTopShooter(), 2);
        }
        else if(MechanismsJoystick.getChangeTopShooter() < -0.1
                && desiredMeanRPM > -Falcon500Data.freeRPM * 0.75) {
            desiredMeanRPM -= Falcon500Data.freeRPM / 5000 
                    * Math.pow(MechanismsJoystick.getChangeTopShooter(), 2);
        }
    }
    private void adjustDesiredSpeedRatio() {
        if(MechanismsJoystick.getIncreaseSpeedRatio()) {
            if(Math.abs(topSpeedDividedByBottomSpeed - 3) < 0.1) { //speed ratio appoximately equals 3
                topSpeedDividedByBottomSpeed = 1000; //effectively make only the top shooter move
            }
            else if(topSpeedDividedByBottomSpeed > 3) {
                topSpeedDividedByBottomSpeed = 0;
            }
            else {
                topSpeedDividedByBottomSpeed += 0.25;
            }
        }
        else if(MechanismsJoystick.getDecreaseSpeedRatio()) {
            if(Math.abs(topSpeedDividedByBottomSpeed - 0) < 0.1) { //speed ratio appoximately equals 0
                topSpeedDividedByBottomSpeed = 1000; //effectively make only the top shooter move
            }
            else if(topSpeedDividedByBottomSpeed > 3) {
                topSpeedDividedByBottomSpeed = 3;
            }
            else {
                topSpeedDividedByBottomSpeed -= 0.25;
            }
        }
    }*/
    private double getDesiredBottomSpeed() {
        return 2 * desiredMeanRPM / (topSpeedDividedByBottomSpeed + 1);
    }
    private double getDesiredTopSpeed() {
        return getDesiredBottomSpeed() * topSpeedDividedByBottomSpeed;
    }
    private boolean getUserInput() {
        ShooterPreset chosen = null;
        if(MechanismsJoystick.getShooterPreset(PresetNames.TARGET_ZONE)) {
            chosen = TARGET_ZONE_PRESET;
        }
        else if(MechanismsJoystick.getShooterPreset(PresetNames.INITIATION_LINE)) {
            chosen = INITIATION_LINE_PRESET;
        }
        else if(MechanismsJoystick.getShooterPreset(PresetNames.TRENCH_RUN_CLOSE)) {
            chosen = TRENCH_RUN_CLOSE_PRESET;
        }
        if(chosen != null) {
            preset = chosen;
            applyPreset();
            return true;
        }
        return false;
    }
    private void applyPreset() {
        if(preset == null) {
            setStopped();
        }
        else {
            desiredMeanRPM = preset.getMeanSpeed();
            topSpeedDividedByBottomSpeed = preset.getSpeedRatio();
            shallowAnglePosition = preset.getShallowAnglePosition();
        }
    }
    public boolean getOkayToShoot(boolean verbose) {
        int desiredTopCountsPerPeriod = Falcon500Data.getCountsPerPeriodFromRPM(getDesiredTopSpeed());
        int desiredBottomCountsPerPeriod = Falcon500Data.getCountsPerPeriodFromRPM(getDesiredBottomSpeed());
        int topPercentError = 0; //if unable to set toppercenterror, get some number so that the program doesn't crash
        if (desiredTopCountsPerPeriod != 0) {
            topPercentError = topMotor.getClosedLoopError() / desiredTopCountsPerPeriod * 100;
        }
        int bottomPercentError = 0;
        if (desiredBottomCountsPerPeriod != 0) {
            bottomPercentError = bottomMotor.getClosedLoopError() / desiredBottomCountsPerPeriod * 100;
        }
        if(verbose) {
            SmartDashboard.putNumber("Desired mean RPM", desiredMeanRPM);
            SmartDashboard.putNumber("Desired top RPM", getDesiredTopSpeed());
            SmartDashboard.putNumber("Desired bottom RPM", getDesiredBottomSpeed());
            SmartDashboard.putNumber("Top percent error", topPercentError);
            SmartDashboard.putNumber("Bottom percent error", bottomPercentError);
        }
        if (topPercentError < ACCEPTABLE_PERCENT_ERROR && bottomPercentError < ACCEPTABLE_PERCENT_ERROR) {
            SmartDashboard.putBoolean("OK to shoot", true);
            return true;
        }
        else {
            SmartDashboard.putBoolean("OK to shoot", false);
            return false;
        }
    }
    private void runMechanism() {
        int desiredTopCountsPerPeriod = Falcon500Data.getCountsPerPeriodFromRPM(getDesiredTopSpeed());
        int desiredBottomCountsPerPeriod = Falcon500Data.getCountsPerPeriodFromRPM(getDesiredBottomSpeed());
        topMotor.set(ControlMode.Velocity, desiredTopCountsPerPeriod);
        bottomMotor.set(ControlMode.Velocity, desiredBottomCountsPerPeriod);
        if(shallowAnglePosition) {
            solenoid.set(Value.kForward);
        }
        else {
            solenoid.set(Value.kReverse);
        }
    }
    public void setPreset(ShooterPreset preset) {
        this.preset = preset;
    }
    public void run() {
        getUserInput();
        runMechanism();
    }
    public void autoRun() {
        applyPreset();
        runMechanism();
    }
    public void setStopped() {
        //reset behaviors
        preset = null;
        desiredMeanRPM = 0;
        topSpeedDividedByBottomSpeed = 0;
        shallowAnglePosition = false;
    }
    public void reset() {
        setStopped();
        //Set up PID gains
        kGainsVelocity = new KGains(0, 0.00025, 0, 0);
        //Configure PIDF gains
        topMotor.config_kF(Falcon500Data.pidLoopIndex, kGainsVelocity.kF);
        topMotor.config_kP(Falcon500Data.pidLoopIndex, kGainsVelocity.kP);
        topMotor.config_kI(Falcon500Data.pidLoopIndex, kGainsVelocity.kI);
        topMotor.config_kD(Falcon500Data.pidLoopIndex, kGainsVelocity.kD);
        bottomMotor.config_kF(Falcon500Data.pidLoopIndex, kGainsVelocity.kF);
        bottomMotor.config_kP(Falcon500Data.pidLoopIndex, kGainsVelocity.kP);
        bottomMotor.config_kI(Falcon500Data.pidLoopIndex, kGainsVelocity.kI);
        bottomMotor.config_kD(Falcon500Data.pidLoopIndex, kGainsVelocity.kD);
    }
}
