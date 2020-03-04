/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.sensors.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.commands.*;
import frc.robot.controls.MechanismsJoystick;

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
  public static Drive drive = new Drive();
  public static Shooter shooter = new Shooter();
  public static Climb climb = new Climb();
  public static CameraServo cameraServo = new CameraServo();
  public static ConveyorBelt conveyorBelt = new ConveyorBelt();
  public static ControlPanel controlPanel = new ControlPanel();
  public static SmartDashboardOutputs outputs = new SmartDashboardOutputs();
  public static ConveyorTest conveyor = new ConveyorTest();
  public static IntakeTest intake = new IntakeTest();
  public static VisionLight light = new VisionLight();
  public static ColorSensor colorSensor = new ColorSensor();
  public static PressureTransducer pressureTransducer = new PressureTransducer();

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
    subsystems = new Subsystem[] {conveyorBelt, drive};
    for (Subsystem subsystem : subsystems) {
      subsystem.reset();
    }
    autoSubsystems = new AutoSubsystem[] {conveyorBelt, drive};
    for (Subsystem auto : autoSubsystems) {
      auto.reset();
    }
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    for (AutoSubsystem auto : autoSubsystems) {
      auto.reset();
    }
    // Put auto commands here
    // (new DriveByDistance(drive, 8, 10, "Drive forward 8 feet, quit after max 10
    // seconds", 20)).start();
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
    for (Subsystem subsystem : subsystems) {
      subsystem.reset();
    }
  }

  @Override
  public void autonomousPeriodic() {
    for (AutoSubsystem auto : autoSubsystems) {
      auto.autoRun();
    }
  }

  @Override
  public void teleopPeriodic() {
    for (Subsystem subsystem : subsystems) {
      if(MechanismsJoystick.isManual()) {
        subsystem.manualRun();
      }
      else {
        subsystem.run();
      }
    }
  }

  @Override
  public void testPeriodic() {
    // for (AutoSubsystem auto : autoSubsystems) {
    //   auto.autoRun();
    // }
    //drive.manualRun();
  }

  @Override
  public void testInit() {
    Command[] tests = { 
        //new IntakeBall(true), 
        //new PreloadShooter(true),
        //new Shoot(Shooter.INITIATION_LINE_PRESET, 20, true),

         new DriveByTime(2, 0.5, 20, true),
        // new DriveByDistance(5, 10, 20),
        // new TurnByDegrees(5, 1, 20, true), new TurnPanel4Rotations(true),

        // new TurnPanelToRequestedColor(true),
        // new TurnPanel4Rotations(true),

        // new CameraSetDriveSetpoint(true)
        };
    new Thread(() -> {
      for (Command command : tests) {
        System.out.print("STARTING_COMMAND_" + command.getName() + "********************");
        command.start();
        try {
          command.join();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
     }
    }).start();
  }
}
