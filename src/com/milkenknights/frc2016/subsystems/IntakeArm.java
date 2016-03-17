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
    public boolean armOnTarget() {
        return pid.onTarget(Constants.Subsystems.Intake.Arm.ALLOWABLE_ERROR);
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Intake Arm State", position.toString());
        SmartDashboard.putNumber("Intake Arm PID Result", pid.calculate(armEncoder.getDistance()));
        SmartDashboard.putNumber("Intake Arm Count", armEncoder.get());
        SmartDashboard.putNumber("Intake Arm Distance", armEncoder.getDistance());
        SmartDashboard.putNumber("Intake Arm Error", pid.getError());
    }

    @Override
    public void update() {
        if (position == IntakePosition.INTAKE && pid.getError() >= -Constants.Subsystems.Intake.Arm.ALLOWABLE_ERROR) {
            arm.set(0.0);
        } else {
            arm.set(pid.calculate(armEncoder.getDistance()));
        }
    }

}
