package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Subsystem;
import com.milkenknights.util.SynchronousPid;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class Catapult extends Subsystem implements Loopable {
    
    public enum CatapultState {
        RETRACT, READY, FIRE, ZERO
    }
    
    private enum ZeroState {
        RETRACT, REVERSE, ZERO, ZEROED
    }
    
    private final MkCanTalon talon;
    private final MkEncoder encoder;
    private final DigitalInput home;
    private final SynchronousPid positionPid;
    private final SynchronousPid velocityPid;
    private CatapultState state;
    private int shotCount;
    private ZeroState zeroState;

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
        positionPid.setPid(Constants.Subsystems.Catapult.POSITION_KP,
                Constants.Subsystems.Catapult.POSITION_KI,
                Constants.Subsystems.Catapult.POSITION_KD);
        positionPid.setOutputRange(-1.0, 1.0);
        
        velocityPid = new SynchronousPid();
        velocityPid.setPid(Constants.Subsystems.Catapult.VELOCITY_KP,
                Constants.Subsystems.Catapult.VELOCITY_KI,
                Constants.Subsystems.Catapult.VELOCITY_KD);
        velocityPid.enableMaxVelocityFeedForward(Constants.Subsystems.Catapult.MAX_VELOCITY);
        velocityPid.setOutputRange(-1.0, 1.0);
        velocityPid.sumOutput();
        
        this.talon = talon;
        this.encoder = encoder;
        this.home = home;
        
        zeroState = ZeroState.RETRACT;
        setState(CatapultState.READY);
    }
    
    /**
     * Fire the catapult.
     */
    public void fire() {
        setState(CatapultState.FIRE);
    }
    
    /**
     * Zero the catapult.
     */
    public void zero() {
    	shotCount = 0;
    	zeroState = ZeroState.RETRACT;
    	setState(CatapultState.ZERO);
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
                positionPid.setSetpoint(shotCount + Constants.Subsystems.Catapult.READY_OFFSET);
                velocityPid.setSetpoint(positionPid.calculate(encoder.getDistance()));
                talon.set(velocityPid.calculate(encoder.getRate()));
                if (positionPid.onTarget(Constants.Subsystems.Catapult.ALLOWABLE_ERROR) || positionPid.getError() < 0) {
                    state = CatapultState.READY;
                }
                break;
            case READY:
                velocityPid.setSetpoint(positionPid.calculate(encoder.getDistance()));
                talon.set(velocityPid.calculate(encoder.getRate()));
                break;
            case FIRE:
                if (zeroState != ZeroState.ZEROED) {
                    state = CatapultState.ZERO;
                    break;
                }
                positionPid.setSetpoint(Constants.Subsystems.Catapult.READY_OFFSET + shotCount + 1);
                velocityPid.setSetpoint(positionPid.calculate(encoder.getDistance()));
                talon.set(velocityPid.calculate(encoder.getRate()));
                if (positionPid.onTarget(Constants.Subsystems.Catapult.ALLOWABLE_ERROR)) {
                    shotCount++;
                    state = CatapultState.RETRACT;
                }
                break;
            case ZERO:
                switch (zeroState) {
                    case RETRACT:
                        if (!home.get()) {
                            zeroState = ZeroState.REVERSE;
                            break;
                        }
                        velocityPid.setSetpoint(Constants.Subsystems.Catapult.RETRACT_SPEED);
                        talon.set(velocityPid.calculate(encoder.getRate()));
                        break;
                    case REVERSE:
                        if (home.get()) {
                            zeroState = ZeroState.ZERO;
                            break;
                        }
                        velocityPid.setSetpoint(-Constants.Subsystems.Catapult.SLOW_RETRACT_SPEED);
                        talon.set(velocityPid.calculate(encoder.getRate()));
                        break;
                    case ZERO:
                        if (!home.get()) {
                            encoder.reset();
                            zeroState = ZeroState.ZEROED;
                            System.out.println("Catapult Zeroed!");
                            setState(CatapultState.RETRACT);
                            break;
                        }
                        velocityPid.setSetpoint(Constants.Subsystems.Catapult.SLOW_RETRACT_SPEED);
                        talon.set(velocityPid.calculate(encoder.getRate()));
                        break;
                    case ZEROED:
                        break;
                    default:
                        break;
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
