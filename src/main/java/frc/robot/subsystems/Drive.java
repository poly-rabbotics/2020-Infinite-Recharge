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
import frc.robot.vision.GripPipelineBlueCtrlPanel;
import frc.robot.vision.PreProc;
import frc.robot.vision.VerticalTarget;

/**
 * Add your docs here.
 */
public class Drive {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  private AHRS ahrs;
  private KGains kGains;
  private PIDController turnController;
  private VerticalTarget goal;
  static boolean shooterFront;
  public Drive() {
    left = new SpeedControllerGroup(RobotMap.frontL, RobotMap.backL);
    right = new SpeedControllerGroup(RobotMap.backL, RobotMap.backR);
    shooterFront = true;
    drive = new DifferentialDrive(right, left);
    ahrs = RobotMap.ahrs;
    kGains = new KGains(0.03, 0.002, 0.3, 0);
    turnController = new PIDController(kGains, true);
    PreProc goalFinder = new PreProc(0, new GripPipelineBlueCtrlPanel());
    goal = new VerticalTarget(goalFinder, 0.15, 8, 4);
    reset();
    //turnController.setTolerance(0.01);
  }
  public void reset() {
    turnController.setSetpoint(ahrs.getAngle());
  }
  public void deltaRotationalSetpoint(double delta) {
    turnController.setSetpoint(turnController.getSetpoint() + delta); // this is just for testing. Will be replaced with vision.
  }
  public void autoOrient() {    
    double turn = turnController.calculate(ahrs.getAngle());
    double move = 0;
    drive.arcadeDrive(move, turn);
  }

  public void run() {
    SmartDashboard.putNumber("Turn", -1);
    SmartDashboard.putNumber("Angle", ahrs.getAngle());
    SmartDashboard.putNumber("setpoint", turnController.getSetpoint());
    SmartDashboard.putNumber("Accumulated Error", turnController.getAccumulatedError());
    if(DriveJoystick.getStartAutoOrientLeft()) {
      deltaRotationalSetpoint(5);
    }
    if(DriveJoystick.getStartAutoOrientRight()) {
      deltaRotationalSetpoint(-5);
    }
    if(DriveJoystick.getStartAutoOrientVision()) {
      deltaRotationalSetpoint(-goal.getYaw());
    }
    if(DriveJoystick.getContinueAutoOrient()) {
      autoOrient();
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