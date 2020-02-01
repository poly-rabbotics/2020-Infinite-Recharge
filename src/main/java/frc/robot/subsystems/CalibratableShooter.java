package frc.robot.subsystems;

import frc.robot.Controls.MechanismsJoystick;
import frc.robot.partData.Falcon500Data;
import frc.robot.utils.KGains;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class CalibratableShooter extends Shooter {
    private double desiredMeanRPM;
    private double topSpeedDividedByBottomSpeed;
    private double acceptablePercentError;
    private KGains kGainsVelocity;


    public CalibratableShooter() {
        super();
        desiredMeanRPM = 240;
        topSpeedDividedByBottomSpeed = 0;
        acceptablePercentError = 1;
        kGainsVelocity = new KGains(0, 0.2, 0, 0);
        topMotor.config_kF(Falcon500Data.pidLoopIndex, kGainsVelocity.kF);
        topMotor.config_kP(Falcon500Data.pidLoopIndex, kGainsVelocity.kP);
        topMotor.config_kI(Falcon500Data.pidLoopIndex, kGainsVelocity.kI);
        topMotor.config_kD(Falcon500Data.pidLoopIndex, kGainsVelocity.kD);
        bottomMotor.config_kF(Falcon500Data.pidLoopIndex, kGainsVelocity.kF);
        bottomMotor.config_kP(Falcon500Data.pidLoopIndex, kGainsVelocity.kP);
        bottomMotor.config_kI(Falcon500Data.pidLoopIndex, kGainsVelocity.kI);
        bottomMotor.config_kD(Falcon500Data.pidLoopIndex, kGainsVelocity.kD);
    }
    private void adjustDesiredMeanRPM() {
        if(MechanismsJoystick.getChangeTopShooter() > 0.1 
          && desiredMeanRPM < Falcon500Data.freeRPM * 0.75) {
            desiredMeanRPM += Falcon500Data.freeRPM / 5000 
                    * Math.pow(MechanismsJoystick.getChangeTopShooter(), 2);
        }
        else if(MechanismsJoystick.getChangeTopShooter() < -0.1
                && desiredMeanRPM > -Falcon500Data.freeRPM * 0.75) {
            desiredMeanRPM += Falcon500Data.freeRPM / 5000 
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
    }
    private double getDesiredBottomSpeed() {
        return 2 * desiredMeanRPM / (topSpeedDividedByBottomSpeed + 1);
    }
    private double getDesiredTopSpeed() {
        return getDesiredBottomSpeed() * topSpeedDividedByBottomSpeed;
    }
    public void calibrationRun() {
        adjustDesiredMeanRPM();
        adjustDesiredSpeedRatio();
        int desiredTopCountsPerPeriod = Falcon500Data.getCountsPerPeriodFromRPM(getDesiredTopSpeed());
        int desiredBottomCountsPerPeriod = Falcon500Data.getCountsPerPeriodFromRPM(getDesiredBottomSpeed());
        int topPercentError = 1000; //if unable to set toppercenterror, get some number so that the program doesn't crash
        if (desiredTopCountsPerPeriod != 0) {
            topPercentError = topMotor.getClosedLoopError() / desiredTopCountsPerPeriod * 100;
        }
        int bottomPercentError = 1000;
        if (desiredBottomCountsPerPeriod != 0) {
            bottomPercentError = bottomMotor.getClosedLoopError() / desiredBottomCountsPerPeriod * 100;
        }
        SmartDashboard.putNumber("Desired mean RPM", desiredMeanRPM);
        SmartDashboard.putNumber("Desired top RPM", getDesiredTopSpeed());
        SmartDashboard.putNumber("Desired bottom RPM", getDesiredBottomSpeed());
        SmartDashboard.putNumber("Top percent error", topPercentError);
        SmartDashboard.putNumber("Bottom percent error", bottomPercentError);
        if (topPercentError < acceptablePercentError && bottomPercentError < acceptablePercentError) {
            SmartDashboard.putString("OK to shoot", "OK");
        }
        else {
            SmartDashboard.putString("OK to shoot", "STOP");
        }
        topMotor.set(ControlMode.Velocity, desiredTopCountsPerPeriod);
        bottomMotor.set(ControlMode.Velocity, desiredBottomCountsPerPeriod);
    }
}