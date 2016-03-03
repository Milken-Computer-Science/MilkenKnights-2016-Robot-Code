package com.milkenknights.frc2016.subsystems;

import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult extends Subsystem implements Loopable {
    
    public enum CatapultState {
        RETRACT, READY, FIRE
    }
    
    private MkCanTalon talon;
    private DigitalInput home;
    private CatapultState state;
    private int shotCount = 0;

    /**
     * Create a new catapult subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The talon to control the catapult
     */
    public Catapult(String name, MkCanTalon talon, DigitalInput home) {
        super(name);
        
        talon.setInverted(true);
        
        home.requestInterrupts(new InterruptHandlerFunction<Object>() {
            @Override
            public void interruptFired(int arg0, Object arg1) {
                home.disableInterrupts();
                //talon.setPosition(0);
                System.out.println("Catapult Zeroed!");
            }
            
        });
        //banner.setUpSourceEdge(true, false);
        //banner.enableInterrupts();

        this.talon = talon;
        this.home = home;
        
        setState(CatapultState.READY);
    }
    
    /**
     * Fire the catapult.
     */
    public void fire() {
        setState(CatapultState.FIRE);
    }
    
    public void stop() {
        setState(CatapultState.READY);
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
//        SmartDashboard.putNumber("Catapult Count", talon.get());
//        SmartDashboard.putNumber("Catapult Error", talon.getError());
        SmartDashboard.putBoolean("Catapult Banner", home.get());
    }

    @Override
    public void update() {
        switch (state) {
            case RETRACT:
//                talon.set(shotCount * camRevolution);
//                if (talon.getClosedLoopError() < 100) {
//                    state = CatapultState.READY;
//                }
                break;
            case READY:
                talon.set(0);
                break;
            case FIRE:
                talon.set(0.5);
                //talon.set((shotCount + 1) * camRevolution);
                //if (Math.abs(talon.getError()) < 100) {
                //    shotCount++;
                //    state = CatapultState.RETRACT;
                //}
                break;
            default:
                break;
        }
        
    }
    
    private void setState(CatapultState state) {
        this.state = state;
    }

}
