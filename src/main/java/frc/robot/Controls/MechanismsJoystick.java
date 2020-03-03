/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class MechanismsJoystick {
  public enum PresetNames {
    INITIATION_LINE, TRENCH_RUN, COLOR_WHEEL
  }
  
  private static Joystick joystick = RobotMap.mechanismsJoystick;

  public static boolean getShooterPreset(PresetNames presetName) {
    //TODO: change to actual channel numbers
    switch(presetName) {
      case INITIATION_LINE:
        return joystick.getRawButton(-1);
      case COLOR_WHEEL:
        return joystick.getRawButton(-2);
      case TRENCH_RUN:
        return joystick.getRawButton(-3);
      default:
        //default to TRENCHRUNCLOSE. Note that this default should never be used, and is only included for robustness.
        return joystick.getRawButton(-3);
    }
  }
  public static String controlState = "Normal";
  public static boolean isManual = false;

  public static double getChangeTopShooter() {
    return joystick.getRawAxis(1);
  }
  public static double getChangeBottomShooter() {
    return joystick.getRawAxis(5);
  }
  public static boolean getToggleClimbingSystem() {
    return joystick.getRawButtonPressed(10);

  }

  public static boolean getAllowClimbingSystem(){
    return joystick.getRawButton(4);
  }
 
  public static boolean getTurnToRequestedColor(){
    if(joystick.getRawAxis(0) < -0.5){
    return true;
    }
    else{
      return false;
    }

  }
  public static boolean getConveyorOverride(){
    return joystick.getRawButton(-2); //TODO: SET TO ACTUAL CHANNEL NUMBER.
  }
  public static boolean getColorWheelRotation(){
    if(joystick.getRawAxis(0) > 0.5){
          return true;
    }
      else{
        return false;
      }
    }
    public static boolean getManualControlPanel(){
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
    //      MANUAL CONTROLS

 
    public static boolean getToggleManShootOne(){
      return joystick.getRawButton(7);
    }
    public static boolean getToggleManShootTwo(){
      return joystick.getRawButton(6);
    }

    public static boolean getToggleManShootThree(){
      return joystick.getRawButton(5);
    }
    public static boolean getToggleManTopConveyor(){
      return joystick.getRawButton(9);
      
    }
    public static boolean getToggleManBottomConveyor(){
      return joystick.getRawButton(8);
    }
    public static boolean getManIntakeMotor(){
      return joystick.getRawButton(3);
    }
    public static boolean getManArmMotor(){
      return joystick.getRawButton(2);
    }
    public static boolean getToggleManArmMotor(){
      return joystick.getRawButtonPressed(2);
    }
    public static boolean getToggleManShooterSolenoid(){
      return joystick.getRawButton(1);
    }
    public static boolean getManColorWheel(){
     if (joystick.getRawAxis(1) >= 0.5){
       return true;
     }
    else {
      return false;
    }
    }
}
