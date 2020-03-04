package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Robot;
import frc.robot.RobotMap;
/**
 * Class for controlling the servo that rotates the camera.
*/
public class CameraServo extends AutoSubsystem {
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
    /**
     * No special functionality is necessary, so manualRun and advancedRun perform the same function.
    */
    public void manualRun() {
        run();
    }
    /**
     * Fix the camera in one place for the duration of the autonomous period.
    */
    public void autoRun() {
        cameraServo.set(1.0);
    }
    public void reset() {}
}
