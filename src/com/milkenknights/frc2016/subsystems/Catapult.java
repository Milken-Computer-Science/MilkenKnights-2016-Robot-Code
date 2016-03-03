package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Subsystem;
import com.milkenknights.util.SynchronousPid;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult extends Subsystem implements Loopable {
    
    public enum CatapultState {
        RETRACT, READY, FIRE, ZERO
    }
    
    private MkCanTalon talon;
    private MkEncoder encoder;
    private DigitalInput home;
    private CatapultState state;
    private int shotCount = 0;
    private SynchronousPid pid;
    private boolean zeroed = false;

    /**
     * Create a new catapult subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The talon to control the catapult
     */
    public Catapult(String name, MkCanTalon talon, MkEncoder encoder, DigitalInput home) {
        super(name);
        
        talon.setInverted(true);
        talon.enableBrakeMode(true);
        
        home.requestInterrupts(new InterruptHandlerFunction<Object>() {
            @Override
            public void interruptFired(int arg0, Object arg1) {
                zeroed = true;
                encoder.reset();
                System.out.println("Catapult Zeroed!");
            }
            
        });
        home.setUpSourceEdge(false, true);
        home.enableInterrupts();
        
        pid = new SynchronousPid();
        pid.setPid(.0001, 0, 0);

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
        SmartDashboard.putNumber("Catapult Count", encoder.get());
        SmartDashboard.putNumber("Catapult Error", pid.getError());
        SmartDashboard.putBoolean("Catapult Banner", home.get());
        SmartDashboard.putNumber("Catapult PID Result", pid.calculate(encoder.get()));
        SmartDashboard.putBoolean("Catapult Zeroed", zeroed);
        SmartDashboard.putBoolean("Catapult Home", !home.get());
    }

    @Override
    public void update() {
        switch (state) {
            case RETRACT:
                pid.setSetpoint(shotCount * Constants.Subsystems.Catapult.GEAR_RATIO
                        * encoder.getPulsesPerRevolution());
                talon.set(pid.calculate(encoder.get()));
                if (pid.onTarget(100)) {
                    state = CatapultState.READY;
                }
                break;
            case READY:
                break;
            case FIRE:
                pid.setSetpoint((shotCount + 1) * Constants.Subsystems.Catapult.GEAR_RATIO 
                        * encoder.getPulsesPerRevolution());
                talon.set(pid.calculate(encoder.get()));
                if (pid.onTarget(100)) {
                    shotCount++;
                    state = CatapultState.RETRACT;
                }
                break;
            case ZERO:
                if (!zeroed) {
                    talon.set(0.25);
                } else {
                    home.disableInterrupts();
                    talon.set(0);
                    setState(CatapultState.RETRACT);
                }
                break;
            default:
                break;
        }
        
    }
    
    private void setState(CatapultState state) {
        this.state = state;
    }

}
