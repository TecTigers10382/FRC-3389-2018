/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3389.robot;

import org.usfirst.frc.team3389.robot.commands.TeliOpIntakeSpin;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInterface {
	/* TODO better to declare the properties here
	 *      and assign them in the constructor below
	 *      declare private properties for the 
	 *      joysticks and for the buttons 
	 */
	      
	Joystick jsDriver = new Joystick(0); // typically it's the left joystick
	Joystick jsOperator = new Joystick(1); // typically it's the right joystick
	
	// Button liftFull = new JoystickButton(jsOperator, 1);

	Button intakeSpinRight = new JoystickButton(jsOperator, 6);
	Button intakeSpinLeft = new JoystickButton(jsOperator, 5);

	public OperatorInterface() {
		// liftFull.whileHeld(new LiftFull());

		/* TODO the button(s) should be documented
		 *      to assist other programmers and code reuse
		 *      the joystick assignments and all button
		 *      assignments should be documented here,
		 *      even the defaults which are referenced in the
		 *      individual commands
		*/
	 		
		if (intakeSpinRight != null)
			intakeSpinRight.whileHeld(new TeliOpIntakeSpin(.75));
		if(intakeSpinLeft != null)
			intakeSpinLeft.whileHeld(new TeliOpIntakeSpin(-.75));
	}

	/**
	 * Let's other objects get values from the left joystick.
	 * 
	 * @return Returns the leftStick object
	 */
	public Joystick getDriverJoystick() {
		return jsDriver;
	}

	/**
	 * Let's other objects get values from the right joystick.
	 * 
	 * @return Returns the rightStick object
	 */
	public Joystick getOperatorJoystick() {
		return jsOperator;
	}

	/* TODO add getter methods for the buttons
	 *      joystick and button properties should be private to the class
	 *      and getter methods used to access the controls
	 */
	
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	
}
