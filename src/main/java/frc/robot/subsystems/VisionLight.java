package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay;
import frc.robot.RobotMap;

public class VisionLight extends Subsystem {
    Relay relay;
    public VisionLight() {
        relay = RobotMap.visionLightRelay;
    }
    /**
     * Turns on the light for vision.
    */
    public void turnOn() {
        relay.set(Relay.Value.kForward); //TODO: choose this value such that the lights turn off when reset
    }
    /**
     * Turns off the light for vision.
    */
    public void turnOff() {
        relay.set(Relay.Value.kOff);
    }
    public void advancedRun() {
        if(!getLocked()) {
            turnOff();
        }
    }
    public void autoRun() {
        if(!getLocked()) {
            turnOff();
        }
    }
    public void manualRun() {
        turnOff();
    }
    /**
     * Turns off the light.
    */
    public void reset() {
        turnOff(); 
    }
}
