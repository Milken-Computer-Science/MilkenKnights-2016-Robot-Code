package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Subsystem;
import com.milkenknights.util.SynchronousPid;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeArm extends Subsystem implements Loopable {

    public enum IntakePosition {
        INTAKE(Constants.Subsystems.Intake.Arm.INTAKE),
        PROTECT(Constants.Subsystems.Intake.Arm.PROTECT),
        STORED(Constants.Subsystems.Intake.Arm.STORED);
        
        public final double position;
        private IntakePosition(final double position) {
            this.position = position;
        }
    }
    
    private MkCanTalon arm;
    private MkEncoder armEncoder;
    private IntakePosition position = IntakePosition.STORED;
    private SynchronousPid pid;
    private boolean zeroed;
    
    /**
     * Create a new intake subsystem.
     * 
     * @param name The name of the subsystem
     * @param armController The MkCanTalon used to move the arm
     */
    public IntakeArm(final String name, final MkCanTalon armController, final MkEncoder armEncoder) {
        super(name);
        
        armEncoder.setDistancePerPulse(Constants.Subsystems.Intake.Arm.GEAR_RATIO
                / armEncoder.getPulsesPerRevolution());
        
        pid = new SynchronousPid();
        pid.setPid(Constants.Subsystems.Intake.Arm.P, Constants.Subsystems.Intake.Arm.I,
                Constants.Subsystems.Intake.Arm.D);
        pid.setOutputRange(-Constants.Subsystems.Intake.Arm.MAXIMUM_OUTPUT,
                Constants.Subsystems.Intake.Arm.MAXIMUM_OUTPUT);
        
        this.arm = armController;
        this.armEncoder = armEncoder;
    }
    
    /**
     * Set the position of the intake.
     */
    public void setPosition(final IntakePosition position) {
        this.position = position;
        pid.setSetpoint(position.position);
        
    }
    
    /**
     * Get the position the arm of the intake is attempting to maintain.
     */
    public IntakePosition getPosition() {
        return position;
    }
    
    /**
     * Get if the arm is on target.
     */
    public boolean isOnTarget() {
        return pid.onTarget(Constants.Subsystems.Intake.Arm.ALLOWABLE_ERROR);
    }
    
    /**
     * Rezeros the intake arm.
     */
    public void zero() {
        zeroed = false;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Intake Arm State", position.toString());
        SmartDashboard.putNumber("Intake Arm PID Result", pid.calculate(armEncoder.getDistance()));
        SmartDashboard.putNumber("Intake Arm Count", armEncoder.get());
        SmartDashboard.putNumber("Intake Arm Speed", armEncoder.getRate());
        SmartDashboard.putNumber("Intake Arm Distance", armEncoder.getDistance());
        SmartDashboard.putNumber("Intake Arm Error", pid.getError());
        SmartDashboard.putNumber("Intake Arm Current", arm.getCurrent());
    }

    @Override
    public void update() {
        if (position == IntakePosition.INTAKE
                && pid.getError() >= -Constants.Subsystems.Intake.Arm.INTAKE_POSITION_DRIFT) {
            pid.setOutputRange(-Constants.Subsystems.Intake.Arm.DRIFT_POWER,
                    Constants.Subsystems.Intake.Arm.DRIFT_POWER);
        } else {
            pid.setOutputRange(-Constants.Subsystems.Intake.Arm.MAXIMUM_OUTPUT,
                    Constants.Subsystems.Intake.Arm.MAXIMUM_OUTPUT);
        }
        
        if (zeroed) {
            arm.set(pid.calculate(armEncoder.getDistance()));
        } else {
            if (Math.abs(armEncoder.getRate()) <= Constants.Subsystems.Intake.Arm.ZEROED_RATE
                    && Math.abs(arm.getCurrent()) >= Constants.Subsystems.Intake.Arm.ZERO_CURRENT) {
                armEncoder.reset();
                zeroed = true;
                System.out.println("Catapult Zeroed!");
            }
            arm.set(Constants.Subsystems.Intake.Arm.ZERO_POWER);
        }
        
    }

}
