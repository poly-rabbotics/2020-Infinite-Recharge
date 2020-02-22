/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveJoystick {
  
  private static Joystick joystick = RobotMap.driveJoystick;
  private static double lastMoveTime = 0;
  public static double getMove(){
    double speed = joystick.getRawAxis(1);
    return speed;
  }
  public static double getTurn(){
    double speed = joystick.getRawAxis(4);
    return speed;
  }
  public static boolean getCameraOrient() {
    return joystick.getRawButtonPressed(1); //A button
  }
  public static boolean getStartAutoOrientLeft() {
    return joystick.getRawButtonPressed(5);
  }
  public static boolean getStartAutoOrientRight() {
    return joystick.getRawButtonPressed(6);
  }
  public static boolean getContinueAutoOrient() {
    if (Math.abs(getMove()) > 0.05 || Math.abs(getTurn()) > 0.05) {
      //Robot has moved
      lastMoveTime = Timer.getFPGATimestamp();
    }
    SmartDashboard.putNumber("last move time", lastMoveTime);
    return Timer.getFPGATimestamp() - lastMoveTime > 0.75;
    //return joystick.getRawButton(5) || joystick.getRawButton(6);
  }

  public static boolean getFront(){
    return joystick.getRawButtonPressed(2);
  }
}
