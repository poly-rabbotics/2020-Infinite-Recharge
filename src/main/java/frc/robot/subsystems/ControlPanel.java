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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.TurnPanel4Rotations;
import frc.robot.commands.TurnPanelToRequestedColor;
import frc.robot.controls.MechanismsJoystick;
import frc.robot.partdata.CompliantWheel4in;
import frc.robot.sensors.ColorSensor;
import frc.robot.fielddata.ControlPanelData;


/**
 * THIS IS AN EARLY VERSION, DO NOT MERGE WITH MASTER.
 */
public class ControlPanel extends AutoSubsystem {
  public static final double FAST_SPEED = 0.75; //Spin quickly, because precision does not matter for the "turn 4 times" task.
  public static final double SLOW_SPEED = 0.30; //TODO: EXPERIMENTALLY DETERMINE OPTIMAL SPEED.
  public static final double REQUIRED_ROTATIONS = 4;
  
  private PWMVictorSPX panelMotor;
  private Encoder encoder;
  private ColorSensor colorSensor;
  public static Color detectedColor;
  private String gameData;

  public ControlPanel() {    
    panelMotor = RobotMap.controlPanelMotor;
    encoder = RobotMap.controlPanelEncoder;
    colorSensor = Robot.colorSensor;
    //This is the number of rotations of the control panel.
    // x pulses times (1 rot of compliant wheel / 44.4 pulses) * (distance / rot of compliant wheel) * (rot of control panel / distance)
    encoder.setDistancePerPulse((1 / 44.4) * (CompliantWheel4in.CIRCUMFERENCE / 1) * (1 / ControlPanelData.CIRCUMFERENCE));
  }
  /**
   * @return the total number of rotations of the control panel that theoretically would have been caused
   * if the robot's control panel mechanism had been in contact with it for the duration of the match.
  */
  public double getPanelRotations(){
    return encoder.getDistance();
  }
  /**
   * Spins the mechanism slowly.
  */
  public void spinSlow() {
    panelMotor.set(SLOW_SPEED);
  }
  /**
   * Spins the mechanism quickly.
  */
  public void spinFast() {
    panelMotor.set(FAST_SPEED);
  }
  /**
   * Makes the mechanism stop moving.
  */
  public void stop() {
    panelMotor.set(0);
  }
  public void advancedRun() {
    gameData = DriverStation.getInstance().getGameSpecificMessage(); //Constantly try to get the game data.
    if(MechanismsJoystick.getManualControlPanel()) { //the user is trying to control the mechanism manually
      unlock(); //Request a stop to any command that may be using this subsystem by ending its lock on this subsystem
      spinFast();
    }
    else if (!getLocked()) { //This means no command has gotten a lock on this mechanism.
      stop();
    }
    if(MechanismsJoystick.getColorWheelRotation()) { //Request to start the "turn 4 times" command
      (new TurnPanel4Rotations(false)).start();
    }
    else if(MechanismsJoystick.getTurnToRequestedColor()) { //Request to start the "turn to requested color" command
      (new TurnPanelToRequestedColor(false)).start();
    }
  }
  public void manualRun() {
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(MechanismsJoystick.getManualControlPanel()) {
      unlock();
      spinFast();
    }
    else if (!getLocked()) {
      stop();
    }
  }
  public void autoRun() {
    if(!getLocked()) { //Stop if no Command has a lock on this Subsystem.
      stop();
    }
  }
  /**
   * @return the game data that this Subsystem has been able to get from the FMS.
  */
  public String getGameData() {
    return gameData;
  }
  /**
   * @return a boolean telling whether the current color is appropriate based on the game data from the FMS.
  */
  public boolean atCorrectColor() {
    //The order of the colors on the control panel is {'G', 'B', 'Y', 'R'}
    char requested = getGameData().charAt(0);
    char found = colorSensor.getColor();
    if( requested == 'G' && found == 'Y' || //requested must be different from found by 90 degrees because of the different location of the field's sensor
        requested == 'B' && found == 'R' ||
        requested == 'Y' && found == 'G' ||
        requested == 'R' && found == 'B') {
      return true;
    }
    return false;
  }
  
  public void reset() {
    //Do nothing
  }
}
