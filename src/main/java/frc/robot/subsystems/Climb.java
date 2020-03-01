package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;

public class Climb implements Subsystem {
    DoubleSolenoid solenoid;
    boolean on;

    public Climb() {
        solenoid = RobotMap.climbSolenoid;
        solenoid.set(Value.kReverse);
        
    }
    public void run() {
        if (MechanismsJoystick.getToggleClimbingSystem() && MechanismsJoystick.getAllowClimbingSystem()) {
            solenoid.set(Value.kForward);
        }
       else{
            solenoid.set(Value.kReverse);
        }
    }
    public void reset() {}
}