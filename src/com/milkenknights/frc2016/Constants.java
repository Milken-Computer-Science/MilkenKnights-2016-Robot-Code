package com.milkenknights.frc2016;

public class Constants {
    
    public static double kControlLoopsDt = 0.005;
    
    // DriveStraightController gains
    public static double kDriveMaxSpeedInchesPerSec = 120;
    public static double kDriveMaxAccelInchesPerSec2 = 10;
    public static double kDrivePositionKp = 0.05; //.7
    public static double kDrivePositionKi = 0;
    public static double kDrivePositionKd = 0;
    public static double kDriveStraightKp = 0.0; //3
    public static double kDriveStraightKi = 0;
    public static double kDriveStraightKd = 0;
    public static double kDrivePositionKv = 0.008;
    public static double kDrivePositionKa = 0.0017;
    public static double kDriveOnTargetError = 0.75;
    public static double kDrivePathHeadingFollowKp = 0.01;

    // TurnInPlaceController gains
    public static double kTurnMaxSpeedRadsPerSec = 5.25;
    public static double kTurnMaxAccelRadsPerSec2 = 5.25;
    public static double kTurnKp = 3.0;
    public static double kTurnKi = 0.18;
    public static double kTurnKd = 0.23;
    public static double kTurnKv = 0.085;
    public static double kTurnKa = 0.075;
    public static double kTurnOnTargetError = 0.0225;
    
    public class Drive {
        public static final double WHEEL_DIAMETER = 4;
        public static final double MAX_SPEED = 0;
        
        public static final int LEFT_MOTOR_1 = 3;
        public static final int LEFT_MOTOR_2 = 4;
        public static final int RIGHT_MOTOR_1 = 4;
        public static final int RIGHT_MOTOR_2 = 4;

        public static final int LEFT_ENCODER_DIOA = 1;
        public static final int LEFT_ENCODER_DIOB = 0;
        public static final int RIGHT_ENCODER_DIOA = 2;
        public static final int RIGHT_ENCODER_DIOB = 3;
        
        public static final int SHIFTER_PCM_ID = 0;
    }
    
    public class Intake {
        public static final int TALON = 5;
    }

}
