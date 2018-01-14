/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot.ioDevices;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TimeOfFlight extends Subsystem {

	// Address of TOF, default is 30
	public final byte TOF_ADDR = 0x30;

	// Buffer size of how much data is received
	public final int I2C_BUF_SIZE = 6;

	I2C timeOfFlight;

	public TimeOfFlight() {
		timeOfFlight = new I2C(Port.kOnboard, TOF_ADDR);
	}

	public void initDefaultCommand() {

	}

	// Returns distance from object in mm from 0 to 1200, if there is an error
	// returns -1
	public int getDistance() {
		// initialize mm with -1
		int mm = -1;

		// Read new info
		byte[] buffer = new byte[I2C_BUF_SIZE];
		timeOfFlight.readOnly(buffer, I2C_BUF_SIZE);

		int checksum = 0;
		// calculates checksum by adding together all values. the first byte is garbage,
		// and the last is the checksum.
		for (int i = 1; i < I2C_BUF_SIZE - 1; i++) {
			checksum += buffer[i];
		}

		// sets mm to distance in mm after making sure checksum checks out.
		if (checksum == buffer[I2C_BUF_SIZE - 1]) {
			// magic numbers
			mm = buffer[3] << 8 + buffer[4];
		}

		// doesn't let mm go over
		if (mm > 1200) {
			mm = 1200;
		}

		return mm;
	}
}
