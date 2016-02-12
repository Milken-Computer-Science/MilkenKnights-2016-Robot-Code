package com.milkenknights.frc2016;

public class Constants {
    
    public class Dio {
        public static final int CATAPULT_HOME = 0;
        public static final int DRIVE_LEFT_A = 1;
        public static final int DRIVE_LEFT_B = 2;
        public static final int DRIVE_RIGHT_A = 3;
        public static final int DRIVE_RIGHT_B = 4;
    }
    
    public class Pcm {
        public static final int ID = 1;
        
        public static final int DRIVE_SHIFTER = 0;
        public static final int LED_RING = 7;
    }
    
    public class CanTalon {
        public static final int DRIVE_LEFT_1 = 2;
        public static final int DRIVE_LEFT_2 = 3;
        public static final int DRIVE_RIGHT_1 = 4;
        public static final int DRIVE_RIGHT_2 = 5;
        public static final int CATAPULT = 6;
        public static final int INTAKE_ARM_1 = 7;
        public static final int INTAKE_SPEED = 8;
        public static final int INTAKE_ARM_2 = 9;
    }
    
    public class ControlLoops {
        public static final double CONTROLLERS_PERIOD = 1 / 100.0;
        public static final double VISION_PERIOD = 1 / 25.0;
        public static final double SMARTDASHBOARD_UPDATER_PERIOD = 1 / 50.0;
    }
    
    public class DriverStation {
        public static final int JOYSTICK_LEFT = 0;
        public static final int JOYSTICK_RIGHT = 1;
        public static final int JOYSTICK_OPERATOR = 2;
    }
    
    public class Auto {
        public static final double ACTION_UPDATE_PERIOD = 1.0 / 50.0;
    }
    
    public class Subsystems {
        
        public class Drive {
            public static final double WHEEL_DIAMETER = 4;
            public static final double MAX_SPEED = 20;
        }
        
        public class Intake {
            
            public class Arm {
                public static final double GEAR_RATIO = 60 / 18;
                
                public static final double ZERO = 0;
                public static final double INTAKE = 0.045;
                public static final double PROTECT = 0.25;
                public static final double STORED = 0.4;
                
                public static final double P = 0.4;
                public static final double I = 0.0001;
                public static final double D = 0.0;
                public static final double F = 0.0;
                
                public static final int I_ZONE = 300;
                public static final int ALLOWABLE_ERROR = 200;
                
            }
            
            public class Speed {
                public static final double INTAKE = -1;
                public static final double OUTPUT = 1;
            }
        }

        public class Catapult {
            public static final double GEAR_RATIO = 50 / 14;
        }
        
    }
    
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
    
}
