/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotMap;
import frc.robot.controls.MechanismsJoystick;
import frc.robot.partdata.CompliantWheel4in;
import frc.robot.fielddata.ControlPanelData;


/**
 * Add your docs here.
 */
public class ControlPanel {
  public static final double SPEED = 0.75;
  public static final double REQUIRED_ROTATIONS = 4;
  private PWMVictorSPX panelMotor;
  private double currentSpeed, startDistance, IR, detectedTop, detectedBottom, detectedMiddle, colorCalled;
  private Encoder encoder;
  private boolean spinningOn, rotationOn, colorOn, blueCall, redCall, greenCall, yellowCall, blueRun, redRun, greenRun, yellowRun;
  private ColorSensorV3 colorSensor;
  private Color detectedColor;
  String gameData;

  public ControlPanel() {

    panelMotor = RobotMap.controlPanelMotor;
    encoder = RobotMap.controlPanelEncoder;
    //This is the number of rotations of the control panel.
    // x pulses times (1 rot of compliant wheel / 44.4 pulses) * (distance / rot of compliant wheel) * (rot of control panel / distance)
    encoder.setDistancePerPulse((1 / 44.4) * (CompliantWheel4in.CIRCUMFERENCE / 1) * (1 / ControlPanelData.CIRCUMFERENCE));
    spinningOn = false;
    colorOn = false;
    rotationOn = false;
    colorSensor = RobotMap.controlPanelColorSensor;
    detectedColor = colorSensor.getColor();
    IR = colorSensor.getIR();
    detectedTop = 0.4; //Min strength of match for a color to be considered to have been detected
    detectedBottom = 0.2; //Max strength of match for a color to be considered to NOT have been detected
    detectedMiddle = 0.35;
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    currentSpeed = 0;
  }
  public void getColorCalled() {
    if(gameData.length() > 0 && colorCalled == -1) {
      switch (gameData.charAt(0))
      {
        case 'B' :
          colorCalled = 1;
          break;
        case 'G' :
          colorCalled = 2;
          break;
        case 'R' :
          colorCalled = 3;
          break;
        case 'Y' :
          colorCalled = 4;
          break;
        default :
          //This is corrupt data
          break;
      }
      if(colorCalled != -1) {
        colorCalled = (colorCalled + 2) % 4;
      }
    }
  }
  public void startThreeRotation() {
    SmartDashboard.putBoolean("Rotations are going", rotationOn);
    SmartDashboard.putNumber(" Number of Rotations ", encoder.getDistance());
    if (MechanismsJoystick.getToggleRotation()) {
      rotationOn = true;
      startDistance = encoder.getDistance();
    }
    if (encoder.getDistance() - startDistance < REQUIRED_ROTATIONS && rotationOn) {
      currentSpeed = SPEED;
    }
    else {
      rotationOn = false;
    }
  }

  public void testRequiredRotation() {
    if (MechanismsJoystick.getToggleRotation() && !spinningOn) {
      panelMotor.set(SPEED);
      spinningOn = true;
    } else if (MechanismsJoystick.getToggleRotation() && spinningOn) {
      panelMotor.set(0);
      spinningOn = false;
    }
    SmartDashboard.putNumber(" Number of Rotations ", encoder.getDistance());
  }

  public void colorSensor() {

    detectedColor = colorSensor.getColor();
    // displaying color numbers to smart dashboard
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);

    SmartDashboard.putBoolean("Called Blue", blueCall);
    SmartDashboard.putBoolean("Called Green", greenCall);
    SmartDashboard.putBoolean("Called Red", redCall);
    SmartDashboard.putBoolean("Called Yellow", yellowCall);

    SmartDashboard.putBoolean("Running Blue", blueRun);
    SmartDashboard.putBoolean("Running Green", greenRun);
    SmartDashboard.putBoolean("Running Red", redRun);
    SmartDashboard.putBoolean("Running Yellow", yellowRun);

