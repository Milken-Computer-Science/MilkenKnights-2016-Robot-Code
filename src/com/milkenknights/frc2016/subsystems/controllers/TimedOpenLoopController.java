package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.util.Controller;

import edu.wpi.first.wpilibj.Timer;

public class TimedOpenLoopController extends Controller {

    protected double m_t0 = 0; // Time of control loop init
    protected double m_t1 = 0; // Time to start deceling
    protected double m_t2 = 0; // Time to end decel
    protected double m_start_power = 0;
    protected double m_end_power = 0;

    /**
     * Create a new TimedOpenLoopController.
     * 
     * @param startPower The inital power to start with
     * @param timeFullOn The time to leave the power at full
     * @param endPower The power to have at the end
     * @param timeToDecel The time to spend decelerating
     */
    public TimedOpenLoopController(double startPower, double timeFullOn, double endPower, double timeToDecel) {
        m_t0 = Timer.getFPGATimestamp();
        m_t1 = m_t0 + timeFullOn;
        m_t2 = m_t1 + timeToDecel;
        m_start_power = startPower;
        m_end_power = endPower;
    }

    public boolean expired() {
        return Timer.getFPGATimestamp() > m_t2;
    }

    /**
     * Called every control loop.
     * 
     * @return The power to send to the motor
     */
    public double update() {
        double cur = Timer.getFPGATimestamp();
        if (cur <= m_t1) {
            return m_start_power;
        } else if (cur > m_t1 && cur <= m_t2) {
            // decel
            double rel_t = cur - m_t1;
            double slope = (m_end_power - m_start_power) / (m_t2 - m_t1);
            return (m_start_power + (slope * rel_t));
        } else {
            return m_end_power;
        }
    }

    @Override
    public void reset() {
        m_t0 = m_t1 = m_t2 = m_end_power = m_start_power = 0;
    }

    @Override
    public boolean isOnTarget() {
        return expired();
    }
}
