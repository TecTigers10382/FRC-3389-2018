package org.usfirst.frc.team3389.robot.subsystems;

import org.usfirst.frc.team3389.robot.RobotMap;
import org.usfirst.frc.team3389.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

@SuppressWarnings("deprecation")
public class DriveTrain extends Subsystem {
	TalonSRX leftFront;
	TalonSRX leftBack;
	TalonSRX rightFront;
	TalonSRX rightBack;	
	
	public DriveTrain() {
		leftFront = new TalonSRX(RobotMap.DRIVE_LEFTFRONT);
		leftBack = new TalonSRX(RobotMap.DRIVE_LEFTBACK);
		rightFront = new TalonSRX(RobotMap.DRIVE_RIGHTFRONT);
		rightBack = new TalonSRX(RobotMap.DRIVE_RIGHTBACK);	
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new Drive());
	}
	
	private void rawDrive(double leftPower, double rightPower){
		leftFront.set(ControlMode.Current, leftPower);
		leftBack.set(ControlMode.Current, leftPower);
		rightFront.set(ControlMode.Current, rightPower);
		rightBack.set(ControlMode.Current, rightPower);
	}
	
	public void tankDrive(double left,double right){
		rawDrive(left,right);
	}
	
	public void Stop(){
		rawDrive(0,0);
	}
	
}
