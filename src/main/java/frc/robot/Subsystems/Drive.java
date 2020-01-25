/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.Controls.Joystick1;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  public Drive() {

    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.backL, RobotMap.backR);
    drive = new DifferentialDrive(right,left);

  }

  public void robotDrive(){
    double move = Joystick1.getMove();
    double turn = Joystick1.getTurn();
    drive.arcadeDrive(move, turn);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
