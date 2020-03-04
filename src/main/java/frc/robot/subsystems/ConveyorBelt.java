/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// TODO: ADD SAFEGUARD SO THAT BALL COUNT CAN BE RESET. OR GIVE IT MANUAL MODE.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.RobotMap;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.PreloadShooter;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class ConveyorBelt extends AutoSubsystem {
 public static double INTAKE_SPEED = 0.5;
 public static double SHOOT_SPEED = 0.5;
 SpeedControllerGroup motors;
 DigitalInput upperSensor, lowerSensor;
 int ballCount;
 boolean lowerBallDetected, upperBallDetected;

 public ConveyorBelt() {
  // Upper and lower conveyor belt always move together, so they are in the same group.
  motors = new SpeedControllerGroup(RobotMap.lowerConveyorMotor, RobotMap.upperConveyorMotor);
  RobotMap.upperConveyorMotor.setInverted(true); //The upper conveyor motor spins the opposite direction
  lowerSensor = RobotMap.intakeSensor;

  upperSensor = RobotMap.shooterSensor;
  ballCount = 3; //TODO: set this to the appropriate number of balls at the beginning of each match.
  lowerBallDetected = false;
  upperBallDetected = false;
 }
 /**
  * @return whether or not there is a ball at the opening of the intake
 */
 public boolean ballDetectedAtIntake() {
   return !lowerSensor.get();
 }
 /**
  * @return whether or not there is a ball just below where it would touch the shooter
 */
 public boolean ballDetectedAtShooter() {
   return !upperSensor.get();
 }
 /**
  * Must be called frequently (like, every 20ms) to keep track of the balls entering and leaving the mechanism.
  * Edits the number of balls as a member var.
 */
  public void countBalls() {
    //Check for balls entering the mechanism
    if(ballDetectedAtIntake()) {
      lowerBallDetected = true;
    }
    if(lowerBallDetected && !ballDetectedAtIntake()) {
      //Last cycle there was a ball detected, but now there is no ball detected.
      ballCount += 1;
      lowerBallDetected = false;
    }
    //Check for balls leaving the mechanism
    if(ballDetectedAtShooter()) {
      upperBallDetected = true;
    }
    if(upperBallDetected && !ballDetectedAtShooter()) {
      ballCount -= 1;
      upperBallDetected = false;
    }
  }
  // public void storeBalls(){
  //   if(lowerSensor.get() && getNumberOfBalls() < 5){ //5 is maximum allowable number of balls
  //     motors.set(INTAKE_SPEED);
  //   }
  //   else{
  //     motors.set(0);
  //   }
  // }

  // public void conveyorOverride(){
  //   if(MechanismsJoystick.getToggleConveyorOverride() && !upperSensor.get()){
  //     motors.set(INTAKE_SPEED);
  //   }
  //   else {
  //     storeBalls();
  //   }
  // }


  public void run(){
    if(!getLocked()) { //If no command has a lock on this subsystem,
      (new IntakeBall(false)).start(); //then the default behavior is to intake another ball
    }
    if(MechanismsJoystick.getConveyorOverride()) { //Request to preload the shooter
      (new PreloadShooter(false)).start();
    }
    countBalls(); //Update the number of balls in the mechanism as needed
    SmartDashboard.putNumber("Number of Balls in Intake", ballCount);
  }
  public void manualRun() {
    //Top conveyor and bottom conveyor are controlled by separate buttons
    if (MechanismsJoystick.getToggleManTopConveyor()) {
      RobotMap.upperConveyorMotor.set(INTAKE_SPEED);
    }
    else {
      RobotMap.upperConveyorMotor.set(0);
    }

    if (MechanismsJoystick.getToggleManBottomConveyor()) {
      RobotMap.lowerConveyorMotor.set(INTAKE_SPEED);
    }
    else {
      RobotMap.lowerConveyorMotor.set(0);
    }
  }
 /**
  * @return whether or not the mechanism must stop in order to prevent accidentally shooting or dropping a power cell
 */
  public boolean needsToStop() {
    return ballDetectedAtShooter();
  }
  /**
   * @return the number of balls in the mechanism
  */
  public int getNumberOfBalls() {
    return ballCount;
  }
  /**
   * Make everything in the mechanism stop.
  */
  public void stop() {
    motors.set(0);
  }
  /**
   * Moves at the appropriate speed for shooting.
  */
  public void moveForShoot() {
      motors.set(SHOOT_SPEED);
  }
  /**
   * Moves at the appropriate speed for intaking a power cell.
  */
  public void moveForIntake() {
    motors.set(INTAKE_SPEED);
  }
  public void reset() {}
  public void autoRun() {
      countBalls();
      if(!getLocked()) {
        stop();
      }
  }
}
