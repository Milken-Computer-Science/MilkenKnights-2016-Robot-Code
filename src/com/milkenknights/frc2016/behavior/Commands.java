package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.subsystems.Drive.DriveGear;
import com.milkenknights.frc2016.subsystems.IntakeArm.IntakePosition;
import com.milkenknights.frc2016.subsystems.IntakeSpeed.IntakeSpeedState;

public class Commands {
    
    public double driveSpeed;
    public double driveRotate;
    public DriveGear driveGear;
    public boolean reverseDrive;
    public AlignRobot alignRobot;
    public boolean toggleBallClamp;
    public boolean fireCatapult;
    public IntakePosition intakePosition;
    public IntakeSpeedState intakeSpeed;
    public boolean zeroIntakeArm;
    public boolean zeroCatapult;
    
    public enum AlignRobot {
        START, STOP, CONTINUE
    }
    
}
