/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.CameraSetDriveSetpoint;
import frc.robot.controls.DriveJoystick;
import frc.robot.controls.MechanismsJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import frc.robot.utils.*;
import frc.robot.sensors.ShooterCamera;

/**
 * Add your docs here.
 */
public class Drive extends AutoSubsystem {
  private static final double FINE_ADJUSTMENT_MAGNITUDE = 2.5;
  private static final double MANUAL_TURN_CONSTANT = 20;

  private boolean shooterFront = true;

  private CustomDifferentialDrive drive;
  private AHRS ahrs;
  private PIDController turnController;
  private double speed, rotation;
  private ShooterCamera camera;
  private DriveMotor[] leftMotors, rightMotors;
  private CANSparkMax leftMotorFront, leftMotorBack, rightMotorFront, rightMotorBack;

  public Drive() {
    //Initialize left and right motors
    leftMotorFront = RobotMap.leftFront;
    leftMotorBack = RobotMap.leftBack;
    rightMotorFront = RobotMap.rightFront;
    rightMotorBack = RobotMap.rightBack;
    leftMotors  = new DriveMotor[]{new DriveMotor(RobotMap.leftFront), new DriveMotor(RobotMap.leftBack)};
    rightMotors = new DriveMotor[]{new DriveMotor(RobotMap.rightFront), new DriveMotor(RobotMap.rightBack)};
    for (DriveMotor motor: leftMotors) {
      motor.setUpConstants();
    }
    for (DriveMotor motor: rightMotors) {
      motor.setUpConstants();
    }
    //Pass left and right motors on to drive
    //SpeedControllerGroup left = new SpeedControllerGroup(leftMotors[0].getMotor(), leftMotors[1].getMotor());
    //SpeedControllerGroup right = new SpeedControllerGroup(rightMotors[0].getMotor(), rightMotors[1].getMotor());
    //SpeedControllerGroup left = new SpeedControllerGroup(RobotMap.leftFront, RobotMap.leftBack);
    //SpeedControllerGroup right = new SpeedControllerGroup(RobotMap.rightFront, RobotMap.rightBack);
    this.drive = new CustomDifferentialDrive(rightMotors, leftMotors);
    
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
  public void applyRotationalPID() {
    rotation = turnController.calculate(ahrs.getAngle());
  }
  public void setRotationalSetpointRelativeToCurrentPos(double change) {
    turnController.setSetpoint(ahrs.getAngle() + change);
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
  public boolean aligned(double tolerance) {
    return Math.abs(ahrs.getAngle() - turnController.getSetpoint()) < tolerance;
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
    //leftMotorFront.set(1);
    //leftMotorBack.set(1);
    drive.arcadeDrive(speed, rotation);
  }
  public boolean getShooterFront() {
    return shooterFront;
  }
  public void printState() {
    SmartDashboard.putNumber("Turn", -1);
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("setpoint", turnController.getSetpoint());
    SmartDashboard.putNumber("Accumulated Error", turnController.getAccumulatedError());
    SmartDashboard.putBoolean("Shooter is Front: ", shooterFront);
  }
  public void cameraOrient() {
    setRotationalSetpointRelativeToCurrentPos(camera.getYaw());
  }
  private void getControllerInput() {
    // SET COOKED ROTATIONAL SETPOINT
    if(DriveJoystick.getAdjustRotationLeft()) {
      setRotationalSetpoint(FINE_ADJUSTMENT_MAGNITUDE);
    }
    else if(DriveJoystick.getAdjustRotationRight()) { //Special fine adjustment
      setRotationalSetpoint(-FINE_ADJUSTMENT_MAGNITUDE);
    }
    else if(DriveJoystick.getCameraOrient()) { //Special auto orient
      (new CameraSetDriveSetpoint("Orient with target")).start();
    }
    else if(DriveJoystick.getTurn() != 0) { //This is the manual control
      setRotationalSetpointRelativeToCurrentPos(TransformationUtils.cubicTransform(DriveJoystick.getTurn()) * MANUAL_TURN_CONSTANT);
    }
    applyRotationalPID();
    

    // SET RAW TRANSLATIONAL SPEED (same no matter which mode, just always raw/medium rare rotation)
    speed = TransformationUtils.squareTransform(DriveJoystick.getMove());
    speed = shooterFront ? speed : -speed;

    // CHANGE FRONT/BACK AS REQUESTED
    if(DriveJoystick.getFront()) {
      shooterFront = !shooterFront;
    }
  }
  
  public void run() {
    if(!getLocked()) {
      getControllerInput();
      move();
    }
  }
  public void manualRun() {
    reset();
    rotation = TransformationUtils.cubicTransform(DriveJoystick.getTurn());    

    // SET RAW TRANSLATIONAL SPEED (same no matter which mode, just always raw/medium rare rotation)
    speed = TransformationUtils.squareTransform(DriveJoystick.getMove());
    speed = shooterFront ? speed : -speed;

    // CHANGE FRONT/BACK AS REQUESTED
    if(DriveJoystick.getFront()) {
      shooterFront = !shooterFront;
    }
    move();
  }
  public void autoRun() {
    move();
  }
}