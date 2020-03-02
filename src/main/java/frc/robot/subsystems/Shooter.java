package frc.robot.subsystems;

import frc.robot.controls.MechanismsJoystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotMap;

public class Shooter extends Subsystem {
    private TalonSRX topMotor, bottomMotor;
    static double distance, preset;
    private double topMotorSpeed, bottomMotorSpeed, lowSpeed, highSpeed, bothSpeed;
    private DoubleSolenoid solenoid;
    private boolean solenoidOut;
  
    public Shooter() {
      topMotorSpeed = 0;
      bottomMotorSpeed = 0;
      topMotor = RobotMap.shooterTopMotor;
      bottomMotor = RobotMap.shooterBottomMotor;
      distance = 0.0;
      preset = 1;
      lowSpeed = 0.3;
      highSpeed = 0.6;
      bothSpeed = 0.8;
      solenoid = RobotMap.shooterSolenoid;
      solenoidOut = false;
    }
    private void adjustSpeeds() {
        if(MechanismsJoystick.getChangeTopShooter() > 0.1 && topMotorSpeed <= 1) {
          topMotorSpeed += .005;
        }
        else if(MechanismsJoystick.getChangeTopShooter() < -0.1 && topMotorSpeed >= 0) {
          topMotorSpeed -= .005;
        }
        if(MechanismsJoystick.getChangeBottomShooter() > .1 && bottomMotorSpeed <= 1) {
          bottomMotorSpeed -= .005;
        }
        else if(MechanismsJoystick.getChangeBottomShooter() <- .1 && bottomMotorSpeed >= 0) {
          bottomMotorSpeed -= .005;
        }
    }

    

    public void run() {
      if(MechanismsJoystick.isManual() == false) {
        adjustSpeeds();
        //SmartDashboard.putNumber("Top Shooter:", topMotorSpeed);
        //SmartDashboard.putNumber("Bottom Shooter:", bottomMotorSpeed);
        topMotor.set(ControlMode.PercentOutput, topMotorSpeed);
        bottomMotor.set(ControlMode.PercentOutput, bottomMotorSpeed);
      }
      else {
        if(MechanismsJoystick.getToggleManShootOne()) {
          topMotor.set(ControlMode.PercentOutput, -bothSpeed);
          bottomMotor.set(ControlMode.PercentOutput, bothSpeed);
        }
        else if(MechanismsJoystick.getToggleManShootTwo()) {
          topMotor.set(ControlMode.PercentOutput, -highSpeed);
          bottomMotor.set(ControlMode.PercentOutput, lowSpeed);
        }
        else if(MechanismsJoystick.getToggleManShootThree()) {
          topMotor.set(ControlMode.PercentOutput, -lowSpeed);
          bottomMotor.set(ControlMode.PercentOutput, highSpeed);
        }
        else {
          topMotor.set(ControlMode.PercentOutput, 0);
          bottomMotor.set(ControlMode.PercentOutput, 0);
        }
        if(MechanismsJoystick.getToggleManShooterSolenoid()) {
          solenoid.set(Value.kForward);
        }
        else if(!MechanismsJoystick.getToggleManShooterSolenoid()) {
          solenoid.set(Value.kReverse);
        }
      }
    }
    public void reset() {}
}