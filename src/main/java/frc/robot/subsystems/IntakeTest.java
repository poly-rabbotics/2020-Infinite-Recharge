/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

public class IntakeTest {
    public static double HOLD_SPEED = 0.05;
    public static double MOVE_SPEED = 1;
    public static double INTAKE_SPEED = 0.25;
    public static double AVOID_INTAKING_SPEED = -0.05;

    private PWMVictorSPX motor, armMotor;
    private boolean out;

    public IntakeTest(){
        motor = RobotMap.intakeMotor;
        motor.setInverted(true);
        armMotor = RobotMap.armMotor;
        out = false;
    }


    public void run(){
        if(MechanismsJoystick.getManIntakeMotor()) {
            motor.set(INTAKE_SPEED);
        }
        else {
            motor.set(AVOID_INTAKING_SPEED);
        }
        if(MechanismsJoystick.isManual()){
            SmartDashboard.putBoolean("out", out);
            SmartDashboard.putNumber("arm motor output", armMotor.get());
            if(MechanismsJoystick.getToggleManArmMotor()) {
                out = !out;
            }
            if(!out){
                if(MechanismsJoystick.getManArmMotor()) {
                    armMotor.set(MOVE_SPEED);
                }
                else {
                    armMotor.set(HOLD_SPEED);
                }
            }
            else {
                if(MechanismsJoystick.getManArmMotor()) {
                    armMotor.set(-MOVE_SPEED);
                }
                else {
                    armMotor.set(-HOLD_SPEED);
                }
            }
        }
    }

}
