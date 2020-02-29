/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class ConveyorBelt {
 PWMVictorSPX lowerConveyor, upperConveyor;
 DigitalInput upperSensor, lowerSensorOne;
 double speed, currentTime, delay;
 int ballCount;
 boolean lowerBallDetected, upperBallDetected, shooterIsSpeed, test;

 public ConveyorBelt(){
  lowerConveyor = RobotMap.lowerConveyorMotor;
  upperConveyor = RobotMap.upperConveyorMotor;
  lowerSensorOne = RobotMap.intakeSensorOne;

  upperSensor = RobotMap.shooterSensor;
  speed = 0.3;
  ballCount = 0;
  lowerBallDetected = false;
  upperBallDetected = false;
  shooterIsSpeed = false;
  currentTime = 0;
  delay = 2.0;
  test = false;
 }
  public void countBalls() {
    //Check for balls entering the mechanism
    if(lowerSensorOne.get() == false) {
      lowerBallDetected = true;
      currentTime = Timer.getFPGATimestamp();
    }
    if(lowerBallDetected == true && lowerSensorOne.get() == true){
      ballCount += 1;
      lowerBallDetected = false;
    }
    //Check for balls leaving the mechanism
    if(upperSensor.get() == false) {
      upperBallDetected = true;
    }
    if(upperBallDetected == true && upperSensor.get() == true){
      ballCount -= 1;
      upperBallDetected = false;
    }
  }
  public void storeBalls(){
    
    if(lowerSensorOne.get() == false && ballCount < 5){
      lowerConveyor.set(speed);
      upperConveyor.set(speed);
      test = true;
    }
    else{
      if(currentTime + delay >= Timer.getFPGATimestamp()){
        lowerConveyor.set(speed);
        upperConveyor.set(speed);
        test = true;
      }
      else{
      lowerConveyor.set(0);
      upperConveyor.set(0);
      test = false;
      }
    }
  }

  public void conveyorOverride(){
    if(MechanismsJoystick.getToggleConveyorOverride() == true && upperSensor.get() == true){
      lowerConveyor.set(speed);
      upperConveyor.set(speed);
      test = true;
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
    countBalls();
    conveyorOverride();
    SmartDashboard.putNumber("Number of Balls in Intake", ballCount);
    SmartDashboard.putBoolean("First Sensor", lowerSensorOne.get());
    SmartDashboard.putBoolean("Second Sensor", upperSensor.get());
    SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Current Time", currentTime);
    SmartDashboard.putBoolean("Is Intaking", test);
  }
 
}
