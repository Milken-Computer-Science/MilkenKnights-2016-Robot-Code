package com.milkenknights.frc2016;

import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.modes.BreachAutoMode;
import com.milkenknights.frc2016.auto.modes.ReachAutoMode;
import com.milkenknights.frc2016.behavior.BehaviorManager;
import com.milkenknights.frc2016.subsystems.Drive.DriveGear;
import com.milkenknights.util.MultiLooper;
import com.milkenknights.util.SmartDashboardUpdater;
import com.milkenknights.util.drive.MotorPairSignal;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {
    
    private final MultiLooper looper = new MultiLooper("Controllers", Constants.ControlLoops.CONTROLLERS_PERIOD);
    private final MultiLooper visionLooper = new MultiLooper("Vision", Constants.ControlLoops.VISION_PERIOD);
    private final SmartDashboardUpdater smartDashboardUpdater = new SmartDashboardUpdater(
            Constants.ControlLoops.SMARTDASHBOARD_UPDATER_PERIOD);
    
    private BehaviorManager behaviorManager;
    private OperatorInterface operatorInterface;
    
    private AutoMode autoMode;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    public void robotInit() {
        System.out.println("Start robotInit()");
        
        behaviorManager = new BehaviorManager();
        operatorInterface = new OperatorInterface();
        
        autoMode = new BreachAutoMode();
        
        looper.addLoopable(HardwareAdapter.DRIVE);
        //looper.addLoopable(HardwareAdapter.INTAKE);
        looper.addLoopable(HardwareAdapter.CATAPULT);
        
        smartDashboardUpdater.addSendable(HardwareAdapter.DRIVE);
        smartDashboardUpdater.addSendable(HardwareAdapter.INTAKE_ARM);
        smartDashboardUpdater.addSendable(HardwareAdapter.INTAKE_SPEED);
        smartDashboardUpdater.addSendable(HardwareAdapter.CATAPULT);
        smartDashboardUpdater.addSendable(HardwareAdapter.BALL_CLAMP);
        smartDashboardUpdater.addSendable(HardwareAdapter.GRIP);
        
        visionLooper.start();
        smartDashboardUpdater.start();
        
        HardwareAdapter.LED_RING.set(true);
        HardwareAdapter.GRIP.register();
        HardwareAdapter.DRIVE.resetEncoders();
        System.out.println("End robotInit()");
    }

    /**
     * This function is called at the start of autonomous.
     */
    public void autonomousInit() {
        System.out.println("Start autonomousInit()");
        HardwareAdapter.COMPRESSOR.stop();
        HardwareAdapter.DRIVE.setGear(DriveGear.HIGH);
        HardwareAdapter.DRIVE.resetEncoders();
        HardwareAdapter.DRIVE.resetGyro();
        
        looper.start();
        autoMode.start();

        System.out.println("End autonomousInit()");
    }
    
    /**
     * This function is called at the start of telop.
     */
    public void teleopInit() {
        System.out.println("Start teleopInit()");
        
        HardwareAdapter.COMPRESSOR.start();
        HardwareAdapter.DRIVE.resetEncoders();
        
        looper.start();
        System.out.println("End teleopInit()");
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
        behaviorManager.update(operatorInterface.getCommands());
    }
    
    /**
     * This function is called at the start of the disabled period.
     */
    public void disabledInit() {
        System.out.println("Start disabledInit()");
        
        autoMode.stop();
        looper.stop();

        HardwareAdapter.COMPRESSOR.stop();
        HardwareAdapter.DRIVE.setOpenLoop(MotorPairSignal.NEUTRAL);
        
        System.gc();
        System.out.println("End disabledInit()");
    }

}
