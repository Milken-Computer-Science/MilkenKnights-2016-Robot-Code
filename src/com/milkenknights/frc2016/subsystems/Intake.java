package com.milkenknights.frc2016.subsystems;

import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
    
    public static final double POSITION_RATIO = 60 / 18;
    
    public enum IntakePosition {
        ZERO(0), INTAKE(0.05), PROTECT(0.25), STORED(0.4);
        
        public final double position;
        private IntakePosition(double position) {
            this.position = position;
        }
    }
    
    public enum IntakeSpeed {
        STOP(0), INTAKE(-1), OUTPUT(1);
        
        public final double speed;
        private IntakeSpeed(double speed) {
            this.speed = speed;
        }
    }
    
    private CANTalon arm;
    private MkCanTalon intakeCord;
    private IntakePosition position;
    private IntakeSpeed speed;
    
    /**
     * Create a new intake subsystem.
     * 
     * @param name The name of the subsystem
     * @param arm The CANTalon used to move the arm
     * @param intakeCord The CanTalon used to control the intake
     */
    public Intake(String name, CANTalon arm, CANTalon follower, MkCanTalon intakeCord) {
        super(name);
        
        arm.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        arm.changeControlMode(CANTalon.TalonControlMode.Position);
        arm.reverseSensor(true);
        arm.setAllowableClosedLoopErr(0);
        arm.setIZone(300);
        arm.set(0);
        arm.setPosition(0);
        arm.setPID(0.1, 0.0, 0.0);
        arm.setF(0.0);
        
        follower.changeControlMode(TalonControlMode.Follower);
        follower.set(arm.getDeviceID());
        
        this.arm = arm;
        this.intakeCord = intakeCord;
        
        setPosition(IntakePosition.STORED);
        setSpeed(IntakeSpeed.STOP);
    }

    public void setSpeed(IntakeSpeed speed) {
        intakeCord.set(speed.speed);
        this.speed = speed;
    }
    
    public void setPosition(IntakePosition position) {
        arm.set(position.position * POSITION_RATIO);
        this.position = position;
    }
    
    public IntakePosition getPosition() {
        return position;
    }
    
    public IntakeSpeed getSpeed() {
        return speed;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Intake Arm State", position.toString());
        SmartDashboard.putString("Intake Speed State", speed.toString());
        
        SmartDashboard.putNumber("Intake Arm Count", arm.get());
        SmartDashboard.putNumber("Intake Arm Error", arm.getError());
    }

}
