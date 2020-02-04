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
import frc.robot.controls.MechanismsJoystick;

import com.kauailabs.navx.frc.AHRS;
import frc.robot.utils.KGains;
import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Add your docs here.
 */
public class Drive {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  private AHRS ahrs;
  private KGains kGains;
  private PIDController turnController;
  public Drive() {
    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.backL, RobotMap.backR);
    drive = new DifferentialDrive(right, left);
    ahrs = RobotMap.ahrs;
    kGains = new KGains(0.03, 0, 0, 0);
    turnController = new PIDController(kGains.kP, kGains.kI, kGains.kD, kGains.kI);
  }
  public void setRotationalSetpoint() {
    turnController.setSetpoint(ahrs.getAngle() + 30); // this is just for testing. Will be replaced with vision.
  }
  public void autoOrient() {
    double turn = turnController.calculate(ahrs.getAngle());
    double move = 0;
    drive.arcadeDrive(move, turn);
  }
  public void run() {
    if(MechanismsJoystick.getStartAutoOrient()) {
      setRotationalSetpoint();
    }
    if(MechanismsJoystick.getContinueAutoOrient()) {
      autoOrient();
    }
    else {
      double move = DriveJoystick.getMove();
      double turn = DriveJoystick.getTurn();
      drive.arcadeDrive(move, turn);
    }
    
  }
}
