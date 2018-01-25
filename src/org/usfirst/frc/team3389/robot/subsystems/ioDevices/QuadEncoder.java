package org.usfirst.frc.team3389.robot.subsystems.ioDevices;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class QuadEncoder extends SensorBase implements CounterBase, PIDSource, Sendable {
	Encoder enc;
//To Grab Encoder Value, use QuadEncoder.get(); returns an int.
//To Grab Encoder Rate, use QuadEncoder.getRate(); returns a double.
//To Zero the Encoder, use QuadEncoder.Zero();
	public void Encoder() {

		enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

	}
	
	public void Zero() {
		enc.reset();
	}
	
	public int getVal() {
		return enc.get();
	}
	
	public double getRate() {
		return enc.getRate();
	}
	@Override
	public void initSendable(SendableBuilder builder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get() {
		return 0;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public double getPeriod() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxPeriod(double maxPeriod) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getStopped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDirection() {
		// TODO Auto-generated method stub
		return false;
	}
}