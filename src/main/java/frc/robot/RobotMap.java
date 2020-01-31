/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

/**
 * Add your docs here.
 */
public class RobotMap {
  public static final Joystick driveJoystick = new Joystick(0);
  public static final Joystick mechanismsJoystick = new Joystick(1);

  public static final TalonSRX shooterTopMotor = new TalonSRX(1);
  public static final TalonSRX shooterBottomMotor = new TalonSRX(2);

  public static final CANSparkMax leftFront = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax leftBack = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax rightFront = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static final CANSparkMax rightBack = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
}