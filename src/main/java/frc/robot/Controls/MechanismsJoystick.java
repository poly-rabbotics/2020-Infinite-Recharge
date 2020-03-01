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
    return joystick.getRawButton(10);

  }

  public static boolean getAllowClimbingSystem(){
    return joystick.getRawButton(4);
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
<<<<<<< HEAD

    public static boolean isManual(){
      if(isManual == true){
        controlState = "Manual";
      }
      else{
        controlState = "Normal";
      }
      return joystick.getRawButton(11);
    }
=======
    //      MANUAL CONTROLS

    public static boolean isManual (){
      return joystick.getRawButton(11);
    }
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
    public static boolean getToggleManIntakeMotor(){
      return joystick.getRawButton(3);
    }
    public static boolean getToggleManIntakeSolenoid(){
      return joystick.getRawButton(2);
    }
    public static boolean getToggleManShooterSolenoid(){
      return joystick.getRawButtonPressed(1);
    }
    public static boolean getToggleManColorWheel(){
     if (joystick.getRawAxis(1) >=0.5){
       return true;
     }
    else {
      return false;
    }
    }


>>>>>>> Manual-Controls
}
