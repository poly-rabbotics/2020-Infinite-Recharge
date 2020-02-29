/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private PWMVictorSPX intakeWheel, topConveyorBelt, bottomConveyorBelt, leftArm, rightArm;
  boolean out;
  double intakeSpeed, armSpeed, topBeltSpeed,bottomBeltSpeed;
  DigitalInput one, two;

  public Intake() {
    leftArm = RobotMap.armMotorOne;
    rightArm = RobotMap.armMotorTwo;
    intakeWheel = RobotMap.intakeMotor;
    topConveyorBelt = RobotMap.topBeltMotor;
    bottomConveyorBelt = RobotMap.bottomBeltMotor; 
    out = false;
    intakeSpeed = 0.5;
    armSpeed = 0.5;
    topBeltSpeed = 0;
    bottomBeltSpeed = 0;
    one = RobotMap.limitSwitchOne;
    two = RobotMap.limitSwitchTwo;
  }

  //Pneumatics controller
 
  private void runIntakeMotor(){
    intakeWheel.set(intakeSpeed);
    if(MechanismsJoystick.getChangeIntake() > 0.1 && intakeSpeed < 1){
      intakeSpeed += .005;
    }
    else if(MechanismsJoystick.getChangeIntake() < -0.1 && intakeSpeed > 0){
      intakeSpeed -= .005;
    }
  }

private void runArmMotors(){

  if(MechanismsJoystick.getToggleArm() && out == false && !one.get()){
    leftArm.set(armSpeed);
    rightArm.set(-armSpeed);
    out = true;
  }
  else if(MechanismsJoystick.getToggleArm() && out == true && !two.get()){
    leftArm.set(-armSpeed);
    rightArm.set(armSpeed);
    out = false;
  }
  else{
    leftArm.set(0);
    rightArm.set(0);
  }

}

  private void runBeltMotors(){
    topConveyorBelt.set(topBeltSpeed);
    bottomConveyorBelt.set(bottomBeltSpeed);

    //toggle top belt
    if (MechanismsJoystick.getToggleTopBelt() == true && topBeltSpeed == 0){
      topBeltSpeed = 0.5;
    }
    else if (MechanismsJoystick.getToggleTopBelt() == true && topBeltSpeed == 0.5){
      topBeltSpeed = 0;
    }
    //toggle bottom belt
    if (MechanismsJoystick.getToggleBottomBelt() == true && bottomBeltSpeed == 0) {
    bottomBeltSpeed = 0.5;
   }
   else if (MechanismsJoystick.getToggleBottomBelt() == true && bottomBeltSpeed == 0.5) {
  bottomBeltSpeed = 0;
   }
  
  }
   public void run(){
    runIntakeMotor();
    runArmMotors();
    runBeltMotors();
    SmartDashboard.putNumber("Motor Speed: ", armSpeed);
   }

  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
