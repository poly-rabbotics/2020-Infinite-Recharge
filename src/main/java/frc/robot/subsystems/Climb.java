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
        boolean toggleClimbingSystem = MechanismsJoystick.getToggleClimbingSystem();
        boolean allowClimbingSystem  = MechanismsJoystick.getAllowClimbingSystem();
        if (allowClimbingSystem) {
            // Mode for endgame
            if(toggleClimbingSystem) {
                // Set the solenoid to the state that it is not currently in
                if(solenoid.get() == Value.kForward) {
                    solenoid.set(Value.kReverse);
                }
                else {
                    solenoid.set(Value.kForward);
                }
            }
        }
        else {
            // Mode for everything except endgame -- cylinder retracted
            solenoid.set(Value.kReverse);
        }
    }
    public void reset() {}
}