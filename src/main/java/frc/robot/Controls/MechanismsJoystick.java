/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;

public class MechanismsJoystick {
  public enum PresetNames {
    TARGET_ZONE, INITIATION_LINE, TRENCH_RUN_CLOSE
  }
  
  private static Joystick joystick = RobotMap.mechanismsJoystick;
  public static boolean getToggleClimbingSystem() {
    return joystick.getRawButton(5) && joystick.getRawButton(6) 
        && joystick.getRawButton(7) && joystick.getRawButton(8);
  }

  public static boolean getShooterPreset(PresetNames presetName) {
    //TODO: change to actual channel numbers
    switch(presetName) {
      case TARGET_ZONE:
        return joystick.getRawButton(-1);
      case INITIATION_LINE:
        return joystick.getRawButton(-2);
      case TRENCH_RUN_CLOSE:
        return joystick.getRawButton(-3);
      default:
        //default to TRENCHRUNCLOSE. Note that this default should never be used, and is only included for robustness.
        return joystick.getRawButton(-3);
    }
  }
  public static double getChangePanelSpeed(){
    double speed = joystick.getRawAxis(0);
    return speed;
  }
}
