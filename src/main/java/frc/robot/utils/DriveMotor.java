package frc.robot.utils;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.controller.PIDController;

import com.revrobotics.CANEncoder;

public class DriveMotor {
    public static final int REGULAR_DRIVE_SLOT = 0;
    public static final int DRIVE_BY_DISTANCE_DRIVE_SLOT = 1;
    public static final double DISTANCE_PER_ROTATION = 4 * Math.PI / 12 / 8.45;
    CANSparkMax motor;
    CANPIDController controller;
    CANEncoder encoder;
    public DriveMotor(CANSparkMax motor) {
        this.motor = motor;
        controller = motor.getPIDController();
        encoder = motor.getEncoder(EncoderType.kQuadrature, 4096);
    }
    public void setUpConstants() {
        motor.restoreFactoryDefaults();
        //constrain output
        controller.setOutputRange(-1, 1);
        //set zone in which I is active
        controller.setIZone(5, REGULAR_DRIVE_SLOT);
        // set PID coefficients for regular drive
        KGains gains = new KGains(0, 0, 0, 0);
        controller.setP(gains.kP, REGULAR_DRIVE_SLOT);
        controller.setI(gains.kI, REGULAR_DRIVE_SLOT);
        controller.setD(gains.kD, REGULAR_DRIVE_SLOT);
        controller.setFF(gains.kF, REGULAR_DRIVE_SLOT);
        //set PID coefficients for drive set distance
        gains = new KGains(0.3, 0.02, 1, 0); //TODO: calibrate PID
        controller.setP(gains.kP, DRIVE_BY_DISTANCE_DRIVE_SLOT);
        controller.setI(gains.kI, DRIVE_BY_DISTANCE_DRIVE_SLOT);
        controller.setD(gains.kD, DRIVE_BY_DISTANCE_DRIVE_SLOT);
        controller.setFF(gains.kF, DRIVE_BY_DISTANCE_DRIVE_SLOT);
    }
    public CANSparkMax getMotor() {
        return motor;
    }
    public void setPositionSetpoint(double value) {
        controller.setReference(value / DISTANCE_PER_ROTATION, ControlType.kPosition, DRIVE_BY_DISTANCE_DRIVE_SLOT);
    }
    public double getPosition() {
        return encoder.getPosition() * DISTANCE_PER_ROTATION;
    }
}