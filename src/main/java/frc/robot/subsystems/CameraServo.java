package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.RobotMap;

public class CameraServo {

Servo camera;

public CameraServo(){
camera = RobotMap.camera;
}
public void run(){

    if(Drive.shooterFront){
        camera.set(0.0);
    }
    else{
        camera.set(0.5);
    }

}



}
