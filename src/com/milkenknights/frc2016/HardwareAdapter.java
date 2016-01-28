package com.milkenknights.frc2016;

import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.frc2016.subsystems.Intake;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.hardware.S4T360;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

public class HardwareAdapter {

    private static final MkCanTalon DRIVE_LEFT_TALON = new MkCanTalon(
            new CANTalon[] {new CANTalon(Constants.Drive.LEFT_MOTOR_1), new CANTalon(Constants.Drive.LEFT_MOTOR_2)});
    private static final MkCanTalon DRIVE_RIGHT_TALON = new MkCanTalon(
            new CANTalon[] {new CANTalon(Constants.Drive.RIGHT_MOTOR_1), new CANTalon(Constants.Drive.RIGHT_MOTOR_2)});
    private static final MkCanTalon INTAKE_TALON = new MkCanTalon(new CANTalon(Constants.Intake.TALON));

    private static final S4T360 DRIVE_LEFT_ENCODER = new S4T360(Constants.Drive.LEFT_ENCODER_DIOA, 
            Constants.Drive.LEFT_ENCODER_DIOB);
    private static final S4T360 DRIVE_RIGHT_ENCODER = new S4T360(Constants.Drive.RIGHT_ENCODER_DIOA, 
            Constants.Drive.RIGHT_ENCODER_DIOB);

    private static final Solenoid DRIVE_SHIFTER = new Solenoid(Constants.PCM_ID, Constants.Drive.SHIFTER_PCM_ID);

    private static final AHRS GYRO = new AHRS(SPI.Port.kMXP);
    //private static final AnalogInput kPressureTrannsducer;

    public static final Drive DRIVE = new Drive("Drive", DRIVE_LEFT_TALON, DRIVE_RIGHT_TALON, DRIVE_LEFT_ENCODER,
            DRIVE_RIGHT_ENCODER, DRIVE_SHIFTER, GYRO);
    public static final Intake INTAKE = new Intake("Intake", INTAKE_TALON);
    public static final PowerDistributionPanel PDP = new PowerDistributionPanel();
    public static final Compressor COMPRESSOR = new Compressor(Constants.PCM_ID);

    public static final Joystick LEFT_STICK = new Joystick(0);
    public static final Joystick RIGHT_STICK = new Joystick(1);
    public static final Joystick OPERATOR_STICK = new Joystick(2);
}
