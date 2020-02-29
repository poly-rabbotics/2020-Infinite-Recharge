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
  public static String controlState = "Normal";
  public static boolean isManual = false;

  public static double getChangeTopShooter() {
    return joystick.getRawAxis(1);
  }
  public static double getChangeBottomShooter() {
    return joystick.getRawAxis(5);
  }
  public static boolean getToggleClimbingSystem() {
    if(joystick.getRawButton(4)&&joystick.getRawButtonPressed(10))
    return true;
    else{
     return false;
    }
  }
 
  public static boolean getToggleColor(){
    if(joystick.getRawAxis(0) < -0.5){
    return true;
    }
    else{
      return false;
    }

  }
  public static boolean getToggleRotation(){
    if(joystick.getRawAxis(0) > 0.5){
          return true;
    }
      else{
        return false;
      }
    }
    public static boolean getToggleManualMotor(){
      return joystick.getRawButton(12);
    }

    public static boolean isManual(){
      if(isManual == true){
        controlState = "Manual";
      }
      else{
        controlState = "Normal";
      }
      return joystick.getRawButton(11);
    }
}
