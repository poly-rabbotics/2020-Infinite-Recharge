package frc.robot.subsystems;

import frc.robot.subsystems.Subsystem;
/**
 * Any Subsystem intended to run during AutonomousPeriodic must be an AutoSubsystem
 * so that its special autoRun() method can be called periodically.
*/
public abstract class AutoSubsystem extends Subsystem {
    /**
     * Method to be called periodically in autonomousPeriodic.
    */
    public abstract void autoRun();
}
