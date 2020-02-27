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
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;

import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class RobotMap {
  public static final Joystick driveJoystick = new Joystick(0);
  public static final Joystick mechanismsJoystick = new Joystick(1);

  public static final AHRS ahrs = new AHRS();

  public static final PWMVictorSPX frontL = new PWMVictorSPX(5);
  public static final PWMVictorSPX frontR = new PWMVictorSPX(7);
  public static final PWMVictorSPX backL = new PWMVictorSPX(6);
  public static final PWMVictorSPX backR = new PWMVictorSPX(8);
  public static final PWMVictorSPX controlPanelMotor = new PWMVictorSPX(0);
  
  public static final Encoder controlPanelEncoder = new Encoder(0,1); 

  public static final ColorSensorV3 controlPanelColorSensor = new ColorSensorV3(I2C.Port.kOnboard);

  public static final TalonSRX shooterTopMotor = new TalonSRX(1);
  public static final TalonSRX shooterBottomMotor = new TalonSRX(2);
  
  public static final DoubleSolenoid climbSolenoid = new DoubleSolenoid(0, 1);
  public static final Servo camera = new Servo(10);

  public static final String shooterCameraName = "Microsoft LifeCam HD-3000";
}
