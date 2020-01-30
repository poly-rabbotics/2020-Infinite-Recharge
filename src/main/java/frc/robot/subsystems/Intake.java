/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
private DoubleSolenoid intakePneumatic;
private PWMVictorSRX intakeWheel;
boolean out;
double motorSpeed;

  public Intake() { 
    intakePneumatic = RobotMap.intakeSolenoid;
    intakeWheel = RobotMap.intakeMotor;
    out = false;
    motorSpeed = 0.5;
  }
  
  public void runPneumatics(){
    if(MechanismJoystick.getToggleArm = true && out = false){
      intakePneumatic.set(Value.kForward);
      out = true;
    }
    else if(MechanismJoystick.getToggleArm = true && out = true){
      intakePneumatic.set(Value.kReverse);
      out = false;
    }
  }
  public void runMotor(){
    intakeWheel.set(motorSpeed);
    if(MechanismJoystick.getChangeIntake > 0.1 && motorSpeed < 1){
      motorSpeed += .005;
    }
    else if(MechanismJoystick.getChangeIntake < -0.1 && motorSpeed > 0){
      motorSpeed -= .005;
    }

    public void run(){
      runPneumatics();
      runMotor();
    }

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
