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
import edu.wpi.first.wpilibj.SerialPort;
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
            new CANTalon(Constants.Drive.LEFT_MOTOR_1), new CANTalon(Constants.Drive.LEFT_MOTOR_2)});
        MkCanTalon driveRightTalon = new MkCanTalon(new CANTalon[] {
            new CANTalon(Constants.Drive.RIGHT_MOTOR_1), new CANTalon(Constants.Drive.RIGHT_MOTOR_2)});
        
        CANTalon intakePositionTalon = new CANTalon(Constants.Intake.POSITION_TALON);
        CANTalon intakeFollowerTalon = new CANTalon(Constants.Intake.FOLLOWER_TALON);
        MkCanTalon intakeSpeedTalon = new MkCanTalon(new CANTalon(Constants.Intake.SPEED_TALON));

        S4T360 driveLeftEncoder = new S4T360(Constants.Drive.LEFT_ENCODER_DIOA, Constants.Drive.LEFT_ENCODER_DIOB);
        S4T360 driveRightEncoder = new S4T360(Constants.Drive.RIGHT_ENCODER_DIOA, Constants.Drive.RIGHT_ENCODER_DIOB);

        Solenoid driveShifter = new Solenoid(Constants.PCM_ID, Constants.Drive.SHIFTER_PCM_ID);
        
        CANTalon catapultTalon = new CANTalon(Constants.Catapult.MOTOR);
        DigitalInput catapultBanner = new DigitalInput(Constants.Catapult.BANNER_DIO);

        AHRS gyro = new AHRS(SerialPort.Port.kUSB);
        //private static final AnalogInput kPressureTrannsducer;

        DRIVE = new Drive("Drive", driveLeftTalon, driveRightTalon, driveLeftEncoder, driveRightEncoder, 
                driveShifter, gyro);
        INTAKE = new Intake("Intake", intakePositionTalon, intakeFollowerTalon, intakeSpeedTalon);
        CATAPULT = new Catapult("Catapult", catapultTalon, catapultBanner);
        PDP = new PowerDistributionPanel();
        COMPRESSOR = new Compressor(Constants.PCM_ID);

        LEFT_STICK = new Joystick(0);
        RIGHT_STICK = new Joystick(1);
        OPERATOR_STICK = new Joystick(2);
    }
    
}
