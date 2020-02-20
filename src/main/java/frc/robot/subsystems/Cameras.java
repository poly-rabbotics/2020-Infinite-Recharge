package frc.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;
import frc.robot.controls.MechanismsJoystick;
import frc.robot.commands.ShowVideo;
import org.opencv.core.*;


public class Cameras {
private static final double DEGREES_PER_UNIT = 246.6;
Servo cameraServo; //Empirically found the 0.27 - 1.0 was 180 degrees of motion
boolean intaking;
ShowVideo shooterVideo, intakeVideo;
CvSource outputStream;

public Cameras(){
    cameraServo = RobotMap.cameraServo;
    RobotMap.intakeCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    RobotMap.intakeCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    CvSink intakeCameraFeed = CameraServer.getInstance().getVideo(RobotMap.intakeCamera);
    CvSink shooterCameraFeed = CameraServer.getInstance().getVideo(RobotMap.shooterCamera);
    outputStream = CameraServer.getInstance().putVideo("Main Camera Stream", RobotMap.IMG_WIDTH, RobotMap.IMG_HEIGHT);
    intakeVideo = new ShowVideo(intakeCameraFeed, outputStream, "Intake Video", 80, true);
    shooterVideo = new ShowVideo(shooterCameraFeed, outputStream, "Intake Video", 80, true);
    intakeVideo.start();
    shooterVideo.start();
    cameraServo.set(0);
}
private void enableShooterVideo() {
    intakeVideo.disable();
    shooterVideo.enable();
}
private void enableIntakeVideo() {
    shooterVideo.disable();
    intakeVideo.enable();
}
public Mat getShooterVideo() {
    return shooterVideo.getImage();
}
public Mat getIntakeVideo() {
    return intakeVideo.getImage();
}
public void run(){
    if(MechanismsJoystick.getEnableClimbingSystem()) {
        cameraServo.set(cameraServo.get() + 90 / DEGREES_PER_UNIT);
    }
    if(Drive.shooterFront) {
        enableShooterVideo();
    }
    else {
        enableIntakeVideo();
    }

}



}
