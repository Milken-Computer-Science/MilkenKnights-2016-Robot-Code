package com.milkenknights.frc2016;

import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.hardware.S4T360;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

public class HardwareAdapter {

    private static final MkCanTalon DRIVE_LEFT_TALON = new MkCanTalon(
            new CANTalon[] {new CANTalon(Constants.Drive.LEFT_MOTOR_1), new CANTalon(Constants.Drive.LEFT_MOTOR_2)});
    private static final MkCanTalon DRIVE_RIGHT_TALON = new MkCanTalon(
            new CANTalon[] {new CANTalon(Constants.Drive.RIGHT_MOTOR_1), new CANTalon(Constants.Drive.RIGHT_MOTOR_2)});

    private static S4T360 DRIVE_LEFT_ENCODER = new S4T360(Constants.Drive.LEFT_ENCODER_DIOA, 
            Constants.Drive.LEFT_ENCODER_DIOB);
    private static S4T360 DRIVE_RIGHT_ENCODER = new S4T360(Constants.Drive.RIGHT_ENCODER_DIOA, 
            Constants.Drive.RIGHT_ENCODER_DIOB);

    private static Solenoid DRIVE_SHIFTER = new Solenoid(Constants.Drive.SHIFTER_PCM_ID);

    private static AHRS GYRO = new AHRS(SPI.Port.kMXP);
    //private static AnalogInput kPressureTrannsducer;

    public static Drive kDrive = new Drive("Drive", DRIVE_LEFT_TALON, DRIVE_RIGHT_TALON, DRIVE_LEFT_ENCODER,
            DRIVE_RIGHT_ENCODER, GYRO, DRIVE_SHIFTER);
    public static PowerDistributionPanel kPDP = new PowerDistributionPanel();
    //public static Compressor kCompressor = new Compressor(0);

    public static Joystick kLeftStick = new Joystick(0);
    public static Joystick kRightStick = new Joystick(1);
    public static Joystick kOperatorStick = new Joystick(2);
}
