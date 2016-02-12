package com.milkenknights.frc2016;

import com.milkenknights.frc2016.subsystems.Catapult;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.frc2016.subsystems.Intake;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.hardware.S4T360;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

public class HardwareAdapter {

    public static Drive DRIVE;
    public static Intake INTAKE;
    public static Catapult CATAPULT;
    public static PowerDistributionPanel PDP;
    public static Compressor COMPRESSOR;

    public static Joystick LEFT_STICK;
    public static Joystick RIGHT_STICK;
    public static Joystick OPERATOR_STICK;
    
    /**
     * Initalize the robot hardware.
     */
    public static void init() {
        MkCanTalon driveLeftTalon = new MkCanTalon(new CANTalon[] {
            new CANTalon(Constants.CanTalon.DRIVE_LEFT_1), new CANTalon(Constants.CanTalon.DRIVE_LEFT_2)});
        MkCanTalon driveRightTalon = new MkCanTalon(new CANTalon[] {
            new CANTalon(Constants.CanTalon.DRIVE_RIGHT_1), new CANTalon(Constants.CanTalon.DRIVE_RIGHT_2)});
        
        CANTalon intakePositionTalon = new CANTalon(Constants.CanTalon.INTAKE_ARM_1);
        CANTalon intakeFollowerTalon = new CANTalon(Constants.CanTalon.INTAKE_ARM_2);
        MkCanTalon intakeSpeedTalon = new MkCanTalon(new CANTalon(Constants.CanTalon.INTAKE_SPEED));

        S4T360 driveLeftEncoder = new S4T360(Constants.Dio.DRIVE_LEFT_A, Constants.Dio.DRIVE_LEFT_B);
        S4T360 driveRightEncoder = new S4T360(Constants.Dio.DRIVE_RIGHT_A, Constants.Dio.DRIVE_RIGHT_B);

        Solenoid driveShifter = new Solenoid(Constants.Pcm.ID, Constants.Pcm.DRIVE_SHIFTER);
        
        CANTalon catapultTalon = new CANTalon(Constants.CanTalon.CATAPULT);
        DigitalInput catapultBanner = new DigitalInput(Constants.Dio.CATAPULT_HOME);

        AHRS gyro = new AHRS(SPI.Port.kOnboardCS1);
        Solenoid ledRing = new Solenoid(Constants.Pcm.LED_RING);
        //private static final AnalogInput kPressureTrannsducer;

        DRIVE = new Drive("Drive", driveLeftTalon, driveRightTalon, driveLeftEncoder, driveRightEncoder, 
                driveShifter, gyro);
        INTAKE = new Intake("Intake", intakePositionTalon, intakeFollowerTalon, intakeSpeedTalon);
        CATAPULT = new Catapult("Catapult", catapultTalon, catapultBanner);
        PDP = new PowerDistributionPanel();
        COMPRESSOR = new Compressor(Constants.Pcm.ID);
        
        GRIP = new GripHelper();

        LEFT_STICK = new Joystick(Constants.DriverStation.JOYSTICK_LEFT);
        RIGHT_STICK = new Joystick(Constants.DriverStation.JOYSTICK_RIGHT);
        OPERATOR_STICK = new Joystick(Constants.DriverStation.JOYSTICK_OPERATOR);
    }
    
}
