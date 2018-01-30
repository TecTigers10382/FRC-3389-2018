/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//https://github.com/FRC-Team-Vern/VL53L0X_Example/blob/master/src/org/usfirst/frc/team5461/robot/sensors/I2CUpdatableAddress.java

package org.usfirst.frc.team3389.robot.subsystems.ioDevices;


import java.nio.ByteBuffer;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.utils.Logger;

//import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;

/**
 * I2C bus interface class.
 *
 * <p>
 * This class is intended to be used by sensor (and other I2C device) drivers.
 * It probably should not be used directly.
 * 
 * @author FRC Team 5461
 */
public class I2CUpdatableAddress extends I2C {

	private Port _port;
	private int _device;
	
	public I2CUpdatableAddress(Port port, int deviceAddress) {
		super(port, deviceAddress);
		this._port = port;
		this._device = deviceAddress;
	}

	public byte readByte(int registerAddress) {
		byte val = 0;
		byte[] buffer = new byte[1];
		read(registerAddress, 1, buffer);
		val = (byte) (buffer[0] & 0xff);
		return val;
	}
	
	public short readShort(int registerAddress) {
		short val = 0;
		ByteBuffer bufferResults = ByteBuffer.allocateDirect(2);
		read(registerAddress, 2, bufferResults);
		val = bufferResults.getShort();
		return val;
	}
	
	public boolean writeByte(int registerAddress, byte data) {
		// simple passthru to maintain API consistency with 'read' methods
		boolean failed = write(registerAddress, (((int)data) & 0xff));
		if (failed)
			Robot.robotLogger.log(Logger.INFO, this, "writeByte failed");
		return failed;
	}

	public boolean writeByte(int registerAddress, int data) {
		// simple passthru to maintain API consistency with 'read' methods
		boolean failed = write(registerAddress, (data & 0xff));
		if (failed)
			Robot.robotLogger.log(Logger.INFO, this, "writeByte failed");
		return failed;
	}
	
	public boolean writeShort(int registerAddress, int data) {
		// the following guarantees correct byte order regardless of big/little endian
		ByteBuffer registerWithDataToSendBuffer = ByteBuffer.allocateDirect(3);
		registerWithDataToSendBuffer.put((byte) registerAddress);
		registerWithDataToSendBuffer.putShort(1, (short) data);
		return writeBulk(registerWithDataToSendBuffer, 3);
	}

	
	public final int setAddress(int deviceCommandSetAddress, int new_address) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		// NOTICE: CHANGING THE ADDRESS IS NOT STORED IN NON-VOLATILE MEMORY
		// POWER CYCLING THE DEVICE REVERTS ADDRESS BACK TO its default
		// Field field = I2C.class.getDeclaredField("m_deviceAddress");
		// field.setAccessible(true);
		//
		// int deviceAddress = (int) field.get(this);
		
		// changing an I2C device address, when there are more than one device defaulting to the same address
		// requires that all other devices on the same address be disabled first
		// this is typically done with a digital IO signal pin - DigitalOutput(pin); 
		// /* for all other devices */ DigitalOutput.set(false); /* for current device */ DigitalOutput.set(true); setAddress(new_address);
		// example of wiring:
		// https://raw.githubusercontent.com/johnbryanmoore/VL53L0X_rasp_python/master/VL53L0X_Mutli_Rpi3_bb.jpg
		// example of digital IO control code:
		// https://github.com/FRC-Team-Vern/VL53L0X_Example/blob/master/src/org/usfirst/frc/team5461/robot/sensors/VL53L0XSensors.java

		Robot.robotLogger.log(Logger.DEBUG, this, "setting address to 0x" + Integer.toHexString(new_address));

		if (this._device == new_address) {
			Robot.robotLogger.log(Logger.DEBUG, this, "exit - no address change required");
			return this._device;
		}
		// Device addresses cannot go higher than 127
		if (new_address > 127) {
			Robot.robotLogger.log(Logger.ERROR, this, "address out of range");
			return this._device;
		}

		boolean success = write(deviceCommandSetAddress, new_address & 0x7F);
		if (success) {
			this._device = new_address;
		}
		else
			Robot.robotLogger.log(Logger.ERROR, this, "failed to change address");
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
		return this._device;
	}

	public final int getAddressFromDevice(int deviceCommandGetAddress) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		byte val;
		val = readByte(deviceCommandGetAddress);
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
		return val;
	}

}