package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Subsystem;
import com.milkenknights.util.SynchronousPid;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult extends Subsystem implements Loopable {
    
    public enum CatapultState {
        RETRACT, READY, FIRE, ZERO
    }
    
    private final MkCanTalon talon;
    //private final Solenoid ballHolder;
    private final MkEncoder encoder;
    private final DigitalInput home;
    private final SynchronousPid pid;
    private CatapultState state;
    private int shotCount;
    private boolean zeroed;

    /**
     * Create a new catapult subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The talon to control the catapult
     */
    public Catapult(final String name, final MkCanTalon talon, final Solenoid ballHolder, final MkEncoder encoder,
            final DigitalInput home) {
        super(name);
        
        talon.enableBrakeMode(true);
        encoder.setDistancePerPulse(Constants.Subsystems.Catapult.GEAR_RATIO / encoder.getPulsesPerRevolution());
        
        home.requestInterrupts(new InterruptHandlerFunction<Object>() {
            @Override
            public void interruptFired(final int arg0, final Object arg1) {
                zeroed = true;
                encoder.reset();
                System.out.println("Catapult Zeroed!");
            }
            
        });
        home.setUpSourceEdge(false, true);
        home.enableInterrupts();
        
        pid = new SynchronousPid();
        pid.setPid(2, 0, 0);
        pid.setOutputRange(Constants.Subsystems.Catapult.DEADBAND, 1);

        this.talon = talon;
        //this.ballHolder = ballHolder;
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
        SmartDashboard.putNumber("Catapult Error", pid.getError());
        SmartDashboard.putNumber("Catapult PID Result", pid.calculate(encoder.getDistance()));
        SmartDashboard.putBoolean("Catapult Banner", home.get());
        SmartDashboard.putBoolean("Catapult Zeroed", zeroed);
        SmartDashboard.putBoolean("Catapult Home", !home.get());
    }

    @Override
    public void update() {
        switch (state) {
            case RETRACT:
                pid.setSetpoint(shotCount + Constants.Subsystems.Catapult.OFFSET);
                talon.set(pid.calculate(encoder.getDistance()));
                if (pid.onTarget(Constants.Subsystems.Catapult.ALLOWABLE_ERROR) || pid.getError() < 0) {
                    state = CatapultState.READY;
                }
                break;
            case READY:
                talon.set(0.0);
                break;
            case FIRE:
                pid.setSetpoint(shotCount + 1 + Constants.Subsystems.Catapult.OFFSET);
                talon.set(pid.calculate(encoder.getDistance()));
                if (pid.onTarget(Constants.Subsystems.Catapult.ALLOWABLE_ERROR)) {
                    shotCount++;
                    state = CatapultState.RETRACT;
                }
                break;
            case ZERO:
                if (!zeroed) {
                    talon.set(Constants.Subsystems.Catapult.DEADBAND);
                } else {
                    home.disableInterrupts();
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
