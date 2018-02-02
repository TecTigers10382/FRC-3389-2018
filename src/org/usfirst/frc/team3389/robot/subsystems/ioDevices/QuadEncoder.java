package org.usfirst.frc.team3389.robot.subsystems.ioDevices;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
public class QuadEncoder extends Subsystem{
	public static Encoder leftEnc  = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	public static Encoder rightEnc = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	
	
public static void encoderInit() {
	leftEnc.setMaxPeriod(1);
	leftEnc.setMinRate(10);
	leftEnc.setDistancePerPulse(5);
	leftEnc.setReverseDirection(true);
	leftEnc.setSamplesToAverage(7);
	rightEnc.setMaxPeriod(1);
	rightEnc.setMinRate(10);
	rightEnc.setDistancePerPulse(5);
	rightEnc.setReverseDirection(true);
	rightEnc.setSamplesToAverage(7);
}


@Override
protected void initDefaultCommand() {
	// TODO Auto-generated method stub
	
}
}