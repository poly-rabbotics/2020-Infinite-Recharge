package frc.robot.subsystems;

import frc.robot.controls.MechanismsJoystick;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotMap;

/*public class Shooter implements Subsystem {
    private TalonSRX topMotor, bottomMotor;
    private double topMotorSpeed, bottomMotorSpeed;
    public Shooter() {
      topMotorSpeed = 0.3;
      bottomMotorSpeed = 0.3;
      topMotor = RobotMap.shooterTopMotor;
      bottomMotor = RobotMap.shooterBottomMotor;
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
        adjustSpeeds();
        SmartDashboard.putNumber("Top Shooter:", topMotorSpeed);
        SmartDashboard.putNumber("Bottom Shooter:", bottomMotorSpeed);
        topMotor.set(ControlMode.PercentOutput, topMotorSpeed);
        bottomMotor.set(ControlMode.PercentOutput, bottomMotorSpeed);
    }
    public void reset() {}
}*/