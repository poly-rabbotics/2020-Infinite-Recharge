/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class ConveyorBelt {
 PWMVictorSPX lowerConveyor, upperConveyor;
 DigitalInput upperSensor, lowerSensorOne, lowerSensorTwo;
 double speed;
 int ballCount;
 boolean lowerBallDetected, upperBallDetected, shooterIsSpeed;

 public ConveyorBelt(){
  lowerConveyor = RobotMap.lowerConveyorMotor;
  upperConveyor = RobotMap.upperConveyorMotor;
  lowerSensorOne = RobotMap.intakeSensorOne;
  lowerSensorTwo = RobotMap.intakeSensorTwo;

  upperSensor = RobotMap.shooterSensor;
  speed = 0.3;
  ballCount = 0;
  lowerBallDetected = false;
  upperBallDetected = false;
  shooterIsSpeed = false;
 }
  public void countBalls() {
    //Check for balls entering the mechanism
    if(lowerSensorOne.get() == true || lowerSensorTwo.get() == true) {
      lowerBallDetected = true;
    }
    if(lowerBallDetected == true && lowerSensorOne.get() == false && lowerSensorTwo.get() == false){
      ballCount += 1;
      lowerBallDetected = false;
    }
    //Check for balls leaving the mechanism
    if(upperSensor.get() == true) {
      upperBallDetected = true;
    }
    if(upperBallDetected == true && upperSensor.get() == false){
      ballCount -= 1;
      upperBallDetected = false;
    }
  }
  public void storeBalls(){
    if(lowerSensorOne.get() == true || lowerSensorTwo.get() == true && getNumberOfBalls() < 5){
      lowerConveyor.set(speed);
      upperConveyor.set(speed);
    }
    else{
      lowerConveyor.set(0);
      upperConveyor.set(0);
    }
  }

  public void conveyorOverride(){
    if(MechanismsJoystick.getToggleConveyorOverride() == true && upperSensor.get() == false){
      lowerConveyor.set(speed);
      upperConveyor.set(speed);
        }
    else{
      storeBalls();
    }
  }

  /*public void allowShooter(){
    if(MechanismsJoystick.getToggleShooter() && shooterIsSpeed){
      lowerConveyor.set(speed);
      upperConveyor.set(speed);
    }
    else{
      conveyorOverride();
    }
  }*/

  public void run(){
    conveyorOverride();
    SmartDashboard.putNumber("Number of Balls in Intake", ballCount);
  }
  public int getNumberOfBalls() {
    return ballCount;
  }
}
