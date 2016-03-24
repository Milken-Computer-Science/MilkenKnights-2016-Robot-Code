package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Subsystem;
import com.milkenknights.util.SynchronousPid;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult extends Subsystem implements Loopable {
    
    public enum CatapultState {
        RETRACT, READY, FIRE, ZERO
    }
    
    private final MkCanTalon talon;
    private final MkEncoder encoder;
    private final DigitalInput home;
    private final SynchronousPid positionPid;
    private final SynchronousPid velocityPid;
    private CatapultState state;
    private int shotCount;

    /**
     * Create a new catapult subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The talon to control the catapult
     */
    public Catapult(final String name, final MkCanTalon talon, final MkEncoder encoder, final DigitalInput home) {
        super(name);
        
        talon.enableBrakeMode(true);
        talon.setInverted(true);
        encoder.setDistancePerPulse(Constants.Subsystems.Catapult.GEAR_RATIO / encoder.getPulsesPerRevolution());
        
        positionPid = new SynchronousPid();
        positionPid.setPid(3.25, 0, 0);
        positionPid.setOutputRange(Constants.Subsystems.Catapult.DEADBAND, 1.0);
        
        velocityPid = new SynchronousPid();
        velocityPid.setPid(0.5, 0.0, 0.0);
        velocityPid.setFeedForward(Constants.Subsystems.Catapult.DEADBAND);
        velocityPid.setOutputRange(0, 1.0);
        velocityPid.setSumOutput(true);

        this.talon = talon;
        this.encoder = encoder;
        this.home = home;
        
        setState(CatapultState.ZERO);
    }
    
    /**
     * Fire the catapult.
     */
    public void fire() {
        setState(CatapultState.FIRE);
    }
    
    /**
     * Get the current state of the catapult.
     */
    public CatapultState getState() {
        return state;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Catapult State", state.toString());
        SmartDashboard.putNumber("Catapult Count", encoder.getDistance());
        SmartDashboard.putNumber("Catapult Speed", encoder.getRate());
        SmartDashboard.putNumber("Catapult Error", positionPid.getError());
        SmartDashboard.putNumber("Catapult PID Result", positionPid.calculate(encoder.getDistance()));
        SmartDashboard.putBoolean("Catapult Banner", home.get());
        SmartDashboard.putBoolean("Catapult Home", !home.get());
    }

    @Override
    public void update() {
        switch (state) {
            case RETRACT:
                positionPid.setSetpoint(shotCount + Constants.Subsystems.Catapult.OFFSET);
                talon.set(positionPid.calculate(encoder.getDistance()));
                if (positionPid.onTarget(Constants.Subsystems.Catapult.ALLOWABLE_ERROR) || positionPid.getError() < 0) {
                    state = CatapultState.READY;
                }
                break;
            case READY:
                talon.set(0.0);
                break;
            case FIRE:
                positionPid.setSetpoint(Constants.Subsystems.Catapult.OFFSET + shotCount + 1);
                talon.set(positionPid.calculate(encoder.getDistance()));
                if (positionPid.onTarget(Constants.Subsystems.Catapult.ALLOWABLE_ERROR)) {
                    shotCount++;
                    state = CatapultState.RETRACT;
                }
                break;
            case ZERO:
                if (home.get()) {
                    velocityPid.setSetpoint(0.3);
                    talon.set(velocityPid.calculate(encoder.getRate()));
                } else {
                    encoder.reset();
                    System.out.println("Catapult Zeroed!");
                    talon.set(0.0);
                    setState(CatapultState.RETRACT);
                }
                break;
            default:
                break;
        }
        
    }
    
    private void setState(final CatapultState state) {
        this.state = state;
    }

}
