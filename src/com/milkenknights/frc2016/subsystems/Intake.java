package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
    
    public enum IntakePosition {
        ZERO(Constants.Subsystems.Intake.Arm.ZERO),
        INTAKE(Constants.Subsystems.Intake.Arm.INTAKE),
        PROTECT(Constants.Subsystems.Intake.Arm.PROTECT),
        STORED(Constants.Subsystems.Intake.Arm.STORED);
        
        public final double position;
        private IntakePosition(double position) {
            this.position = position;
        }
    }
    
    public enum IntakeSpeed {
        NEUTRAL(0), INTAKE(Constants.Subsystems.Intake.Speed.INTAKE), OUTPUT(Constants.Subsystems.Intake.Speed.OUTPUT);
        
        public final double speed;
        private IntakeSpeed(double speed) {
            this.speed = speed;
        }
    }
    
    private MkCanTalon arm;
    private MkCanTalon speedController;
    private IntakePosition position;
    private IntakeSpeed speed;
    
    /**
     * Create a new intake subsystem.
     * 
     * @param name The name of the subsystem
     * @param armController The MkCanTalon used to move the arm
     * @param speedController The MkCanTalon used to control the intake
     */
    public Intake(String name, MkCanTalon armController, MkCanTalon speedController) {
        super(name);
        
        this.arm = armController;
        this.speedController = speedController;
        
        setPosition(IntakePosition.INTAKE);
        setSpeed(IntakeSpeed.NEUTRAL);
    }

    /**
     * Set the speed of the intake.
     */
    public void setSpeed(IntakeSpeed speed) {
        speedController.set(speed.speed);
        this.speed = speed;
    }
    
    /**
     * Set the position of the intake.
     */
    public void setPosition(IntakePosition position) {
        //arm.set(position.position * Constants.Subsystems.Intake.Arm.GEAR_RATIO);
        this.position = position;
    }
    
    /**
     * Get the position the arm of the intake is attempting to maintain.
     */
    public IntakePosition getPosition() {
        return position;
    }
    
    /**
     * Get the speed the intake is trying to maintain.
     */
    public IntakeSpeed getSpeed() {
        return speed;
    }
    
    /**
     * Get if the arm is on target.
     */
    public boolean armOnTarget() {
        return false; // TODO: Impl
        //return Math.abs(arm.getError()) < Constants.Subsystems.Intake.Arm.ALLOWABLE_ERROR;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Intake Arm State", position.toString());
        SmartDashboard.putString("Intake Speed State", speed.toString());
    }

}
