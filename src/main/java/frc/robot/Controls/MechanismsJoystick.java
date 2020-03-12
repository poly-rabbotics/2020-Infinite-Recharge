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
import frc.robot.subsystems.Shooter;

public class MechanismsJoystick {
  
  private static Joystick joystick = RobotMap.mechanismsJoystick;
  public static String controlState = "Normal";
  public static boolean isManual = false;


  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // SHOOTER ADJUST ----------------------------------------------------------------------------------------------------------------------
  public static double getChangeTopShooter() {
    return joystick.getRawAxis(3);
  }
  public static double getChangeBottomShooter() {
    return joystick.getRawAxis(5);
  }
  public static boolean allowCalibrateShooter(){
    return joystick.getRawButton(3);
  }

  /*public static boolean getToggleShooter(){
    return joystick.getRawButtonPressed(1);
  }
  */

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // SHOOTER CONTROLS  ---------------------------------------------------------------------------------------------------------------------------
  public static boolean getToggleManShootOne(){
    return joystick.getRawButton(5);
  }
  public static boolean getToggleManShootTwo(){
    return joystick.getRawButton(6);
  }

  public static boolean getToggleManShootThree(){
    return joystick.getRawButton(7);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // CLIMBER  ---------------------------------------------------------------------------------------------------------------------------
  public static boolean getToggleClimbingSystem() {
    return joystick.getRawButtonPressed(10);
  }
  public static boolean getAllowClimbingSystem(){
    return joystick.getRawButton(4);
  }
 
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // COLOR WHEEL ---------------------------------------------------------------------------------------------------------------------------
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
      if(joystick.getRawAxis(1) < -0.5){
        return true;
  }
    else{
      return false;
    }
    }

    public static boolean getStopMotor(){
      if(joystick.getRawAxis(1) > 0.5){
        return true;
  }
    else{
      return false;
    }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONVEYOR / INTAKE ----------------------------------------------------------------------------------------------------------------------
    /*public static boolean getTestsIntake(){
      return joystick.getRawButton(2);
    }
    */

    public static boolean getReverseConveyor(){
      return joystick.getRawButton(1);
    }
    public static boolean getToggleConveyorOverride(){
      return joystick.getRawButton(8);
    }
    
    public static boolean getToggleIntakeMotor(){
      return joystick.getRawButton(3);
    }
    public static boolean getToggleArmMotor(){
      return joystick.getRawButton(9);
    }
    public static boolean getToggleManArmMotor(){
      return joystick.getRawButtonPressed(9);
    }
    public static boolean getAllowShooter(){
      if(getToggleManShootOne() || getToggleManShootTwo() || getToggleManShootThree()){
        return true;
        }
      else{return false;}
    }

    public static boolean getEject(){
      return joystick.getRawButton(12);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // AUTO SWITCHES ----------------------------------------------------------------------------------------------------------------------
    public static boolean autoSwitchOne(){
      return joystick.getRawButton(1);
    }

    public static boolean autoSwitchTwo(){
      return joystick.getRawButton(2);
    }

    public static boolean autoSwitchThree(){
      return joystick.getRawButton(3);
    }
    

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MANUAL ONLY COMMANDS ----------------------------------------------------------------------------------------------------------------------
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public static boolean isManual(){
      if(isManual == true){
        controlState = "Manual";
      }
      else{
        controlState = "Normal";
      }
      return joystick.getRawButton(11);
    }

   /* public static boolean getToggleManTopConveyor(){
      return joystick.getRawButton(9);
    }
    public static boolean getToggleManBottomConveyor(){
      return joystick.getRawButton(8);
    }
    */
    
    public static boolean getToggleManShooterSolenoid(){
      return joystick.getRawButton(12);
    }
    public static boolean getToggleManColorWheel(){
     if (joystick.getRawAxis(1) >=0.5){
       return true;
     }
    else {
      return false;
    }
  }
 

}
