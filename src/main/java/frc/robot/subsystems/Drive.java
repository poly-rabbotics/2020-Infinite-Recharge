/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
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
public class Drive extends AutoSubsystem {
  static boolean shooterFront = true;

  private DifferentialDrive drive;
  private AHRS ahrs;
  private PIDController turnController;
  private double speed, rotation;
  private ShooterCamera camera;
  private DriveMotor[] leftMotors, rightMotors;
  public Drive() {
    //Initialize left and right motors
    leftMotors  = new DriveMotor[]{new DriveMotor(RobotMap.leftFront), new DriveMotor(RobotMap.leftBack)};
    rightMotors = new DriveMotor[]{new DriveMotor(RobotMap.leftFront), new DriveMotor(RobotMap.leftBack)};
    //Pass left and right motors on to drive
    SpeedControllerGroup left = new SpeedControllerGroup(leftMotors[0].getMotor(), leftMotors[1].getMotor());
    SpeedControllerGroup right = new SpeedControllerGroup(rightMotors[0].getMotor(), rightMotors[1].getMotor());
    this.drive = new DifferentialDrive(right, left);
    
    //Set up turn controller
    KGains kGains = new KGains(0.01, 0.002, 1, 0);
    this.turnController = new PIDController(kGains, true);
    //turnController.setTolerance(0.01);

    //Set up inputs
    this.ahrs = RobotMap.ahrs;
    camera = new ShooterCamera(RobotMap.shooterCameraName);
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
  public void setTranslationalSetpoint(double change) {
    for(DriveMotor motor : leftMotors) {
      motor.setSmartTranslationSetpoint(change);
    }
    for(DriveMotor motor : rightMotors) {
      motor.setSmartTranslationSetpoint(change);
    }
  }
  public double getPosition() { //return mean position of all drive motors
    return (leftMotors[0].getPosition() + leftMotors[1].getPosition() 
            + rightMotors[0].getPosition() + rightMotors[1].getPosition()) / 4;
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
  }
  
  public void run() {
    System.out.println(camera.getYaw());
    printState();
    if(!getLocked()) {
      getControllerInput();
      move();
    }
  }
  public void autoRun() {
    //System.out.println(speed);
    if(!getLocked()) {
      autoOrient();
      move();
    }
  }
}