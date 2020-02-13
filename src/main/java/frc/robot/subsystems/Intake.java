/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

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
  private DoubleSolenoid intakePneumatic, conveyerBeltPneumatic;
  private PWMVictorSPX intakeWheel, topConveyorBelt, bottomConveyorBelt;
  boolean out;
  double motorSpeed,topBeltSpeed,bottomBeltSpeed;

  public Intake() {
    intakePneumatic = RobotMap.intakeSolenoid;
    conveyerBeltPneumatic = RobotMap.conveyerBeltSolenoid;
    intakeWheel = RobotMap.intakeMotor;
    topConveyorBelt = RobotMap.topBeltMotor;
    bottomConveyorBelt = RobotMap.bottomBeltMotor; 
    out = false;
    motorSpeed = 0.5;
    topBeltSpeed = 0;
    bottomBeltSpeed = 0;
  }

  //Pneumatics controller
  private void runPneumatics() {
    if (MechanismsJoystick.getToggleArm() == true && out == false) {
      intakePneumatic.set(Value.kForward);
      out = true;
    }
    else if(MechanismsJoystick.getToggleArm() == true && out == true) {
      intakePneumatic.set(Value.kReverse);
      out = false;
    }
    if (MechanismsJoystick.getAllowShooter()){
      conveyerBeltPneumatic.set(Value.kReverse);
    }
    else {
      conveyerBeltPneumatic.set(Value.kForward);
    }
  }
  private void runIntakeMotor(){
    intakeWheel.set(motorSpeed);
    if(MechanismsJoystick.getChangeIntake() > 0.1 && motorSpeed < 1){
      motorSpeed += .005;
    }
    else if(MechanismsJoystick.getChangeIntake() < -0.1 && motorSpeed > 0){
      motorSpeed -= .005;
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
    runPneumatics();
    runBeltMotors();
    SmartDashboard.putNumber("Motor Speed: ", motorSpeed);
   }

  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
