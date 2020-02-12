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

import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.utils.*;

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
    kGains = new KGains(0.0, 0.0001, 0, 0);
    turnController = new PIDController(kGains, true);
    //turnController.setTolerance(0.01);
  }
  public void setRotationalSetpoint() {
    turnController.setSetpoint(ahrs.getAngle() + 30); // this is just for testing. Will be replaced with vision.
  }
  public void autoOrient() {
    SmartDashboard.putNumber("Turn", turnController.calculate(ahrs.getAngle(), 30));
    
    double turn = turnController.calculate(ahrs.getAngle());
    double move = 0;
    drive.arcadeDrive(move, -turn);
  }
  public void run() {
    SmartDashboard.putNumber("Turn", -1);
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("setpoint", turnController.getSetpoint());
    SmartDashboard.putNumber("Accumuluated Error", turnController.getAccumulatedError());
    if(DriveJoystick.getStartAutoOrient()) {
      setRotationalSetpoint();
    }
    if(DriveJoystick.getContinueAutoOrient()) {
      autoOrient();
    }
    else {
      double move = DriveJoystick.getMove();
      double turn = DriveJoystick.getTurn();
      drive.arcadeDrive(move, turn);
    }
  }
}