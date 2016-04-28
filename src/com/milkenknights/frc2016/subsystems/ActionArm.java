package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Subsystem;
import com.milkenknights.util.SynchronousPid;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class ActionArm extends Subsystem implements Loopable {

    public enum ActionArmPosition {
        PORTICULLIS(Constants.Subsystems.ActionArm.PORTICULLIS),
        CDF(Constants.Subsystems.ActionArm.CDF),
        STORED(Constants.Subsystems.ActionArm.STORED);
        
        public final double position;
        private ActionArmPosition(final double position) {
            this.position = position;
        }
    }
    
    private MkCanTalon arm;
    private MkEncoder armEncoder;
    private ActionArmPosition position = ActionArmPosition.STORED;
    private SynchronousPid pid;
    private boolean zeroed;
    
    /**
     * Create a new ActionArm subsystem.
     * 
     * @param name The name of the subsystem
     * @param armController The MkCanTalon used to move the arm
     */
    public ActionArm(final String name, final MkCanTalon armController, final MkEncoder armEncoder) {
        super(name);
        
        armEncoder.setDistancePerPulse(Constants.Subsystems.ActionArm.GEAR_RATIO
                / armEncoder.getPulsesPerRevolution());
        
        pid = new SynchronousPid();
        pid.setPid(Constants.Subsystems.ActionArm.P, Constants.Subsystems.ActionArm.I,
                Constants.Subsystems.ActionArm.D);
        pid.setOutputRange(-Constants.Subsystems.ActionArm.MAXIMUM_OUTPUT,
                Constants.Subsystems.ActionArm.MAXIMUM_OUTPUT);
        
        armController.setInverted(true);
        
        this.arm = armController;
        this.armEncoder = armEncoder;
    }
    
    /**
     * Set the position of the ActionArm.
     */
    public void setPosition(final ActionArmPosition position) {
        this.position = position;
        pid.setSetpoint(position.position);
    }
    
    /**
     * Get the position the arm of the ActionArm is attempting to maintain.
     */
    public ActionArmPosition getPosition() {
        return position;
    }
    
    /**
     * Get if the arm is on target.
     */
    public boolean isOnTarget() {
        return zeroed && pid.onTarget(Constants.Subsystems.ActionArm.ALLOWABLE_ERROR);
    }
    
    /**
     * Zeros the ActionArm.
     */
    public void zero() {
        zeroed = false;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("ActionArm State", position.toString());
        SmartDashboard.putNumber("ActionArm PID Result", pid.calculate(armEncoder.getDistance()));
        SmartDashboard.putNumber("ActionArm Count", armEncoder.get());
        SmartDashboard.putNumber("ActionArm Speed", armEncoder.getRate());
        SmartDashboard.putNumber("ActionArm Distance", armEncoder.getDistance());
        SmartDashboard.putNumber("ActionArm Error", pid.getError());
        SmartDashboard.putNumber("ActionArm Current", arm.getCurrent());
    }

    @Override
    public void update() {
        if (zeroed) {
            arm.set(pid.calculate(armEncoder.getDistance()));
        } else {
            if (Math.abs(armEncoder.getRate()) <= Constants.Subsystems.ActionArm.ZEROED_RATE
                    && Math.abs(arm.getCurrent()) >= Constants.Subsystems.ActionArm.ZERO_CURRENT) {
                armEncoder.reset();
                zeroed = true;
                System.out.println("ActionArm Zeroed!");
                return;
            }
            arm.set(Constants.Subsystems.ActionArm.ZERO_POWER);
        }
        
    }

}
