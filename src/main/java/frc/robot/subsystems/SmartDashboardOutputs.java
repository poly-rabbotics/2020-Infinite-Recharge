/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class SmartDashboardOutputs {
double number, numberTwo; 
boolean intakeArm;
public SmartDashboardOutputs(){
    number=0;
    intakeArm=true;
    numberTwo=0;
}
public void run(){

    // DRIVE VALUES
    SmartDashboard.putString("Front", Drive.front);

    // SHOOTER VALUES
    SmartDashboard.putNumber("Distance from Shooter:", Shooter.distance);
    SmartDashboard.putNumber("Preset", Shooter.preset);

    // CONVEYOR/INTAKE VALUES
    SmartDashboard.putNumber("Number of Balls", number);
    SmartDashboard.putBoolean("Arm is Up",intakeArm);

    // COLORWHEEL VALUES TESTING
    
   /*  SmartDashboard.putBoolean("Called Blue", ControlPanel.blueCall);
    SmartDashboard.putBoolean("Called Green",ControlPanel.greenCall);
    SmartDashboard.putBoolean("Called Red",ControlPanel.redCall);
    SmartDashboard.putBoolean("Called Yellow",ControlPanel.yellowCall);

    SmartDashboard.putBoolean("Running Blue",ControlPanel.blueRun);
    SmartDashboard.putBoolean("Running Green",ControlPanel. greenRun);
    SmartDashboard.putBoolean("Running Red",ControlPanel.redRun);
    SmartDashboard.putBoolean("Running Yellow",ControlPanel.yellowRun);

    SmartDashboard.putNumber("Red", ControlPanel.detectedColor.red);
    SmartDashboard.putNumber("Green", ControlPanel.detectedColor.green);
    SmartDashboard.putNumber("Blue", ControlPanel.detectedColor.blue);
    */

    // COLORWHEEL VALUES IN GAME
    SmartDashboard.putString("Color Called", ControlPanel.colorCalledName);
    SmartDashboard.putString("Color Detected", ControlPanel.colorDetectedName);
    SmartDashboard.putBoolean("The Color Choice Method is On",ControlPanel.colorOn);
    SmartDashboard.putBoolean("Rotations are going", ControlPanel.rotationOn);

    // AUTONOMOUS MODE
    SmartDashboard.putNumber("Automode ",numberTwo);

    // CONTROLS
    SmartDashboard.putString("Controls", MechanismsJoystick.controlState);
   
}
}
