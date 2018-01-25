package org.usfirst.frc.team3389.robot.subsystems.manipulators;

import org.usfirst.frc.team3389.robot.subsystems.ioDevices.QuadEncoder;

public class Lifter{
	private double height;
	private double radius;
	public static final QuadEncoder enc = new QuadEncoder();
	
	public void Lifter() {
		
	}
	
	public void getHeight() {
		height=Math.pi*enc.getVal();
	}
}