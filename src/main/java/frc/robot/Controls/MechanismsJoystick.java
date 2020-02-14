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
    TARGETZONE, INITIATIONLINE, TRENCHRUNCLOSE
  }
  
  private static Joystick joystick = RobotMap.mechanismsJoystick;

  public static double getChangeTopShooter() {
    return joystick.getRawAxis(1);
  }
  public static double getChangeBottomShooter() {
    return joystick.getRawAxis(5);
  }
  public static boolean getToggleClimbingSystem() {
    return joystick.getRawButton(5) && joystick.getRawButton(6) 
        && joystick.getRawButton(7) && joystick.getRawButton(8);
  }
  public static boolean getIncreaseSpeedRatio() {
    return joystick.getRawButton(5);
  }
  public static boolean getDecreaseSpeedRatio() {
    return joystick.getRawButton(6);
  }

  public static boolean getShooterPreset(PresetNames presetName) {
    //TODO: change to actual channel numbers
    switch(presetName) {
      case TARGETZONE:
        return joystick.getRawButton(-1);
      case INITIATIONLINE:
        return joystick.getRawButton(-2);
      case TRENCHRUNCLOSE:
        return joystick.getRawButton(-3);
      default:
        //default to TRENCHRUNCLOSE. Note that this default should never be used, and is only included for robustness.
        return joystick.getRawButton(-3);
    }
  }
}
