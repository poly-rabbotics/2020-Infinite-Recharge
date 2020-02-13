package frc.robot.decorations;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogInput;

//tikTokLights is the name of the instancevariables in this class, but led is the name in the robot map
public class LED {
    AddressableLED tikTokLights;
    AddressableLEDBuffer tikTokLightsBuffer;
    int rainbowFirstPixelHue;
    Double timeLED;
    boolean lightsOne;
    Timer tikTokLightsTimer;
    AnalogInput microphone;

    public LED(){
        tikTokLights = RobotMap.led;
        tikTokLightsBuffer = RobotMap.ledBuffer;
        tikTokLights.setLength(tikTokLightsBuffer.getLength());
        tikTokLights.setData(tikTokLightsBuffer);
        tikTokLights.start();
        rainbowFirstPixelHue = 0;
        timeLED = 5.0;
        lightsOne = true;
        tikTokLightsTimer = RobotMap.timer;
        tikTokLightsTimer.start();
        microphone = new AnalogInput(0);

    }

    double intensity()
    {
      return (microphone.getVoltage()-.3)/3.5 ;
    }

    public void cycle(){
        // For every pixel
        for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (rainbowFirstPixelHue + (i * 180 / tikTokLightsBuffer.getLength())) % 180;
          // Set the value
          tikTokLightsBuffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowFirstPixelHue += 3;
        // Check bounds
        rainbowFirstPixelHue %= 180;
        tikTokLights.setData(tikTokLightsBuffer);
      }

      public void oneColor(){
        for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            tikTokLightsBuffer.setRGB(i, (int)(255*intensity()), (int)(255*intensity()), 0);
         }
         tikTokLights.setData(tikTokLightsBuffer);
      }
      public void twoColorsAdjacent(){
        for (var i = 0; i < tikTokLightsBuffer.getLength()/2; i++) {
            // Sets the specified LED to the RGB values for red
            tikTokLightsBuffer.setRGB(i, 0, 255, 0);
         }
         for (var i = tikTokLightsBuffer.getLength()/2; i < tikTokLightsBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            tikTokLightsBuffer.setRGB(i, 255, 0, 0);
         }
         tikTokLights.setData(tikTokLightsBuffer);
      }

      public void twoColorsIntegrated(){
        for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
            if(i%2 == 0){
            tikTokLightsBuffer.setRGB(i, 0, 255, 0);
            }
            else{
            tikTokLightsBuffer.setRGB(i, 255, 255, 0);
            }
         }
         tikTokLights.setData(tikTokLightsBuffer);
      }

      public void oneColorAlternating(){
        if(lightsOne == true && tikTokLightsTimer.get()%timeLED == 0){
        for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
          tikTokLightsBuffer.setRGB(i, 0, 255, 0);
       }
       lightsOne = false;
      }
      else if(lightsOne == true && tikTokLightsTimer.get()%timeLED == 0){
       for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
        tikTokLightsBuffer.setRGB(i, 255, 255, 0);
       }
       lightsOne = true;
     }
     tikTokLights.setData(tikTokLightsBuffer);
     
      }

      public void twoColorsAlternating(){
         
      if(lightsOne == true && tikTokLightsTimer.get()%timeLED == 0){

        for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
            if(i%2 == 0){
            tikTokLightsBuffer.setRGB(i, 0, 255, 0);
            }
            else{
            tikTokLightsBuffer.setRGB(i, 255, 255, 0);
            }
         }
         lightsOne = false;
        }
        else if(lightsOne == false && tikTokLightsTimer.get()%timeLED == 0){
            for (var i = 0; i < tikTokLightsBuffer.getLength(); i++) {
                if(i%2 == 0){
                tikTokLightsBuffer.setRGB(i, 255, 255, 0);
                }
                else{
                tikTokLightsBuffer.setRGB(i, 0, 255, 0);
                }
             }
            lightsOne = true;
        }
         tikTokLights.setData(tikTokLightsBuffer);
      }

    public void run(boolean isEnabled, boolean isAutonomous){
     if(isEnabled && isAutonomous == false){
       // oneColorAlternating();
        oneColor();
     }
    else if(isEnabled && isAutonomous){
        oneColor();
    }
    else{
        cycle();
    }
}
   
}
