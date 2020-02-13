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
import edu.wpi.first.wpilibj.DoubleSolenoid;


/**
 * Add your docs here.
 */
public class RobotMap {
  public static final Joystick driveJoystick = new Joystick(0);
  public static final Joystick mechanismsJoystick = new Joystick(1);

  public static final PWMVictorSPX frontL = new PWMVictorSPX(2);
  public static final PWMVictorSPX frontR = new PWMVictorSPX(5);
  public static final PWMVictorSPX backL = new PWMVictorSPX(3);
  public static final PWMVictorSPX backR = new PWMVictorSPX(4);

  public static final TalonSRX shooterTopMotor = new TalonSRX(1);
  public static final TalonSRX shooterBottomMotor = new TalonSRX(2);

  public static final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0,1);
  public static final DoubleSolenoid conveyerBeltSolenoid = new DoubleSolenoid(2,3);
  public static final PWMVictorSPX intakeMotor = new PWMVictorSPX(6);
  public static final PWMVictorSPX topBeltMotor = new PWMVictorSPX(7);
  public static final PWMVictorSPX bottomBeltMotor = new PWMVictorSPX(8); 
}
