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
		timeOfFlightSensor = new VL53L0X(1);
	}

	/**
	 * Get's the distance in millimeters from the Time of Flight Sensor
	 * 
	 * @return distance in millimeters; -1 if there is an error
	 */
	public int getDistanceMillimeters() {
		try {
			return timeOfFlightSensor.readRangeSingleMillimeters();
		} catch (NACKException e) {
			Robot.robotLogger.log(Logger.ERROR, this, "Could not read Time of Flight Sensor\n" + e.getStackTrace());
		}

		return -1;
	}

}
