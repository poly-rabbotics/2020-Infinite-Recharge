/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

public class IntakeTest {
    private PWMVictorSPX motor;
    private DoubleSolenoid solenoid;
    private boolean out;
    private double speed;

    
public IntakeTest(){
    motor = RobotMap.intakeMotor;
    solenoid = RobotMap.intakeSolenoid;
    out = false;
    speed = 0.5;
}


public void run(){
if(MechanismsJoystick.isManual()){
    if (MechanismsJoystick.getToggleManIntakeMotor()){
        motor.set(speed);
    }
    else {
        motor.set(0);
    }

    if (MechanismsJoystick.getToggleManIntakeSolenoid() && out == false){
        solenoid.set (Value.kForward);
        out = true;
    }
    else if (MechanismsJoystick.getToggleManIntakeSolenoid() && out == true) {
        solenoid.set (Value.kReverse);
        out = false;
    }
}
}

}
