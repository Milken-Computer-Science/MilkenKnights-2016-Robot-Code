package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;

public class WaitForIntakeArmAction extends TimeoutAction {

    public WaitForIntakeArmAction(double time) {
        super(time);
    }
    
    @Override
    public boolean isFinished() {
        return HardwareAdapter.INTAKE.armOnTarget() || super.isFinished();
    }

}
