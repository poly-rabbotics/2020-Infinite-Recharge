/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;

/**
 * Add your docs here.
 */
public class Drive {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  static boolean shooterFront;
  public Drive() {

    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.backL, RobotMap.backR);
    drive = new DifferentialDrive(right,left);

    shooterFront = true;

  }

  public void run(){
    double move = DriveJoystick.getMove();
    double turn = DriveJoystick.getTurn();
  
    if(DriveJoystick.getFront() == true && shooterFront){
      shooterFront = false;
    }
    else if(DriveJoystick.getFront() == true && shooterFront == false){
      shooterFront = true;
    }
    if (shooterFront == true){
      drive.arcadeDrive(move, turn);
    }
    else{
      drive.arcadeDrive(-move, turn);
    }
  }
}
