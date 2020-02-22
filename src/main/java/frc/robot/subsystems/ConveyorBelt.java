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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class ConveyorBelt implements AutoSubsystem {
 public static double INTAKE_SPEED = 0.5;
 public static double SHOOT_SPEED = 0.3;
 SpeedControllerGroup motors;
 DigitalInput upperSensor, lowerSensorOne, lowerSensorTwo;
 int ballCount;
 boolean lowerBallDetected, upperBallDetected;

 public ConveyorBelt(){
  motors = new SpeedControllerGroup(RobotMap.lowerConveyorMotor, RobotMap.upperConveyorMotor);
  lowerSensorOne = RobotMap.intakeSensorOne;
  lowerSensorTwo = RobotMap.intakeSensorTwo;

  upperSensor = RobotMap.shooterSensor;
  ballCount = 0;
  lowerBallDetected = false;
  upperBallDetected = false;
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
      motors.set(INTAKE_SPEED);
    }
    else{
      motors.set(0);
    }
  }

  public void conveyorOverride(){
    if(MechanismsJoystick.getToggleConveyorOverride() == true && upperSensor.get() == false){
      motors.set(INTAKE_SPEED);
        }
    else{
      storeBalls();
    }
  }

  public void stop() {
    motors.set(0);
  }
  public void run(){
    conveyorOverride();
    SmartDashboard.putNumber("Number of Balls in Intake", ballCount);
  }
  public int getNumberOfBalls() {
    return ballCount;
  }
  public void moveForShoot() {
      motors.set(SHOOT_SPEED);
  }
  public void reset() {}
  public void autoRun() {
      countBalls();
  }
}