/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class ButtonPanel {
    Joystick switches;
    int autoMode; 

    public ButtonPanel(){
        switches = RobotMap.mechanismsJoystick;
 
        autoMode = 0; 
        
    }
    public void autoSwitches(){

        autoMode = 0;
        if(switches.getRawButton(1)){
            autoMode += 1;
        }
        if(switches.getRawButton(2)){
            autoMode += 2;
        }
        if(switches.getRawButton(3)){
            autoMode += 4 ;
        }
        SmartDashboard.putNumber("autoMode", autoMode);
    }
}
