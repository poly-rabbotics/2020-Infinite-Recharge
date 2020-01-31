/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.EncoderType;
import com.revrobotics.CANEncoder;

/**
 * Add your docs here.
 */
public class Drive {
  private SpeedControllerGroup left, right;
  private DifferentialDrive drive;
  //private CANEncoder encoderLeftFront, encoderLeftBack, encoderRightFront, encoderRightBack;
  //private CANPIDController leftFrontPID, leftBackPID, rightFrontPID, rightBackPID;
  public Drive() {
    //basic elements of drive
    left = new SpeedControllerGroup(RobotMap.leftFront, RobotMap.leftBack);
    right = new SpeedControllerGroup(RobotMap.leftBack, RobotMap.rightBack);
    drive = new DifferentialDrive(right, left);
    //restore factory defaults on all drive motors
    setUpMotor(RobotMap.leftFront);
    setUpMotor(RobotMap.leftBack);
    setUpMotor(RobotMap.rightFront);
    setUpMotor(RobotMap.leftBack);
  }
  private void setUpMotor(CANSparkMax sparkMax) {
    sparkMax.restoreFactoryDefaults();
    CANEncoder encoder = sparkMax.getEncoder(EncoderType.kQuadrature, 4096);
    CANPIDController pidController = sparkMax.getPIDController();
    // PID coefficients
    double kP = 0; 
    double kI = 0;
    double kD = 0; 
    double kIz = 0; 
    double kFF = 0; 
    double kMaxOutput = 1; 
    double kMinOutput = -1;

    // set PID coefficients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);
  }
  private void brake() {
    double kD = 1;
    double kP = 0.2;
    //Set kD
    RobotMap.leftFront.getPIDController().setD(kD);
    RobotMap.leftBack.getPIDController().setD(kD);
    RobotMap.rightFront.getPIDController().setD(kD);
    RobotMap.rightBack.getPIDController().setD(kD);
    //set kP
    RobotMap.leftFront.getPIDController().setP(kP);
    RobotMap.leftBack.getPIDController().setP(kP);
    RobotMap.rightFront.getPIDController().setP(kP);
    RobotMap.rightBack.getPIDController().setP(kP);
  }
  public void run(){
    brake();
    /*
    double move = DriveJoystick.getMove();
    double turn = DriveJoystick.getTurn();
    drive.arcadeDrive(move, turn);
    */
  }
}
