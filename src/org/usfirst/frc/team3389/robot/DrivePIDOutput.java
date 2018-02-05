package org.usfirst.frc.team3389.robot;


import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class DrivePIDOutput implements PIDOutput {
	
	private SpeedController c;

	/**
	 * @param c Speed controller
	 */
	public DrivePIDOutput(SpeedController c) {
		this.c = c;
	}
	
	@Override
	public void pidWrite(double output) {
		c.set(output);
	}

}