    SmartDashboard.putBoolean("The Color Choice Method is On", colorOn);
    // blue
    if (detectedColor.blue >= detectedTop && detectedColor.green >= detectedTop
        && detectedColor.red <= detectedBottom) {
      blueRun = true;
    } else {
      blueRun = false;
    }
    // green
    if (detectedColor.blue <= detectedMiddle && detectedColor.green >= detectedTop
        && detectedColor.red <= detectedBottom) {
      greenRun = true;
    } else {
      greenRun = false;
    }
    // red
    if (detectedColor.blue <= detectedBottom && detectedColor.green <= detectedMiddle
        && detectedColor.red >= detectedTop) {
      redRun = true;
    } else {
      redRun = false;
    }

    // yellow
    if (detectedColor.blue <= detectedBottom && detectedColor.green >= detectedTop
        && detectedColor.red >= detectedBottom) {
      yellowRun = true;
    } else {
      yellowRun = false;
    }

  }

  public void colorChoice(){
    if(colorCalled == 1){
      if (blueRun == false) {
        currentSpeed = SPEED;
        
      }
      else {
        colorOn = false;

    }
  }
    else if(colorCalled == 2){
      if (greenRun == false) {
        currentSpeed = SPEED;
        
      }
      else {
        colorOn = false;
      }

     }
     else if(colorCalled == 3){
      if (redRun == false) {
        currentSpeed = SPEED;
      }
      else {
        colorOn = false;
      }

     }
     else if(colorCalled == 4){
      if(yellowRun == false) {
        currentSpeed = SPEED;
      }
      else if(yellowRun == true) {
        colorOn = false;
      }
   
     }
     else {
       currentSpeed = 0; //Game data not yet received
     }
  }

  public void startColorChoice(){
   if(MechanismsJoystick.getToggleColor() && !colorOn){
    colorOn = true;
  }
  else if(!MechanismsJoystick.getToggleColor() && colorOn){
    colorChoice();
  }
  else if (MechanismsJoystick.getToggleColor() && colorOn ){
    currentSpeed = 0;
    colorOn = false;
  }

}

  public void dataBlue() {

    blueCall = true;
    redCall = false;
    yellowCall = false;
    greenCall = false;
    colorCalled = 1;
  }
  public void dataGreen(){
 
    greenCall = true;
    blueCall = false;
    redCall = false;
    yellowCall = false;
    colorCalled = 2;

  }
  public void dataRed(){
  
    redCall = true;
    blueCall = false;
    yellowCall = false;
    greenCall = false;
    colorCalled = 3;
  
  }
  public void dataYellow(){

    yellowCall = true;
    blueCall = false;
    greenCall = false;
    redCall = false;
    colorCalled = 4;
    
  }



  public void manualMotor(){
    if(MechanismsJoystick.getToggleManualMotor()){
      currentSpeed = SPEED;
    }
  }
  public void manualMotorM(){
    if(MechanismsJoystick.getToggleManColorWheel()){
      currentSpeed = SPEED;
    }
    else{
      currentSpeed = 0;
    }
  }

  public void checkGameData(){
    if(gameData.length() > 0)
    {
      switch (gameData.charAt(0))
      {
        case 'B' :
        dataBlue();
          break;
        case 'G' :
        dataGreen();
          break;
        case 'R' :
        dataRed();
          break;
        case 'Y' :
        dataYellow();
          break;
        default :
          //This is corrupt data
          break;
      }
    }  
  }

  public void run() {
    if(!MechanismsJoystick.isManual()){
    checkGameData();
    getColorCalled();

    currentSpeed = 100;
    System.out.println("start sequence");
    startThreeRotation();
    System.out.println(currentSpeed);
    startColorChoice();
    System.out.println(currentSpeed);
    System.out.println(currentSpeed);
    colorSensor();
    manualMotor();
    if(currentSpeed == 100) {
      currentSpeed = 0;
    }
    panelMotor.set(currentSpeed);
  }
  else{
    manualMotorM();
    panelMotor.set(currentSpeed);
  }
  }
}
