/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.commands.DriveByTime;
import frc.robot.commands.DriveInRegularPolygonSequence;
import frc.robot.commands.TurnByDegrees;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  Subsystem subsystems[];
  AutoSubsystem autoSubsystems[];
  Drive drive;
  Shooter shooter;
  Climb climb;
  CameraServo cameraServo;
  ControlPanel controlPanel;
  ConveyorTest conveyor;
  IntakeTest intake;

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
    drive = new Drive(); 
    shooter = new Shooter();
    climb = new Climb();
    cameraServo = new CameraServo();
    controlPanel = new ControlPanel();
    conveyor = new ConveyorTest();
    intake = new IntakeTest();
    subsystems = new Subsystem[]{drive, shooter, climb, cameraServo};
    for(Subsystem subsystem: subsystems) {
      subsystem.reset();
    }
    autoSubsystems = new AutoSubsystem[]{drive};
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
  }

  @Override
  public void autonomousInit() {
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
  }

  @Override
  public void autonomousPeriodic() {
    for(AutoSubsystem auto: autoSubsystems) {
      auto.autoRun();
    }
  }

  @Override
  public void teleopPeriodic() {
    for(Subsystem subsystem: subsystems) {
      subsystem.run();
    }
    controlPanel.run();
    conveyor.run();
    intake.run();
  }

  @Override
  public void testPeriodic() {
  }
}
