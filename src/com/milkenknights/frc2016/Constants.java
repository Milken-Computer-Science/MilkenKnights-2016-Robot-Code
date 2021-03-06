package com.milkenknights.frc2016;

public final class Constants {
    
    public class Dio {
        public static final int CATAPULT_HOME = 0;
        public static final int DRIVE_LEFT_A = 10;  // ENC 0
        public static final int DRIVE_LEFT_B = 11;  // ENC 0
        public static final int DRIVE_RIGHT_A = 12; // ENC 1
        public static final int DRIVE_RIGHT_B = 13; // ENC 1
        public static final int CATAPULT_A = 14;    // ENC 2
        public static final int CATAPULT_B = 15;    // ENC 2
        public static final int INTAKE_A = 16;      // ENC 3
        public static final int INTAKE_B = 17;      // ENC 3
        public static final int ACTION_ARM_A = 18;  // ENC 4
        public static final int ACTION_ARM_B = 19;  // ENC 4
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
    }
    
    public class CanTalon {
        public static final int DRIVE_LEFT_1 = 9;
        public static final int DRIVE_LEFT_2 = 12;
        public static final int DRIVE_RIGHT_1 = 10;
        public static final int DRIVE_RIGHT_2 = 11;
        public static final int CATAPULT = 8;
        public static final int INTAKE_ARM = 7;
        public static final int INTAKE_SPEED = 3;
        public static final int ACTION_ARM = 2;
    }
    
    public class ControlLoops {
        public static final double CONTROLLERS_PERIOD = 1 / 200.0;
        public static final double SLOW_PERIOD = 1 / 25.0;
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
        
        public static final double HORIZONTAL_FOV  = 54.0;
        
        public static final double OFFSET = -30.0;
    }
    
    public class Auto {
        public static final double ACTION_UPDATE_PERIOD = 1.0 / 50.0;
    }
    
    public class Subsystems {
        
        public class Drive {
            public static final double WHEEL_DIAMETER = 7.67;
            public static final double GEAR_RATIO = 20.0 / 64.0 / 3.0;
            
            public static final double ENCODER_BROKEN_DISTANCE = 6.0;
            
            public static final double MAXIMUM_SPEED = 140.0;
            public static final double MAXIMUM_ACCEL = 200.0;
            
            public static final double VELOCITY_OUTPUT_RANGE = 0.2;
            
            public static final double VELOCITY_KP = 0.00125;
            public static final double VELOCITY_KI = 0.0;
            public static final double VELOCITY_KD = 0.0;
            
            public static final double TURN_KP = 4.0;
            public static final double TURN_KI = 0.0;
            public static final double TURN_KD = 0.1;
            public static final double TURN_ALLOWABLE_ERROR = 2.0;
            
            public static final double POSITION_KP = 0.3;
            public static final double POSITION_KI = 0.0;
            public static final double POSITION_KD = 0.0;
            public static final double POSITION_KV = 0.008;
            public static final double POSITION_KA = 0.0017;
            public static final double POSITION_ALLOWABLE_ERROR = 3.0;
            
            public static final double STRAIGHT_KP = 0.1;
            public static final double STRAIGHT_KI = 0.0;
            public static final double STRAIGHT_KD = 0.0;
            
        }
        
        public class Intake {
            
            public class Arm {
                public static final double GEAR_RATIO = 16.0 / 44.0;
                
                public static final double INTAKE = -0.375;
                public static final double PROTECT = -0.25;
                public static final double STORED = 0.0;
                
                public static final double P = 10.0;
                public static final double I = 0.0;
                public static final double D = 0.0;
                
                public static final double MAXIMUM_OUTPUT = 0.75;
                public static final double DRIFT_POWER = 0.10;
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
            public static final double GEAR_RATIO = -14.0 / 64.0;
            
            public static final double ALLOWABLE_ERROR = 0.025;
            public static final double RETRACT_SPEED = 0.4;
            public static final double SLOW_RETRACT_SPEED = 0.1;
            public static final double MAX_VELOCITY = 1.0;
            public static final double READY_OFFSET = 0.1;
            public static final double FIRE_OFFSET = 0.075;
            public static final double RETRACT_DELAY = 0.5;
            
            public static final double VELOCITY_KP = 0.005;
            public static final double VELOCITY_KI = 0.0;
            public static final double VELOCITY_KD = 0.0;
            
            public static final double POSITION_KP = 12.0;
            public static final double POSITION_KI = 0.0;
            public static final double POSITION_KD = 0.0;
        }
        
        public class ActionArm {
            public static final double GEAR_RATIO = 16.0 / 36.0;
            
            public static final double PORTICULLIS = -0.41;
            public static final double CDF = -0.355;
            public static final double STORED = 0.0;
            
            public static final double MAXIMUM_OUTPUT = 1.0;
            
            public static final double ZERO_POWER = 0.3;
            public static final double ZERO_CURRENT = 3.0;
            public static final double ZEROED_RATE = 0.05;
            public static final double ALLOWABLE_ERROR = 0.025;
            
            public static final double P = 50.0;
            public static final double I = 0.0;
            public static final double D = 0.0;
        }
        
    }
    
}
