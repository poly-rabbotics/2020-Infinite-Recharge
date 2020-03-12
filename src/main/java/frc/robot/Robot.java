/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveByTime;
import frc.robot.commands.DriveInRegularPolygonSequence;
import frc.robot.commands.TurnByDegrees;
import frc.robot.sensors.PressureTransducer;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static Subsystem subsystems[];
  public static AutoSubsystem autoSubsystems[];
  public static Drive drive;
  public static Shooter shooter;
  public static Climb climb;
  public static CameraServo cameraServo;
  public static ControlPanel controlPanel;
  public static ConveyorBelt conveyor;
  public static SmartDashboardOutputs outputs;
  public static IntakeTest intake;
  public static VisionLight light;
  public static PressureTransducer pressureTransducer;
  public static Timer timer;
  public static AutoModes autoModes;
  public static LEDLights led;

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
    drive = new Drive(); 
    shooter = new Shooter();
    climb = new Climb();
    conveyor = new ConveyorBelt();
    cameraServo = new CameraServo();
    controlPanel = new ControlPanel();
    outputs = new SmartDashboardOutputs();
    intake = new IntakeTest();
    light = new VisionLight();
    pressureTransducer = new PressureTransducer();
    timer = new Timer();
    autoModes = new AutoModes();
    led = new LEDLights();
    subsystems = new Subsystem[]{ shooter, climb, light};
    for(Subsystem subsystem: subsystems) {
      subsystem.reset();
    }
    autoSubsystems = new AutoSubsystem[]{};
    for(Subsystem auto: autoSubsystems) {
      auto.reset();
    }
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    autoModes.setMode();
    outputs.run();
    led.runLEDs();
    if(isDisabled()){
      LEDLights.pattern = 2;
    }
    else if(isAutonomous()){
      LEDLights.pattern = 1;
    }
    }

  @Override
  public void autonomousInit() {
    timer.start();
    for(AutoSubsystem auto: autoSubsystems) {
      auto.reset();
    }
    //Put auto commands here
  }
  @Override
  public void teleopInit() {
    super.teleopInit();
    for(Subsystem subsystem: subsystems) {
      subsystem.reset();
    }
   // drive.reset();
    timer.start();
 
  }

  @Override
  public void autonomousPeriodic() {
    for(AutoSubsystem auto: autoSubsystems) {
      auto.autoRun();
    }
    autoModes.run();
  }

  @Override
  public void teleopPeriodic() {
    for(Subsystem subsystem: subsystems) {
      subsystem.run();
    }
    drive.run();
    controlPanel.run();
    outputs.run();
    intake.run();
    conveyor.run();
    cameraServo.run();
    light.run();
  }

  @Override
  public void testPeriodic() {
  }
}
