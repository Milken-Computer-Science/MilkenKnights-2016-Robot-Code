package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.BehaviorManager;
import com.milkenknights.util.MotorPairSignal;
import com.milkenknights.util.MultiLooper;
import com.milkenknights.util.SmartDashboardUpdater;
import com.milkenknights.util.TankDriveHelper;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {
    
    private MultiLooper looper = new MultiLooper("Controllers", 1 / 100.0);
    private SmartDashboardUpdater smartDashboardUpdater = new SmartDashboardUpdater(1 / 50.0);
    
    private BehaviorManager behaviorManager;
    private OperatorInterface operatorInterface;
    private TankDriveHelper tankDriveHelper;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    public void robotInit() {
        System.out.println("Start robotInit()");
        HardwareAdapter.init();
        
        behaviorManager = new BehaviorManager();
        operatorInterface = new OperatorInterface();
        tankDriveHelper = new TankDriveHelper(HardwareAdapter.DRIVE);
        
        looper.addLoopable(HardwareAdapter.DRIVE);
        looper.addLoopable(HardwareAdapter.CATAPULT);
        
        smartDashboardUpdater.addSendable(HardwareAdapter.DRIVE);
        smartDashboardUpdater.addSendable(HardwareAdapter.CATAPULT);
        
        smartDashboardUpdater.start();
        System.out.println("End robotInit()");
    }

    /**
     * This function is called at the start of autonomous.
     */
    public void autonomousInit() {
        System.out.println("Start autonomousInit()");
        HardwareAdapter.DRIVE.reset();
        
        looper.start();

        System.out.println("End autonomousInit()");
    }
    
    /**
     * This function is called at the start of telop.
     */
    public void teleopInit() {
        looper.start();
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
        tankDriveHelper.drive(HardwareAdapter.LEFT_STICK.getY(), HardwareAdapter.RIGHT_STICK.getY());
        behaviorManager.update(operatorInterface.getCommands());
    }
    
    /**
     * This function is called at the start of the disabled period.
     */
    public void disabledInit() {
        System.out.println("Start disabledInit()");
        looper.stop();

        HardwareAdapter.DRIVE.setOpenLoop(MotorPairSignal.NEUTRAL);
        
        System.gc();
        System.out.println("End disabledInit()");
    }

}
