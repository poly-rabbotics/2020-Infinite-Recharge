package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class CameraServo extends Subsystem {
    Servo cameraServo;
    public CameraServo() {
        cameraServo = RobotMap.cameraServo;
    }
    public void run() {

        if(Robot.drive.getShooterFront()) {
            cameraServo.set(0.27);
        }
        else {
            cameraServo.set(1.0);
        }

    }
    public void reset() {}
}