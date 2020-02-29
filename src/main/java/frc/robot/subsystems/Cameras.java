package frc.robot.subsystems;

//Based on code from https://docs.wpilib.org/en/latest/docs/software/vision-processing/introduction/using-the-cameraserver-on-the-roborio.html

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

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Cameras {
    private static final double DEGREES_PER_UNIT = 246.6;
    Servo cameraServo; //Empirically found the 0.27 - 1.0 was 180 degrees of motion
    public Cameras() {
        new Thread(() -> {
            UsbCamera intakeCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.intakeCameraPath);
            UsbCamera shooterCamera  = CameraServer.getInstance().startAutomaticCapture(RobotMap.shooterCameraPath);
            CvSink intakeCvSink = CameraServer.getInstance().getVideo(intakeCamera);
            CvSink shooterCvSink = CameraServer.getInstance().getVideo(shooterCamera);
            CvSource outputStream = CameraServer.getInstance().putVideo("OUTPUT", RobotMap.IMG_WIDTH, RobotMap.IMG_HEIGHT);
      
            Mat intakeSource = new Mat();
            Mat shooterSource = new Mat();
            Mat output = new Mat();
      
            while(!Thread.interrupted()) {
              if (intakeCvSink.grabFrame(intakeSource) == 0) {
                continue;
              }
              if (shooterCvSink.grabFrame(shooterSource) == 0) {
                continue;
              }
              if(Drive.shooterFront) {
                intakeSource.copyTo(output);
              }
              else {
                  shooterSource.copyTo(output);
              }
              outputStream.putFrame(output);
            }
          }).start();
    }
}
