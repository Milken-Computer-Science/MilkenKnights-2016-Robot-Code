package com.milkenknights.frc2016;

public final class Constants {
    
    // DriveStraightController gains
    public static final double kDriveMaxSpeedInchesPerSec = 140;
    public static final double kDriveMaxAccelInchesPerSec2 = 200;
    public static final double kDrivePositionKp = 0.3; //.7
    public static final double kDrivePositionKi = 0.0;
    public static final double kDrivePositionKd = 0.0;
    public static final double kDriveStraightKp = 0.1; //3
    public static final double kDriveStraightKi = 0.0;
    public static final double kDriveStraightKd = 0.0;
    public static final double kDrivePositionKv = 0.008;
    public static final double kDrivePositionKa = 0.0017;
    public static final double kDriveOnTargetError = 0.75;
    public static final double kDrivePathHeadingFollowKp = 0.01;
    
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
    
    public class Analog {
        public static final int PRESSURE_TRANSDUCER = 0;
    }
    
    public class Pcm {
        public static final int ID = 1;
        
        public static final double HIGH_PRESSURE = 115.0;
        public static final double LOW_PRESSURE = 70.0;

        public static final int DRIVE_SHIFTER = 0;
        public static final int BALL_HOLDER = 1;
        public static final int FLASHLIGHT = 6;
        public static final int LED_RING = 7;
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
        public static final double CONTROLLERS_PERIOD = 1 / 200.0;
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
        
        public static final double HORIZONTAL_FOV  = 67.0; // TODO: Find this value
        
        public static final double OFFSET = -7.0;
    }
    
    public class Auto {
        public static final double ACTION_UPDATE_PERIOD = 1.0 / 50.0;
    }
    
    public class Subsystems {
        
        public class Drive {
            public static final double WHEEL_DIAMETER = 7.67;
            public static final double GEAR_RATIO = 20.0 / 64.0 / 3.0;
            
            public static final double MAXIMUM_SPEED = 140.0;
            
            public static final double VELOCITY_KP = 0.001;
            public static final double VELOCITY_KI = 0.0;
            public static final double VELOCITY_KD = 0.0;
            
            public static final double TURN_KP = 2.5;
            public static final double TURN_KI = 0.0;
            public static final double TURN_KD = 0.0;
            public static final double TURN_ALLOWABLE_ERROR = 3.0;
        }
        
        public class Intake {
            
            public class Arm {
                public static final double GEAR_RATIO = 16.0 / 44.0;
                
                public static final double INTAKE = -0.34;
                public static final double PROTECT = -0.2;
                public static final double STORED = 0.0;
                
                public static final double P = 10.0;
                public static final double I = 0.0;
                public static final double D = 0.0;
                
                public static final double MAXIMUM_OUTPUT = 0.75;
                public static final double DRIFT_POWER = 0.04;
                public static final double ZERO_POWER = 0.20;
                public static final double ZERO_CURRENT = 3.0;
                
                public static final double ZEROED_RATE = 0.05;
                public static final double ALLOWABLE_ERROR = 0.025;
                public static final double INTAKE_POSITION_DRIFT = 0.075;
            }
            
            public class Speed {
                public static final double INTAKE = 1.0;
                public static final double OUTPUT = -1.0;
            }
        }

        public class Catapult {
            public static final double GEAR_RATIO = 14.0 / 64.0;
            
            public static final double ALLOWABLE_ERROR = 0.025;
            public static final double RESET_SPEED = 0.3;
            public static final double MAX_VELOCITY = 0.8;
            public static final double READY_OFFSET = 0.15;
            
            public static final double VELOCITY_KP = 0.001;
            public static final double VELOCITY_KI = 0.0;
            public static final double VELOCITY_KD = 0.0;
            
            public static final double POSITION_KP = 3.0;
            public static final double POSITION_KI = 0.0;
            public static final double POSITION_KD = 0.0;
        }
        
    }
    
}
