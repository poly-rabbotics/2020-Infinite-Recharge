package frc.robot.sensors;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotMap;

public class ColorSensor {
    private ColorSensorV3 colorSensor;
    public static final double DETECTED_HIGH = 0.4; //Min strength of match for a color to be considered to have been detected
    public static final double DETECTED_LOW = 0.2; //Max strength of match for a color to be considered to NOT have been detected
    public static final double DETECTED_MEDIUM = 0.35;

    public ColorSensor() {
        colorSensor = RobotMap.controlPanelColorSensor;
    }

    public char getColor() {
        Color detectedColor = colorSensor.getColor();
        // blue
        if (detectedColor.blue >= DETECTED_HIGH && detectedColor.green >= DETECTED_HIGH
            && detectedColor.red <= DETECTED_LOW) {
          return 'B';
        }
        // green
        if (detectedColor.blue <= DETECTED_MEDIUM && detectedColor.green >= DETECTED_HIGH
            && detectedColor.red <= DETECTED_LOW) {
          return 'G';
        }
        // red
        if (detectedColor.blue <= DETECTED_LOW && detectedColor.green <= DETECTED_MEDIUM
            && detectedColor.red >= DETECTED_HIGH) {
          return 'R';
        }
        // yellow
        if (detectedColor.blue <= DETECTED_LOW && detectedColor.green >= DETECTED_HIGH
            && detectedColor.red >= DETECTED_LOW) {
          return 'Y';
        }
        return '?'; //No color detected
    }
}