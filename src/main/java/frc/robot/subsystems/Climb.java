package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.controls.DriveJoystick;
import frc.robot.controls.MechanismsJoystick;

public class Climb extends Subsystem {
    DoubleSolenoid solenoid;
    static boolean on;

    public Climb() {
        solenoid = RobotMap.climbSolenoid;
        solenoid.set(Value.kReverse);
        on = false;
        
    }
    public void run() {
        boolean toggleClimbingSystem = MechanismsJoystick.getToggleClimbingSystem();
        boolean allowClimbingSystem  = MechanismsJoystick.getAllowClimbingSystem();
        boolean driveAllowClimb = DriveJoystick.getDriveAllowClimb();
        boolean driveToggleClimb = DriveJoystick.getDriveToggleClimb();
        if(allowClimbingSystem) {
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
            on = true;
        }
        else if(driveAllowClimb) {
            // Mode for endgame
            if(driveToggleClimb) {
                // Set the solenoid to the state that it is not currently in
                if(solenoid.get() == Value.kForward) {
                    solenoid.set(Value.kReverse);
                    
                }
                else {
                    solenoid.set(Value.kForward);

                }
            }
            on = true;
        }
        else {
            // Mode for everything except endgame -- cylinder retracted
            solenoid.set(Value.kReverse);
            on = false;
        }
    }
    public void reset() {}
}