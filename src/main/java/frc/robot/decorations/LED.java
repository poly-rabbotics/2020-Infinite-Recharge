package frc.robot.decorations;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.RobotMap;

//tikTokLights is the name of the instancevariables in this class, but led is the name in the robot map
public class LED {
    AddressableLED tikTokLights;

    public LED(){
        tikTokLights = RobotMap.led;
    }
}
