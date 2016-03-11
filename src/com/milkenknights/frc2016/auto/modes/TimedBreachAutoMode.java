package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;

public class TimedBreachAutoMode extends AutoMode {

	@Override
	protected void routine() throws AutoModeEndedException {
		waitForTimedDrive(7.0, 1.0);
	}

}
