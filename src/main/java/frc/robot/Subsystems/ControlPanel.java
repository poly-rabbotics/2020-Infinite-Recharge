/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;

/** 
 * Add your docs here.
 */
public class ControlPanel extends Subsystem {
  private Spark panelMotor;
  private double panelMotorSpeed, requiredRotations, IR;
  private Encoder encoder;
  private boolean spinning;
 private ColorSensorV3 colorSensor;
  private Color detectedColor;
  


  public ControlPanel(){

    panelMotor = RobotMap.controlPanelMotor;
    panelMotorSpeed = .5;
    requiredRotations = 1000; // must be changed
    encoder = RobotMap.controlPanelEncoder;
    spinning = false;
    colorSensor = RobotMap.controlPanelColorSensor;
    detectedColor = colorSensor.getColor();
    IR = colorSensor.getIR();
    

  }
  public void threeRotation(){
   if(DriveJoystick.getToggleRotation() == true){
      panelMotor.set(panelMotorSpeed);
   }
   if(encoder.get() > requiredRotations){
     panelMotor.set(0);
   }
   SmartDashboard.putNumber(" Number of Rotations ", encoder.get());
  }

  public void testRequiredRotation(){
    if(DriveJoystick.getToggleRotation() == true && spinning == false){
      panelMotor.set(panelMotorSpeed);
      spinning = true;
    }
    else if(DriveJoystick.getToggleRotation() == true && spinning == true){
      panelMotor.set(0);
      spinning = false;
    }
    SmartDashboard.putNumber(" Number of Rotations ", encoder.get());
  }

  public void colorSensor(){
    detectedColor = colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
  }

  public void run(){
    panelMotor.set(panelMotorSpeed);
    if(DriveJoystick.getChangePanelSpeed() > 0.1 && panelMotorSpeed <= 1) {
      panelMotorSpeed += .005;
    }
    else if(DriveJoystick.getChangePanelSpeed() < -0.1 && panelMotorSpeed >= 0) {
      panelMotorSpeed -= .005;
    }
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
