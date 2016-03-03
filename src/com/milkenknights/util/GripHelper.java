package com.milkenknights.util;

import com.milkenknights.frc2016.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class GripHelper implements MkSendable, ITableListener {
    
    private final ITable gripTable = NetworkTable.getTable(Constants.Vision.GRIP_TABLE_ID);
    private final ITable targetTable = gripTable.getSubTable(Constants.Vision.TARGETS_TABLE_ID);
    private final ITable matSize = gripTable.getSubTable(Constants.Vision.MAT_SIZE_TABLE_ID);
    
    private Target cachedTarget = new Target(0, 0, 0, 0, 0, 0);
    
    /**
     * Class to store the data of a target.  
     */
    public class Target {
        public double centerX;
        public double centerY;
        public double width;
        public double height;
        public double area;
        public double solidity;
        
        /**
         * Create a new target.
         */
        public Target(double centerX, double centerY, double width, double height, double area, double solidity) {
            reset(centerX, centerY, width, height, area, solidity);
        }
        
        /**
         * Reset this targets information.
         */
        public void reset(double centerX, double centerY, double width, double height, double area, double solidity) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.width = width;
            this.height = height;
            this.area = area;
            this.solidity = solidity;
        }
    }
    
    /**
     * Tell this GripHelper to begin listening for target updates.
     */
    public void register() {
        targetTable.addTableListener(this);
    }
    
    /**
     * Gets the angle to the largest target based on the last image processed by GRIP.  
     * @return The angle from -180.0 to 180.0
     */
    public double getAngleToTarget() {
        return ((2 * (cachedTarget.centerX / matSize.getNumber("x", 1))) - 1) * Constants.Vision.HORIZONTAL_FOV / 2.0;
    }
    
    @Override
    public void valueChanged(ITable source, String key, Object value, boolean isNew) {
        double[] centerX = source.getNumberArray("centerX", new double[0]);
        double[] centerY = source.getNumberArray("centerY", new double[0]);
        double[] width = source.getNumberArray("width", new double[0]);
        double[] height = source.getNumberArray("height", new double[0]);
        double[] area = source.getNumberArray("area", new double[0]);
        double[] solidity = source.getNumberArray("solidity", new double[0]);
        
        if (area.length > 0) {
            int index = 0;
            double largest = 0;
            for (int i = 0; i < area.length; i++) {
                if (area[i] >= largest) {
                    largest = area[i];
                    index = i;
                }
            }
            
            try {
                cachedTarget.reset(centerX[index], centerY[index], width[index], height[index], area[index],
                    solidity[index]);
            } catch (ArrayIndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }
        }
    }
    
    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Vision Angle to Target", getAngleToTarget());
    }
    
}
