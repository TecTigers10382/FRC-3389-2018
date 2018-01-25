package org.usfirst.frc.team3389.robot.subsystems.manipulators;

import org.usfirst.frc.team3389.robot.Robot;
import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.LiftStick;
import org.usfirst.frc.team3389.robot.subsystems.ioDevices.QuadEncoder;
import org.usfirst.frc.team3389.robot.utils.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lifter extends Subsystem{
	TalonSRX lift;
	private double height;
	private double radius;
	public static final QuadEncoder enc = new QuadEncoder();
		
	public Lifter() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		lift = new TalonSRX(RobotMap.LIFT);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
	
	public void driveLift(double power) {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter:\t" + power);
		
		lift.set(ControlMode.PercentOutput, power);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");	
	}
	
	protected void initDefaultCommand() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		setDefaultCommand(new LiftStick());
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}	
	
	public void stop() {
		Robot.robotLogger.log(Logger.DEBUG, this, "enter");
		
		driveLift(0);
		
		Robot.robotLogger.log(Logger.DEBUG, this, "exit");
	}
	
	public void getHeight() {
		height=Math.PI*enc.getVal();
	}
}
