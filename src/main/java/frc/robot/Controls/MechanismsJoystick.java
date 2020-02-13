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
  
  private static Joystick joystick = RobotMap.mechanismsJoystick;

  public static double getChangeTopShooter() {
    return joystick.getRawAxis(1);
  }
  public static double getChangeBottomShooter() {
    return joystick.getRawAxis(5);
  }
  public static boolean getToggleClimbingSystem() {
    return joystick.getRawButton(5) && joystick.getRawButton(6) && joystick.getRawButton(7) && joystick.getRawButton(8);
  }
  public static boolean getToggleArm() {
    return joystick.getRawButton(4);
  }
  public static double getChangeIntake() {
    return joystick.getRawAxis(0);
  }
  public static boolean getToggleTopBelt(){
    return joystick.getRawButton(2);
  } 
  public static boolean getToggleBottomBelt(){
    return joystick.getRawButton(3);
    }
  public static boolean getAllowShooter(){
    return joystick.getRawButtonPressed(1);
  }
}
