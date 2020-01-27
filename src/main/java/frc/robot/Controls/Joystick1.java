/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;

public class Joystick1{
  
  private static Joystick firstJoystick = RobotMap.joystick1;

  public static double getMove(){
    double speed = firstJoystick.getRawAxis(1);
    return speed;
  }
  public static double getTurn(){
    double speed = firstJoystick.getRawAxis(4);
    return speed;
  }
  public static double getChangePanel(){
    double speed = firstJoystick.getRawAxis(0);
    return speed;
  }
}
