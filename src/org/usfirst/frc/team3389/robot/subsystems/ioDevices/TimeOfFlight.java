/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.subsystems.ioDevices;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.VL53L0X.I2CUpdatableAddress.NACKException;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.VL53L0X.VL53L0X;
import org.usfirst.frc.team3389.robot.utils.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Uses VL53L0X library to read distance from objects.
 * 
 * @author TEC Tigers
 * @see org.usfirst.frc.team3389.robot.subsystems.ioDevices.VL53L0X.VL53L0X
 */
public class TimeOfFlight extends Subsystem {

	VL53L0X timeOfFlightSensor;

	public void initDefaultCommand() {
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Constructor.
	 */
	public TimeOfFlight() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		timeOfFlightSensor = new VL53L0X(0);
		try {
			timeOfFlightSensor.init(false);
		} catch (NACKException e) {
			// TODO Auto-generated catch block
			Robot.robotLogger.log(Logger.ERROR, this, "failed to initialize VL53L0X sensor");
		}
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}

	/**
	 * Get's the distance in millimeters from the Time of Flight Sensor
	 * 
	 * @return distance in millimeters; -1 if there is an error
	 */
	public int getDistanceMillimeters() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		int val = -1;
		try {
			val = timeOfFlightSensor.readRangeSingleMillimeters();
		} catch (NACKException e) {
			Robot.robotLogger.log(Logger.ERROR, this, "Could not read Time of Flight Sensor\n");
		}
		if (val > 8000) { // values aroudn 8190 are all erroneous conditions
			Robot.robotLogger.log(Logger.DEBUG, this, "erroneous range value = " + val);
			val = -1;
		}
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
		return val;
	}

}
