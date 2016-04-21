package com.milkenknights.frc2016;

import com.milkenknights.frc2016.subsystems.ActionArm;
import com.milkenknights.frc2016.subsystems.BallClamp;
import com.milkenknights.frc2016.subsystems.Catapult;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.frc2016.subsystems.IntakeArm;
import com.milkenknights.frc2016.subsystems.IntakeSpeed;
import com.milkenknights.util.GripHelper;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkCompressor;
import com.milkenknights.util.MkGyro;
import com.milkenknights.util.MkJoystick;
import com.milkenknights.util.MkPressureTransducer;
import com.milkenknights.util.hardware.S4T360;
import com.milkenknights.util.hardware.SsiTechnologiesPressureTransducer;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

public final class HardwareAdapter {

    public static final Drive DRIVE;
    public static final IntakeArm INTAKE_ARM;
    public static final IntakeSpeed INTAKE_SPEED;
    public static final Catapult CATAPULT;
    public static final ActionArm ACTION_ARM;
    public static final BallClamp BALL_CLAMP;
    
    public static final PowerDistributionPanel PDP;
    public static final MkCompressor COMPRESSOR;
    
    public static final GripHelper GRIP;

    public static final MkJoystick DRIVE_STICK;
    public static final MkJoystick OPERATOR_STICK;
    
    /**
     * Initialize the robot hardware.
     */
    static {
        System.out.println("Start HardwareAdapter Init");
        
        final MkCanTalon driveLeftTalon = new MkCanTalon(new CANTalon[] {
            new CANTalon(Constants.CanTalon.DRIVE_LEFT_1), new CANTalon(Constants.CanTalon.DRIVE_LEFT_2)});
        final MkCanTalon driveRightTalon = new MkCanTalon(new CANTalon[] {
            new CANTalon(Constants.CanTalon.DRIVE_RIGHT_1), new CANTalon(Constants.CanTalon.DRIVE_RIGHT_2)});
        
        final MkCanTalon intakePositionTalon = new MkCanTalon(new CANTalon(Constants.CanTalon.INTAKE_ARM));
        final MkCanTalon intakeSpeedTalon = new MkCanTalon(new CANTalon(Constants.CanTalon.INTAKE_SPEED));
        final MkCanTalon catapultTalon = new MkCanTalon(new CANTalon(Constants.CanTalon.CATAPULT));
        final MkCanTalon actionArmTalon = new MkCanTalon(new CANTalon(Constants.CanTalon.ACTION_ARM));

        System.out.println("CanTalon init complete");
        
        final S4T360 driveLeftEncoder = new S4T360(Constants.Dio.DRIVE_LEFT_A, Constants.Dio.DRIVE_LEFT_B);
        final S4T360 driveRightEncoder = new S4T360(Constants.Dio.DRIVE_RIGHT_A, Constants.Dio.DRIVE_RIGHT_B);
        final S4T360 catapultEncoder = new S4T360(Constants.Dio.CATAPULT_A, Constants.Dio.CATAPULT_B);
        final S4T360 intakeEncoder = new S4T360(Constants.Dio.INTAKE_A, Constants.Dio.INTAKE_B);
        final S4T360 actionArmEncoder = new S4T360(Constants.Dio.ACTION_ARM_A, Constants.Dio.ACTION_ARM_B);

        System.out.println("Encoder init complete");
        
        final Solenoid driveShifter = new Solenoid(Constants.Pcm.ID, Constants.Pcm.DRIVE_SHIFTER);
        final Solenoid ballClamp = new Solenoid(Constants.Pcm.ID, Constants.Pcm.BALL_HOLDER);

        System.out.println("Solenoid init complete");
        
        final DigitalInput catapultHome = new DigitalInput(Constants.Dio.CATAPULT_HOME);

        System.out.println("DIO init complete");
        
        final MkGyro gyro = new MkGyro(new AHRS(SPI.Port.kOnboardCS1));
        final  MkPressureTransducer pressureTransducer
            = new SsiTechnologiesPressureTransducer(Constants.Analog.PRESSURE_TRANSDUCER);

        System.out.println("Analog IO init complete");
        
        DRIVE = new Drive("Drive", driveLeftTalon, driveRightTalon, driveLeftEncoder, driveRightEncoder, 
                driveShifter, gyro);
        INTAKE_ARM = new IntakeArm("Intake Arm", intakePositionTalon, intakeEncoder);
        INTAKE_SPEED = new IntakeSpeed("Intake Speed", intakeSpeedTalon);
        CATAPULT = new Catapult("Catapult", catapultTalon, catapultEncoder, catapultHome);
        ACTION_ARM = new ActionArm("Action Arm", actionArmTalon, actionArmEncoder);
        BALL_CLAMP = new BallClamp("Ball Clamp", ballClamp);
        
        System.out.println("Subsystem init complete");
        
        PDP = new PowerDistributionPanel();
        COMPRESSOR = new MkCompressor(Constants.Pcm.ID, pressureTransducer);
        
        GRIP = new GripHelper();

        DRIVE_STICK = new MkJoystick(Constants.DriverStation.DRIVE_JOYSTICK);
        OPERATOR_STICK = new MkJoystick(Constants.DriverStation.OPERATOR_JOYSTICK);
        
        System.out.println("HardwareAdapter init complete");
    }
    
    private HardwareAdapter() {
        // Utility Class
    }
    
}
