package frc.robot.subsystems;

import frc.robot.Controls.MechanismsJoystick;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.RobotMap;

public class Shooter {
    private PWMVictorSPX topMotor, bottomMotor;
    private int topMotorSpeed, bottomMotorSpeed;
    public Shooter() {
        topMotor = RobotMap.shooterTopMotor;
        bottomMotor = RobotMap.shooterBottomMotor;
    }
    private void adjustSpeeds() {
        if(MechanismsJoystick.getChangeTopShooter() > 0.1 && topMotorSpeed <= 1) {
          topMotorSpeed+=.005;
        }
        else if(MechanismsJoystick.getChangeTopShooter() < -0.1 && topMotorSpeed >= 0) {
          topMotorSpeed-=.005;
        }
        if(MechanismsJoystick.getChangeBottomShooter() > .1 && bottomMotorSpeed <= 1) {
          bottomMotorSpeed+=.005;
        }
        else if(MechanismsJoystick.getChangeBottomShooter() <- .1 && bottomMotorSpeed >= 0) {
          bottomMotorSpeed-=.005;
        }
    }
    public void run() {
        adjustSpeeds();
        SmartDashboard.putNumber("Top Shooter:", topMotorSpeed);
        SmartDashboard.putNumber("Bottom Shooter:", bottomMotorSpeed);
        topMotor.set(topMotorSpeed);
        bottomMotor.set(bottomMotorSpeed);
    }
}