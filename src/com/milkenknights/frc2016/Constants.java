package com.milkenknights.frc2016;

public class Constants {
    
    public static final double kControlLoopsDt = 0.005;
    
    // DriveStraightController gains
    public static final double kDriveMaxSpeedInchesPerSec = 60;
    public static final double kDriveMaxAccelInchesPerSec2 = 200;
    public static final double kDrivePositionKp = 0.1; //.7
    public static final double kDrivePositionKi = 0.0;
    public static final double kDrivePositionKd = 0.0;
    public static final double kDriveStraightKp = 0.1; //3
    public static final double kDriveStraightKi = 0.0;
    public static final double kDriveStraightKd = 0.0;
    public static final double kDrivePositionKv = 0.008;
    public static final double kDrivePositionKa = 0.0017;
    public static final double kDriveOnTargetError = 0.75;
    public static final double kDrivePathHeadingFollowKp = 0.01;

    // TurnInPlaceController gains
    public static final double kTurnMaxSpeedRadsPerSec = 5.25;
    public static final double kTurnMaxAccelRadsPerSec2 = 3;
    public static final double kTurnKp = 0.2;
    public static final double kTurnKi = 0.0;
    public static final double kTurnKd = 0.0;
    public static final double kTurnKv = 0.085;
    public static final double kTurnKa = 0.075;
    public static final double kTurnOnTargetError = 0.05;
    
    public class Dio {
        public static final int CATAPULT_HOME = 0;
        public static final int DRIVE_LEFT_A = 10; // ENC 0
        public static final int DRIVE_LEFT_B = 11; // ENC 0
        public static final int DRIVE_RIGHT_A = 12; // ENC 1
        public static final int DRIVE_RIGHT_B = 13; // ENC 1
        public static final int CATAPULT_A = 14; // ENC 2
        public static final int CATAPULT_B = 15; // ENC 2
        public static final int INTAKE_A = 16; // ENC 3
        public static final int INTAKE_B = 17; // ENC 3
    }
    
    public class Pcm {
        public static final int ID = 1;

        public static final int DRIVE_SHIFTER = 0;
        public static final int BALL_HOLDER = 1;
    }
    
    public class CanTalon {
        public static final int DRIVE_LEFT_1 = 11; // 12
        public static final int DRIVE_LEFT_2 = 10; // 10
        public static final int DRIVE_RIGHT_1 = 9; // 9
        public static final int DRIVE_RIGHT_2 = 12; // 11
        public static final int CATAPULT = 8;
        public static final int INTAKE_ARM = 7;
        public static final int INTAKE_SPEED = 3;
    }
    
    public class ControlLoops {
        public static final double CONTROLLERS_PERIOD = 1 / 100.0;
        public static final double VISION_PERIOD = 1 / 25.0;
        public static final double SMARTDASHBOARD_UPDATER_PERIOD = 1 / 50.0;
    }
    
    public class DriverStation {
        public static final int DRIVE_JOYSTICK = 0;
        public static final int OPERATOR_JOYSTICK = 1;
        
        public static final double TWIST_MULTIPLIER = 1.5;
    }
    
    public class Vision {
        public static final String GRIP_TABLE_ID = "GRIP";
        public static final String TARGETS_TABLE_ID = "targets";
        public static final String MAT_SIZE_TABLE_ID = "matSize";
        
        public static final double HORIZONTAL_FOV  = 67; // TODO: Find this value
    }
    
    public class Auto {
        public static final double ACTION_UPDATE_PERIOD = 1.0 / 50.0;
    }
    
    public class Subsystems {
        
        public class Drive {
            public static final double WHEEL_DIAMETER = 7.67;
            
            public static final double GEAR_RATIO = 20.0 / 64.0 / 3.0;
        }
        
        public class Intake {
            
            public class Arm {
                public static final double GEAR_RATIO = 16.0 / 44.0;
                
                public static final double INTAKE = -0.35;
                public static final double PROTECT = -0.1717;
                public static final double STORED = 0.0;
                
                public static final double P = 7.5;
                public static final double I = 0.0;
                public static final double D = 0.0;
                
                public static final double MAXIMUM_OUTPUT = 0.50;
                
                public static final double ALLOWABLE_ERROR = 0.125; // TODO: Find this value
                
            }
            
            public class Speed {
                public static final double INTAKE = 1;
                public static final double OUTPUT = -1;
            }
        }

        public class Catapult {
            public static final double GEAR_RATIO = 14.0 / 64.0;
            
            public static final double ALLOWABLE_ERROR = 0.025;
            public static final double DEADBAND = 0.1;
            
            public static final double OFFSET = 0.155;
        }
        
    }
    
}
