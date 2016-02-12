package com.milkenknights.frc2016.subsystems;

import com.milkenknights.util.Loopable;
import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult extends Subsystem implements Loopable {
    
    public enum CatapultState {
        RETRACT, READY, FIRE
    }
    
    private CANTalon talon;
    private DigitalInput banner;
    private CatapultState state;
    private int shotCount = 0;

    /**
     * Create a new catapult subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The talon to control the catapult
     */
    public Catapult(String name, CANTalon talon, DigitalInput banner) {
        super(name);
        
        talon.changeControlMode(TalonControlMode.PercentVbus);
//        talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
//        talon.changeControlMode(CANTalon.TalonControlMode.Position);
//        talon.reverseSensor(true);
//        talon.setAllowableClosedLoopErr(25);
//        talon.setIZone(1000);
//        talon.configPeakOutputVoltage(15, -6);
//        talon.set(0);
//        talon.setPID(0.5, 0.0005, 0);
//        talon.setF(0);
        
        banner.requestInterrupts(new InterruptHandlerFunction<Object>() {
            @Override
            public void interruptFired(int arg0, Object arg1) {
                banner.disableInterrupts();
                talon.setPosition(0);
                System.out.println("Catapult Zeroed!");
            }
            
        });
        //banner.setUpSourceEdge(true, false);
        //banner.enableInterrupts();

        this.talon = talon;
        this.banner = banner;
        
        setState(CatapultState.READY);
    }
    
    /**
     * Fire the catapult.
     */
    public void fire() {
        setState(CatapultState.FIRE);
    }
    
    /**
     * This method is temporary because there is no encoder on the catapult.
     * 
     * TODO: Remove method
     */
    public void stop() {
        setState(CatapultState.READY);
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Catapult State", state.toString());
//        SmartDashboard.putNumber("Catapult Count", talon.get());
//        SmartDashboard.putNumber("Catapult Error", talon.getError());
        SmartDashboard.putBoolean("Catapult Banner", banner.get());
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
                talon.set(1);
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
