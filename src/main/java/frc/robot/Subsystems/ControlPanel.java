/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class ControlPanel extends Subsystem {
  private Spark panelMotor;
  private int panelMotorSpeed;

  public ControlPanel(){

    panelMotor = RobotMap.controlPanelMotor;
    panelMotorSpeed = .5;

  }

  public void Run(){
    if(MechanismsJoystick.getChanePanelSpeed() > 0.1 && panelMotorSpeed <= 1) {
      panelMotorSpeed += .005;
    }
    else if(MechanismsJoystick.getChangePanelSpeed() < -0.1 && panelMotorSpeed >= 0) {
      panelMotorSpeed -= .005;
    }
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
