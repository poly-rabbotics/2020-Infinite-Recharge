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
 PWMVictorSPX lowerConveyor,upperConveyor, intake;
 DigitalInput upperSensor, lowerSensor;
 double speed, currentTimeOne, currentTimeTwo, delay, count, countTwo, speedTwo;
 int ballCount;
 boolean lowerBallDetected, upperBallDetected, shooterIsSpeed, test, detectedUp, detectedBottom;

 public ConveyorBelt(){
  lowerConveyor = RobotMap.lowerConveyorMotor;
  upperConveyor = RobotMap.upperConveyorMotor;
  intake = RobotMap.intakeMotor;
  lowerSensor = RobotMap.intakeSensorOne;
  upperSensor = RobotMap.shooterSensor;
  speed = 0.6;
  speedTwo = 0.4;
  ballCount = 0;
  lowerBallDetected = false;
  upperBallDetected = false;
  shooterIsSpeed = false;
  currentTimeOne = 0;
  currentTimeTwo = 0;
  delay = 0.01;
  test = false;
  count = 0;
  countTwo = 0;
  detectedUp = false;
  detectedBottom = false;
 }

  public void upperThreshold(){

    if(!upperSensor.get()){
      count ++;
    }
    else{
      count = 0;
      detectedUp = false;
    }
    if( count > 3 ){
      detectedUp = true;
    }
  }
  public void lowerThreshold(){

    if(!lowerSensor.get()){
      countTwo ++;
    }
    else{
      countTwo = 0;
      detectedBottom = false;
    }
    if( countTwo > 3 ){
      detectedBottom = true;
    }
  }

  public void countBalls() {
    //Check for balls entering the mechanism
    if(detectedBottom) {
      lowerBallDetected = true;
      currentTimeOne = Timer.getFPGATimestamp();
    }
    if(lowerBallDetected && !detectedBottom){
      ballCount += 1;
      lowerBallDetected = false;
    }
    //Check for balls leaving the mechanism
    if(detectedUp) {
      upperBallDetected = true;
    }
    if(upperBallDetected == true && !detectedUp){
      ballCount -= 1;
      upperBallDetected = false;
    }
  }
  public void storeBalls(){
    
    if(detectedBottom && ballCount < 5){
      lowerConveyor.set(speed);
      upperConveyor.set(-speed);
      test = true;
    }
    else{
      if(currentTimeOne + delay >= Timer.getFPGATimestamp()){
        lowerConveyor.set(speed);
        upperConveyor.set(-speed);
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
    if(MechanismsJoystick.getAllowConveyor()){
      lowerConveyor.set(speed);
      upperConveyor.set(-speed);
    }
    else if(MechanismsJoystick.getToggleConveyorOverride() == true && !detectedUp){
      lowerConveyor.set(speed);
      upperConveyor.set(-speed);
      test = true;
        }
    else{
      storeBalls();
    }
  }

  public void intakeArm(){
    if(MechanismsJoystick.getTestsIntake()){
      intake.set(-speedTwo);
    }
    else{
      intake.set(0);
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
    intakeArm();
    lowerThreshold();
    upperThreshold();
    conveyorOverride();
    SmartDashboard.putNumber("Number of Balls in Intake", ballCount);
    SmartDashboard.putBoolean("First Sensor", lowerSensor.get());
    SmartDashboard.putBoolean("First Detecte", detectedBottom);
    SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Current Time", currentTimeOne);
    SmartDashboard.putBoolean("Is Intaking", test);
  }
 
}
