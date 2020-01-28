package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

public class Climb {
    DoubleSolenoid solenoid;

    public Climb() {
        solenoid = RobotMap.climbSolenoid;
    }
    public void run() {
        
    }
}