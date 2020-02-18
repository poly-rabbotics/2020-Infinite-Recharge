package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;
import frc.robot.controls.MechanismsJoystick;

public class CameraServo {
private static final double DEGREES_PER_UNIT = 246.6;
Servo cameraServo; //Empirically found the 0.27 - 1.0 was 180 degrees of motion
UsbCamera intakeCamera;
UsbCamera shooterCamera;
boolean intaking;

public CameraServo(){
    cameraServo = RobotMap.cameraServo;
    intakeCamera = RobotMap.intakeCamera;
    shooterCamera = RobotMap.shooterCamera;
}
public void run(){
    if(MechanismsJoystick.getEnableClimbingSystem()) {
        
    }
    if(Drive.shooterFront){
    }
    else{
    }

}



}
