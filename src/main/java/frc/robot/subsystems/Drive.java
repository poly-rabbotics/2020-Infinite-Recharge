/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
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
public class Drive implements Subsystem {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  private AHRS ahrs;
  private KGains kGains;
  private PIDController turnController;
  static boolean shooterFront;
  public Drive() {
    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.backL, RobotMap.backR);
    shooterFront = true;
    drive = new DifferentialDrive(right, left);
    ahrs = RobotMap.ahrs;
    kGains = new KGains(0.03, 0.002, 0.3, 0);
    turnController = new PIDController(kGains, true);
    //turnController.setTolerance(0.01);
  }
  public void reset() {
    turnController.setSetpoint(ahrs.getAngle());
  }
  protected void setRotationalSetpoint(double change) {
    turnController.setSetpoint(turnController.getSetpoint() + change);
  }
  protected boolean autoOrient(double tolerance) {    
    double turn = turnController.calculate(ahrs.getAngle());
    double move = 0;
    drive.arcadeDrive(move, turn);
    return Math.abs(ahrs.getAngle() - turnController.getSetpoint()) < tolerance;
  }

  public void run() {
    SmartDashboard.putNumber("Turn", -1);
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("setpoint", turnController.getSetpoint());
    SmartDashboard.putNumber("Accumulated Error", turnController.getAccumulatedError());
    if(DriveJoystick.getStartAutoOrientLeft()) {
      setRotationalSetpoint(5);
    }
    if(DriveJoystick.getStartAutoOrientRight()) {
      setRotationalSetpoint(-5);
    }
    if(DriveJoystick.getContinueAutoOrient()) {
      autoOrient(0);
    }
    else {
      turnController.setSetpoint(ahrs.getAngle());
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
      SmartDashboard.putBoolean("getFront: ", DriveJoystick.getFront());
    }
  }
}