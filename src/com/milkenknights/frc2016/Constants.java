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
    
    public class Dio {
        public static final int CATAPULT_HOME = 0;
        public static final int DRIVE_LEFT_A = 10;
        public static final int DRIVE_LEFT_B = 11;
        public static final int DRIVE_RIGHT_A = 12;
        public static final int DRIVE_RIGHT_B = 13;
        public static final int CATAPULT_A = 14;
        public static final int CATAPULT_B = 15;
    }
    
    public class Pcm {
        public static final int ID = 1;

        public static final int DRIVE_SHIFTER = 0;
    }
    
    public class CanTalon {
        public static final int DRIVE_LEFT_1 = 12;
        public static final int DRIVE_LEFT_2 = 10;
        public static final int DRIVE_RIGHT_1 = 9;
        public static final int DRIVE_RIGHT_2 = 11;
        public static final int CATAPULT = 8;
        public static final int INTAKE_ARM = 6; // TODO: Find this value
        public static final int INTAKE_SPEED = 7;
    }
    
    public class ControlLoops {
        public static final double CONTROLLERS_PERIOD = 1 / 100.0;
        public static final double VISION_PERIOD = 1 / 25.0;
        public static final double SMARTDASHBOARD_UPDATER_PERIOD = 1 / 50.0;
    }
    
    public class DriverStation {
        public static final int JOYSTICK = 0;
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
            public static final double WHEEL_DIAMETER = 8;
            
            public static final double GEAR_RATIO = 20.0 / 64.0 / 3.0;
            
            public static final double MAX_SPEED_LOW = 10; // TODO: Find this value
            public static final double MAX_SPEED_HIGH = 20; // TODO: Find this value
        }
        
        public class Intake {
            
            public class Arm {
                public static final double GEAR_RATIO = 60 / 18; // TODO: Find this value
                
                public static final double ZERO = 0; // TODO: Find this value
                public static final double INTAKE = 0.045; // TODO: Find this value
                public static final double PROTECT = 0.25; // TODO: Find this value
                public static final double STORED = 0.4; // TODO: Find this value
                
                public static final double P = 0.4; // TODO: Find this value
                public static final double I = 0.0001; // TODO: Find this value
                public static final double D = 0.0; // TODO: Find this value
                public static final double F = 0.0; // TODO: Find this value
                
                public static final int I_ZONE = 300; // TODO: Find this value
                public static final int ALLOWABLE_ERROR = 200; // TODO: Find this value
                
            }
            
            public class Speed {
                public static final double INTAKE = -1;
                public static final double OUTPUT = 1;
            }
        }

        public class Catapult {
            public static final double GEAR_RATIO = 64 / 14;
        }
        
    }
    
}
