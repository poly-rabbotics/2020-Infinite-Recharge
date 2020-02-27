/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;

/**
 * Add your docs here.
 */
public class Drive {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  public static boolean shooterFront;
  public Drive() {

    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.backL, RobotMap.backR);
    drive = new DifferentialDrive(right,left);

    shooterFront = true;

  }

  public void run(){
    double move = DriveJoystick.getMove();
    double turn = DriveJoystick.getTurn();

    if(DriveJoystick.getFront()) {
      shooterFront = !shooterFront;
    }
    
    if (shooterFront){
      drive.arcadeDrive(move, turn);
    }
    else{
      drive.arcadeDrive(-move, turn);
    }
    SmartDashboard.putBoolean("Shooter is Front: ", shooterFront);
    SmartDashboard.putBoolean("getFront: ",DriveJoystick.getFront());
  }
}
