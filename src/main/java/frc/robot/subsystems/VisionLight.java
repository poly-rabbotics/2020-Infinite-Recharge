package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.RobotMap;

public class VisionLight extends Subsystem {
    DigitalOutput relay;
    public VisionLight() {
        relay = RobotMap.visionLightRelay;
    }
    public void turnOn() {
        relay.set(true); //TODO: choose this value such that the lights turn off when reset
    }
    public void turnOff() {
        relay.set(false);
    }
    public void run() {
        if(!getLocked()) {
            //Be controlled by manual controls
        }
    }
    public void reset() {
        turnOff(); 
    }
}