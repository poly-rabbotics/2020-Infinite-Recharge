package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;
import frc.robot.Controls.MechanismsJoystick;

public class Climb {
    DoubleSolenoid solenoid;

    public Climb() {
        solenoid = RobotMap.climbSolenoid;
        solenoid.set(Value.kReverse);
    }
    public void run() {
        if (MechanismsJoystick.getToggleClimbingSystem()) {
            if(solenoid.get() == Value.kForward) {
                solenoid.set(Value.kReverse);
            }
            else {
                solenoid.set(Value.kForward);
            }
        }
    }
}