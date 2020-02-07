/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;

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

  public static final Spark controlPanelMotor = new Spark(0);
  public static final Encoder controlPanelEncoder = new Encoder(0 , 1);
}
