/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class ControlPanel implements Subsystem {
  private Spark panelMotor;
  private double panelMotorSpeed;

  public ControlPanel(){

    panelMotor = RobotMap.controlPanelMotor;
    panelMotorSpeed = .5;

  }

  public void run(){
    panelMotor.set(panelMotorSpeed);
    if(MechanismsJoystick.getChangePanelSpeed() > 0.1 && panelMotorSpeed <= 1) {
      panelMotorSpeed += .005;
    }
    else if(MechanismsJoystick.getChangePanelSpeed() < -0.1 && panelMotorSpeed >= 0) {
      panelMotorSpeed -= .005;
    }
  }
  public void reset() {}
}