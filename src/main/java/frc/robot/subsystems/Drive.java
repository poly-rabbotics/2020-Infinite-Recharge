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
import frc.robot.RobotMap;
import frc.robot.commands.CameraSetDriveSetpoint;
import frc.robot.controls.DriveJoystick;
import frc.robot.controls.MechanismsJoystick;

import com.kauailabs.navx.frc.AHRS;
import frc.robot.utils.*;
import frc.robot.sensors.ShooterCamera;


/**
 * Add your docs here.
 */
public class Drive extends AutoSubsystem {
  private static final double FINE_ADJUSTMENT_MAGNITUDE = 2.5;
  private static final double MANUAL_TURN_CONSTANT = 20;

  private boolean shooterFront = true;

  private DifferentialDrive drive;
  private AHRS ahrs;
  private PIDController turnController;
  private double speed, rotation;
  private ShooterCamera camera;
  private DriveMotor[] leftMotors, rightMotors;
  /**
   * Initializes left and right motors, drive, turncontroller, and drivetrain-related sensors.
  */
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
  /**
   * Resets the turn controller.
  */
  public void reset() {
    turnController.reset();
    turnController.setSetpoint(ahrs.getAngle());
  }
  /**
   * Adjusts the rotational setpoint with respect to what the setpoint is right now.
   * @param change the amount by which the setpoint itself should be changed (positive in the counterclockwise direction)
  */
  public void setRotationalSetpoint(double change) {
    turnController.setSetpoint(turnController.getSetpoint() + change);
  }
  /**
   * Adjusts the desired rotation based on the current turning setpoint and PID.
  */
  public void applyRotationalPID() {
    rotation = turnController.calculate(ahrs.getAngle());
  }
  /**
   * Adjusts the rotational setpoint with respect to the robot's current orientation.
   * @param change the amount by which the setpoint should differ from the robot's current orientation (positive in the counterclockwise direction)
  */
  public void setRotationalSetpointRelativeToCurrentPos(double change) {
    turnController.setSetpoint(ahrs.getAngle() + change);
  }
  /**
   * Sets the desired translational speed.
   * @param speed a number in the interval [-1, +1], of which translational speed should be a function.
  */
  public void setDriveForward(double speed) {
    //System.out.print("Setting drive forward to ");
    //System.out.println(speed);
    this.speed = speed;
  }
  /**
   * Sets a translational position setpoint, which should be approached using SmartMotion.
   * @param change the desired translational displaacement of the robot from its current location
  */
  public void setTranslationalSetpoint(double change) {
    for(DriveMotor motor : leftMotors) {
      motor.setSmartTranslationSetpoint(change);
    }
    for(DriveMotor motor : rightMotors) {
      motor.setSmartTranslationSetpoint(change);
    }
  }
  /**
   * Returns the integral from t=0 to present of the dot product of the displacement 
   * vector of the corresponding side of the robot and the unit vector for the direction
   * in which the robot is facing.
   * @return a value that is (basically but not exactly) the mean displacement of both 
              sides of the robot since t=0
  */
  public double getPosition() { //return mean position of all drive motors
    return (leftMotors[0].getPosition() + leftMotors[1].getPosition() 
            + rightMotors[0].getPosition() + rightMotors[1].getPosition()) / 4;
  }
  /**
   * @param tolerance the maximum allowable difference between current position and desired position.
   * @return whether or not the robot has rotated to the correct rotational position.
  */
  public boolean aligned(double tolerance) {
    return Math.abs(ahrs.getAngle() - turnController.getSetpoint()) < tolerance;
  }
  /**
   * @return the difference between the current angle of the robot and the angle in which it was 
   * facing when the robot code started.
  */
  public double getAngle() {
    return ahrs.getAngle();
  }
  /**
   * @return the rotational setpoint of the robot.
  */
  public double getSetpoint() {
    return turnController.getSetpoint();
  }
  /**
   * @return the current distance from the current rotational position to the rotational setpoint.
  */
  public double getError() {
    return getAngle() - getSetpoint();
  }
  /**
   * Moves the robot based on the desirable translational and rotational speeds determined by other 
   * functions.
  */
  private void move() {
    drive.arcadeDrive(speed, rotation);
  }
  /**
   * @return whether or not the shooter side of the robot is to be treated as the front.
  */
  public boolean getShooterFront() {
    return shooterFront;
  }
  /**
   * Reports information about the current state of the drivetrain to the SmartDashboard.
  */
  public void printState() {
    SmartDashboard.putNumber("Turn", -1);
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("setpoint", turnController.getSetpoint());
    SmartDashboard.putNumber("Accumulated Error", turnController.getAccumulatedError());
    SmartDashboard.putBoolean("Shooter is Front: ", shooterFront);
  }
  /**
   * Sets the rotational setpoint based on data from the camera.
  */
  public void cameraOrient() {
    setRotationalSetpointRelativeToCurrentPos(camera.getYaw());
  }
  /**
   * Uses data from the DriveController to determine the desirable rotation and translation values.
  */
  private void getControllerInput() {
    // SET COOKED ROTATIONAL SETPOINT
    if(DriveJoystick.getAdjustRotationLeft()) { //Request to make a small rotation to the left.
      setRotationalSetpoint(FINE_ADJUSTMENT_MAGNITUDE);
    }
    else if(DriveJoystick.getAdjustRotationRight()) { //Request to make a small rotation to the right.
      setRotationalSetpoint(-FINE_ADJUSTMENT_MAGNITUDE);
    }
    else if(DriveJoystick.getCameraOrient()) { //Request to orient the robot toward the goal using the camera.
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
  }
  public void autoRun() {
    if(!getLocked()) {
      move();
    }
  }
}
