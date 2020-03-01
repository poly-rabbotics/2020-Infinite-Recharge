/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;

import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.utils.*;
import frc.robot.sensors.ShooterCamera;
/**
 * Add your docs here.
 */
public class Drive implements Subsystem, AutoSubsystem {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  private AHRS ahrs;
  private KGains kGains;
  private PIDController turnController;
  static boolean shooterFront;
  private double speed, rotation;
  private ShooterCamera camera;
  public static String front;
  public Drive() {
    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.frontR, RobotMap.backR);
    shooterFront = true;
    drive = new DifferentialDrive(right, left);
    ahrs = RobotMap.ahrs;
    kGains = new KGains(0.01, 0.002, 1, 0);
    //kGains = new KGains(0.03, 0, 0, 0);
    turnController = new PIDController(kGains, true);
    //turnController.setTolerance(0.01);
    camera = new ShooterCamera(RobotMap.shooterCameraName);
    front = "Shooter";
    reset();
  }
  public void reset() {
    turnController.reset();
    turnController.setSetpoint(ahrs.getAngle());
  }
  public void setRotationalSetpoint(double change) {
    turnController.setSetpoint(turnController.getSetpoint() + change);
  }
  public void setDriveForward(double speed) {
    //System.out.print("Setting drive forward to ");
    //System.out.println(speed);
    this.speed = speed;
  }
  protected boolean aligned(double tolerance) {
    return Math.abs(ahrs.getAngle() - turnController.getSetpoint()) < tolerance;
  }
  protected void autoOrient() {
    rotation = turnController.calculate(ahrs.getAngle());
  }
  public double getAngle() {
    return ahrs.getAngle();
  }
  public double getSetpoint() {
    return turnController.getSetpoint();
  }
  public double getError() {
    return getAngle() - getSetpoint();
  }
  private void move() {
    drive.arcadeDrive(speed, rotation);
  }
  public void printState() {
    SmartDashboard.putNumber("Turn", -1);
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("setpoint", turnController.getSetpoint());
    SmartDashboard.putNumber("Accumulated Error", turnController.getAccumulatedError());
    SmartDashboard.putBoolean("Shooter is Front: ", shooterFront);
  }
  private void getControllerInput() {
    if(DriveJoystick.getStartAutoOrientLeft()) {
      setRotationalSetpoint(5);
    }
    if(DriveJoystick.getStartAutoOrientRight()) {
      setRotationalSetpoint(-5);
    }
    if(DriveJoystick.getCameraOrient()) {
      System.out.println("CAMERA ORIENT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      setRotationalSetpoint(camera.getYaw());
    }
    if(DriveJoystick.getContinueAutoOrient()) {
      autoOrient();
    }
    else {
      turnController.setSetpoint(ahrs.getAngle());
      speed = shooterFront ? DriveJoystick.getMove() : -DriveJoystick.getMove();
      rotation = DriveJoystick.getTurn();
      if(DriveJoystick.getFront()) {
        shooterFront = !shooterFront;
      }
    }
    if(shooterFront){
      front = "Shooter";
    }
    else{
      front = "Intake";
    }
  }
  public void run() {
    System.out.println(camera.getYaw());
   // printState();
    getControllerInput();
    move();

  }
  public void autoRun() {
    //System.out.println(speed);
    autoOrient();
    move();
  }
}