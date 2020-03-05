package frc.robot.utils;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.controller.PIDController;

import com.revrobotics.CANEncoder;

public class DriveMotor {
    public static final int REGULAR_DRIVE_SLOT = 0;
    public static final int STOP_DRIVE_SLOT = 1;
    public static final int SMART_TRANSLATION_DRIVE_SLOT = 2;

    public static final int SMART_TRANSLATION_MAX_VELOCITY = 1000;
    public static final int SMART_TRANSLATION_MIN_VELOCITY = 6;
    public static final int SMART_TRANSLATION_MIN_ACCELERATION = 500;
    public static final int SMART_TRANSLATION_ALLOWED_ERROR = 0;
    public static final double DISTANCE_PER_ROTATION = 6 * Math.PI / 12 / 8.45; //6in wheels, 12in per foot, 8.45
    CANSparkMax motor;
    CANPIDController controller;
    CANEncoder encoder;
    public DriveMotor(CANSparkMax motor) {
        this.motor = motor;
        controller = motor.getPIDController();
        encoder = motor.getEncoder();
    }
    public void setUpConstants() {
        motor.restoreFactoryDefaults();
        //constrain output
        //controller.setOutputRange(-1, 1);
        //set zone in which I is active
        //controller.setIZone(5, REGULAR_DRIVE_SLOT);
        // set PID coefficients for regular drive
        KGains gains = new KGains(1.0, 0, 0, 0);
        controller.setIZone(0);
        controller.setFF(0);
        inputKGains(controller, gains, REGULAR_DRIVE_SLOT);
        //set PID coefficients for drive set distance
        gains = new KGains(6e-5, 0, 0, 0.000015); //TODO: calibrate PID
        inputKGains(controller, gains, STOP_DRIVE_SLOT);
        //set PID coefficients for smart translation
        gains = new KGains(5e-5, 1e-6, 0, 0);
        inputKGains(controller, gains, SMART_TRANSLATION_DRIVE_SLOT);
        //set smart motion specific values
        controller.setSmartMotionMaxVelocity(SMART_TRANSLATION_MAX_VELOCITY, SMART_TRANSLATION_DRIVE_SLOT);
        controller.setSmartMotionMinOutputVelocity(SMART_TRANSLATION_MIN_VELOCITY, SMART_TRANSLATION_DRIVE_SLOT);
        controller.setSmartMotionMaxAccel(SMART_TRANSLATION_MIN_ACCELERATION, SMART_TRANSLATION_DRIVE_SLOT);
        controller.setSmartMotionAllowedClosedLoopError(SMART_TRANSLATION_ALLOWED_ERROR, SMART_TRANSLATION_DRIVE_SLOT);
    }
    private static void inputKGains(CANPIDController controller, KGains gains, int slot) {
        controller.setP(gains.kP, slot);
        controller.setI(gains.kI, slot);
        controller.setD(gains.kD, slot);
        controller.setFF(gains.kF, slot);
    }
    public CANSparkMax getMotor() {
        return motor;
    }
    /**
     * WARNING this method as defined currently is intended to be used for stopping.
     * It is suggested that you only pass 0 or small numbers to the value, and 
     * use setSmartTranslationSetpoint for large translations.
     */
    public void setPositionSetpoint(double value) {
        controller.setReference(encoder.getPosition() + value / DISTANCE_PER_ROTATION, ControlType.kPosition, STOP_DRIVE_SLOT);
    }
    public void setSmartTranslationSetpoint(double value) {
        System.out.print("Setting smart translation setpoint: ");
        System.out.println(value);
        controller.setReference(value / DISTANCE_PER_ROTATION, ControlType.kSmartMotion, SMART_TRANSLATION_DRIVE_SLOT);
    }
    public double getPosition() {
        return encoder.getPosition() * DISTANCE_PER_ROTATION;
    }
    public void setVoltage(double voltage) {
        controller.setReference(voltage, ControlType.kVoltage, REGULAR_DRIVE_SLOT);
    }
}