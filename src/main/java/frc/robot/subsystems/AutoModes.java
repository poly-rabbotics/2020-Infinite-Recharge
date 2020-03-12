/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.RobotState;
import frc.robot.Robot;
import frc.robot.controls.MechanismsJoystick;

/**
 * Add your docs here.
 */
public class AutoModes {
    public static double autoMode, one, two, three;
 
    public AutoModes(){
        autoMode = 0;
        one = 0;
        two = 0;
        three = 0;
    }
    public void setMode(){
        if(RobotState.isAutonomous() && MechanismsJoystick.autoSwitchOne()){
            one = 1;
        }
        else{one = 0;}
        if(RobotState.isAutonomous() && MechanismsJoystick.autoSwitchTwo()) {
            two = 2;
        }
        else{two = 0;}
        if(RobotState.isAutonomous() && MechanismsJoystick.autoSwitchThree()){
            three = 4;
        }
        else{three = 0;}
        autoMode = one + two + three;
    }

    // Mode One shoots three balls then drives past the initiation line
    public void modeZero(){

        Robot.shooter.autoRun(0, 5, 2);
        Robot.conveyor.autoRun(2, 5);
        Robot.drive.altAutoRun(5, 6, 0.5,0);
    }

    public void modeOne(){
        Robot.drive.altAutoRun(0,1, 0.5,0);
        Robot.drive.altAutoRun(1,6, 0, 0);
        Robot.shooter.autoRun(1, 6, 1);
        Robot.conveyor.autoRun(3,6);
        Robot.drive.altAutoRun(6, 7, -0.5, 0);
        Robot.drive.altAutoRun(7, 15, 0, 0);
    }

    public void modeTwo(){
        Robot.drive.altAutoRun(0, 2, 0.4, 0);
        Robot.drive.altAutoRun(2, 4, 0, 0.5);
    }

    public void modeThree(){
        Robot.drive.altAutoRun(5, 6, 0.5,0);
        Robot.shooter.autoRun(6, 11, 1);
        Robot.conveyor.autoRun(8, 11);
        
    }

    public void modeFour(){
        Robot.shooter.autoRun(0, 5, 1);
        Robot.conveyor.autoRun(2, 5);
        Robot.drive.altAutoRun(5, 6, -0.5, 0);
    }

    public void modeFive(){
        Robot.shooter.autoRun(3, 9, 2);
        Robot.conveyor.autoRun(5, 9);
        Robot.drive.altAutoRun(9, 10, 0.5, 0);
    }

    public void modeSix(){
        Robot.drive.altAutoRun(0, 1, 0.5, 0);
        Robot.shooter.autoRun(1, 6, 1);
        Robot.conveyor.autoRun(3,6);
        
    }

    public void modeSeven(){
        Robot.drive.altAutoRun(0,1,0.5,0);
        Robot.drive.altAutoRun(1,15,0,0);
        Robot.shooter.autoRun(8, 14, 1);
        Robot.conveyor.autoRun(10, 14);
    }
    

    public void run(){
        if(autoMode == 0){
            modeZero();
        }
        else if(autoMode == 1){
            modeOne();
        }
        else if(autoMode == 2){
            modeTwo();
        }
        else if(autoMode == 3){
            modeThree();
        }
        else if(autoMode == 4){
            modeFour();
        }
        else if(autoMode == 5){
            modeFive();
        }
        else if(autoMode == 6){
            modeSix();
        }
        else if(autoMode == 7){
            modeSeven();
        }
    }
}
