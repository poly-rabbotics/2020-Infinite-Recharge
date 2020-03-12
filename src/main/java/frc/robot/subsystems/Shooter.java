package frc.robot.subsystems;

import frc.robot.controls.MechanismsJoystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter extends Subsystem {
    private TalonSRX topMotor, bottomMotor;
    static double distance, preset;
    private double topMotorSpeed, bottomMotorSpeed, lowSpeed, highSpeed, bothSpeed;
    private DoubleSolenoid solenoid;
    private boolean solenoidOut;
    double calHighVelocity = 0, calLowVelocity = 0;
    double kP = 0.1, kD = 2, kF=1023.0/20660.0;
  
    public Shooter() {
      topMotorSpeed = 0;
      bottomMotorSpeed = 0;
      topMotor = RobotMap.shooterTopMotor;
      bottomMotor = RobotMap.shooterBottomMotor;
      distance = 0.0;
      preset = 1;
      lowSpeed = 0.225;
      highSpeed = 0.525;
      bothSpeed = 0.7;
      solenoid = RobotMap.shooterSolenoid;
      solenoidOut = false;
      configureShooterPids();

    }
    private void adjustSpeeds() {
        if(MechanismsJoystick.getChangeTopShooter() > 0.1 && topMotorSpeed <= 1) {
          topMotorSpeed += .005;
        }
        else if(MechanismsJoystick.getChangeTopShooter() < -0.1 && topMotorSpeed >= 0) {
          topMotorSpeed -= .005;
        }
        if(MechanismsJoystick.getChangeBottomShooter() > .1 && bottomMotorSpeed <= 1) {
          bottomMotorSpeed -= .005;
        }
        else if(MechanismsJoystick.getChangeBottomShooter() <- .1 && bottomMotorSpeed >= 0) {
          bottomMotorSpeed -= .005;
        }
    }

    public void presetOne(){
      topMotor.set(ControlMode.PercentOutput, -bothSpeed);
      topMotorSpeed = -3500;
      bottomMotorSpeed = 3500;
      double topMotorV = topMotorSpeed * 2048 / 600;
    double bottomMotorV = bottomMotorSpeed * 2048 / 600;

    topMotor.set(ControlMode.Velocity,topMotorV);
    bottomMotor.set(ControlMode.Velocity,bottomMotorV);
    
    }

    public void presetTwo(){
     
      topMotorSpeed = -1930;
      bottomMotorSpeed = 1930;
      double topMotorV = topMotorSpeed * 2048 / 600;
    double bottomMotorV = bottomMotorSpeed * 2048 / 600;

    topMotor.set(ControlMode.Velocity,topMotorV);
    bottomMotor.set(ControlMode.Velocity,bottomMotorV);

    }

    public void presetThree(){
      topMotorSpeed = -3000;
      bottomMotorSpeed = 3000;
      double topMotorV = topMotorSpeed * 2048 / 600;
    double bottomMotorV = bottomMotorSpeed * 2048 / 600;

    topMotor.set(ControlMode.Velocity,topMotorV);
    bottomMotor.set(ControlMode.Velocity,bottomMotorV);
    solenoid.set(Value.kReverse);

    }

    public void run() {

      /*if(MechanismsJoystick.allowCalibrateShooter()){
        calibrateShooter();
      }
      else{
        */
      if(MechanismsJoystick.isManual()) {
        //adjustSpeeds();
        //SmartDashboard.putNumber("Top Shooter:", topMotorSpeed);
        //SmartDashboard.putNumber("Bottom Shooter:", bottomMotorSpeed);
        //topMotor.set(ControlMode.PercentOutput, topMotorSpeed);
        //bottomMotor.set(ControlMode.PercentOutput, bottomMotorSpeed);
        if(MechanismsJoystick.getToggleManShootThree()){
          presetThree();
        }
        else if(MechanismsJoystick.getToggleManShootTwo()){
          presetTwo();
        }
        else if(MechanismsJoystick.getToggleManShootOne()){
          presetOne();
        }
        else{
          topMotor.set(ControlMode.PercentOutput, 0);
          bottomMotor.set(ControlMode.PercentOutput, 0);
          solenoid.set(Value.kForward);
        }
        /*
        if(MechanismsJoystick.getToggleManShooterSolenoid()) {
          solenoid.set(Value.kReverse);
        }
        else if(!MechanismsJoystick.getToggleManShooterSolenoid()) {
          solenoid.set(Value.kForward);
        }
        */
        
      }

      else {
        if(MechanismsJoystick.getToggleManShootOne()) {
          presetOne();
          solenoid.set(Value.kForward);
        }
        else if(MechanismsJoystick.getToggleManShootTwo()) {
          presetTwo();
          solenoid.set(Value.kForward);

        }
        else if(MechanismsJoystick.getToggleManShootThree()) {
          presetThree();
          
        }
        else {
          topMotor.set(ControlMode.PercentOutput, 0);
          bottomMotor.set(ControlMode.PercentOutput, 0);
          solenoid.set(Value.kForward);
   
        }
       
      }
    
    SmartDashboard.putNumber("Top Setpoint:",topMotorSpeed);
    SmartDashboard.putNumber("Bottom Setpoint",bottomMotorSpeed);
    SmartDashboard.putNumber("Top Power", topMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Bottom Power",bottomMotor.getMotorOutputPercent());

    SmartDashboard.putNumber("Top RPM",topMotor.getSelectedSensorVelocity()*600.0/2048.0);
    SmartDashboard.putNumber("Bottom RPM",bottomMotor.getSelectedSensorVelocity()*600.0/2048.0);

    SmartDashboard.putNumber("Top Error",topMotorSpeed-topMotor.getSelectedSensorVelocity()*600.0/2048.0);
    SmartDashboard.putNumber("Bottom Error",bottomMotorSpeed-bottomMotor.getSelectedSensorVelocity()*600.0/2048.0);
  }

    public void autoRun(double startTime, double endTime, double preset){
      SmartDashboard.putNumber("Timer", Robot.timer.get());
      if(Robot.timer.get() > startTime && Robot.timer.get() < endTime){
        if(preset == 1){
        presetOne();
        }
        else if(preset == 2){
          presetTwo();
        }
        else if(preset == 3){
          presetThree();
        }
        
      }
      else{
        topMotor.set(ControlMode.PercentOutput, 0);
        bottomMotor.set(ControlMode.PercentOutput, 0);
        solenoid.set(Value.kForward);
      }
  }
  void configureShooterPids()
  {
    topMotor.configFactoryDefault();
    bottomMotor.configFactoryDefault();

    topMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,0,100);
    bottomMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,0,100);

    topMotor.configNeutralDeadband(0.001);
    bottomMotor.configNeutralDeadband(0.001);

    topMotor.config_kP(0,kP);
    bottomMotor.config_kP(0,kP);

    topMotor.config_kD(0,kD);
    bottomMotor.config_kD(0,kD);

    topMotor.config_kF(0,kF);
    bottomMotor.config_kF(0,kF);
   
  }

  // CALIBRATING SHOOTER

  public void calibrateShooter()
  {

    if (MechanismsJoystick.getToggleManShootOne())
    {
      bottomMotorSpeed += 10;
    }
    if (MechanismsJoystick.getToggleManShootTwo())
    {
      topMotorSpeed -= 10;
    }

    if (MechanismsJoystick.getToggleManShootThree())
    {
      topMotorSpeed += 10;
      bottomMotorSpeed -= 10;
    }

    // 2048 Units/Rev * x RPM / 600 100ms/min

    double topMotorV = topMotorSpeed * 2048 / 600;
    double bottomMotorV = bottomMotorSpeed * 2048 / 600;

    topMotor.set(ControlMode.Velocity,topMotorV);
    bottomMotor.set(ControlMode.Velocity,bottomMotorV);

    


  }

    public void reset() {}
}