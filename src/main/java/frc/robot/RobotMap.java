/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Relay;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
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

  public static final CANSparkMax frontL = new CANSparkMax(1, MotorType.kBrushless);
  public static final CANSparkMax frontR = new CANSparkMax(2, MotorType.kBrushless);
  public static final CANSparkMax backL = new CANSparkMax(3, MotorType.kBrushless);
  public static final CANSparkMax backR = new CANSparkMax(4, MotorType.kBrushless);
  public static final PWMVictorSPX controlPanelMotor = new PWMVictorSPX(0);
  public static final PWMVictorSPX upperConveyorMotor = new PWMVictorSPX(1);
  public static final PWMVictorSPX lowerConveyorMotor = new PWMVictorSPX(2);
  public static final PWMVictorSPX intakeMotor = new PWMVictorSPX(3);
  public static final PWMVictorSPX armMotor = new PWMVictorSPX(4);

  public static final Encoder controlPanelEncoder = new Encoder(0,1); 

  public static final ColorSensorV3 controlPanelColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  public static final DigitalInput intakeSensorOne = new DigitalInput(2);
  public static final DigitalInput shooterSensor = new DigitalInput(3);

  public static final DigitalOutput lightRelay = new DigitalOutput(9);

  public static final TalonSRX shooterTopMotor = new TalonSRX(5);
  public static final TalonSRX shooterBottomMotor = new TalonSRX(6);
  
  public static final DoubleSolenoid climbSolenoid = new DoubleSolenoid(0, 1);
  public static final DoubleSolenoid shooterSolenoid = new DoubleSolenoid(2, 3);
  //public static final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(4,5);
  public static final Servo camera = new Servo(9);

  public static final String shooterCameraName = "Microsoft LifeCam HD-3000";
  public static final DigitalOutput visionLightRelay = new DigitalOutput(9);
}
