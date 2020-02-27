/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;

public class DriveJoystick {
  
  private static Joystick joystick = RobotMap.driveJoystick;

  public static double getMove(){
    double speed = joystick.getRawAxis(1);
    return speed;
  }
  public static double getTurn(){
    double speed = joystick.getRawAxis(4);
    return speed;
  }

  public static boolean getFront(){
    return joystick.getRawButtonPressed(2);
  }
}
