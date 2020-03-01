/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
 public static double SHOOT_SPEED = 0.3;
 SpeedControllerGroup motors;
 DigitalInput upperSensor, lowerSensor, lowerSensorTwo;
 int ballCount;
 boolean lowerBallDetected, upperBallDetected;

 public ConveyorBelt(){
  motors = new SpeedControllerGroup(RobotMap.lowerConveyorMotor, RobotMap.upperConveyorMotor);
  lowerSensor = RobotMap.intakeSensor;

  upperSensor = RobotMap.shooterSensor;
  ballCount = 0;
  lowerBallDetected = false;
  upperBallDetected = false;
 }
  public void countBalls() {
    //Check for balls entering the mechanism
    if(lowerSensor.get()) {
      lowerBallDetected = true;
    }
    if(lowerBallDetected && !lowerSensor.get()){
      ballCount += 1;
      lowerBallDetected = false;
    }
    //Check for balls leaving the mechanism
    if(upperSensor.get()) {
      upperBallDetected = true;
    }
    if(upperBallDetected && !upperSensor.get()){
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
    if(getLock() == "") { //If no command has a lock on this subsystem
      (new IntakeBall("Intake ball " + getNumberOfBalls(), 20, false)).start();
    }
    if(MechanismsJoystick.getToggleConveyorOverride()) {
      (new PreloadShooter("Preload shooter", 20, false)).start();
    }
    countBalls();
    SmartDashboard.putNumber("Number of Balls in Intake", ballCount);
  }
  public boolean needsToStop() {
    return upperSensor.get();
  }
  public int getNumberOfBalls() {
    return ballCount;
  }
  public void stop() {
    motors.set(0);
  }
  public void moveForShoot() {
      motors.set(SHOOT_SPEED);
  }
  public void moveForIntake() {
    motors.set(INTAKE_SPEED);
  }
  public void reset() {}
  public void autoRun() {
      countBalls();
  }
}