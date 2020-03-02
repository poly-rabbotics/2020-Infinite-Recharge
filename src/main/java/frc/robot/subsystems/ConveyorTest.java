/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// N O T   F I N A L; PURELY TESTING

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

public class ConveyorTest {

private PWMVictorSPX top, bottom;
private double speed;


public ConveyorTest(){
  top = RobotMap.upperConveyorMotor;
  top.setInverted(true);
  bottom = RobotMap.lowerConveyorMotor;
  speed = 0.95;
}

public void run(){
  if(MechanismsJoystick.isManual()){
  if (MechanismsJoystick.getToggleManTopConveyor()) {
    top.set(speed);
  }
  else {
    top.set(0);
  }

  if (MechanismsJoystick.getToggleManBottomConveyor()) {
    bottom.set(speed);
  }
  else {
    bottom.set(0);
  }
}
}

}
