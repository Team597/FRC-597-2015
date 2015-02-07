package org.usfirst.frc.team597.robot;

import Autonomous.Autonomous;
import Bane.Claw;
import Bane.Drive;
import Elevator.Elevator;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	Joystick xboxGamepad;
	Joystick joystickLeft;
	Joystick joystickRight;
	Talon talonLeft;
	Talon talonRight;
	Talon omniTalon;
	DoubleSolenoid omniDirection;
	DoubleSolenoid solenoidClawLeft;
	DoubleSolenoid solenoidClawRight;
	Compressor compressorAir;
	DigitalInput topSwitch;
	DigitalInput botSwitch;
	
	Elevator elevator;
	Autonomous autonomous;
	Drive robotDriveOmni;
	Claw robotClaw;
	
	boolean buttonPressed;
	

	public Robot() {
		xboxGamepad = new Joystick(2);
		joystickLeft = new Joystick(0);
		joystickRight = new Joystick(1);
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
		omniTalon = new Talon(2);
		omniDirection = new DoubleSolenoid(4, 5);
		solenoidClawLeft = new DoubleSolenoid(0, 1);
		solenoidClawRight = new DoubleSolenoid(2, 3);
		compressorAir = new Compressor();
		
		autonomous = new Autonomous(joystickLeft, joystickLeft, talonLeft, talonRight, omniTalon, omniDirection, solenoidClawLeft, solenoidClawRight);
		elevator = new Elevator();
		robotDriveOmni = new Drive(joystickLeft, joystickRight, talonLeft, talonRight, omniTalon, omniDirection);
		robotClaw = new Claw(joystickLeft, solenoidClawLeft, solenoidClawRight);
	}

	public void robotInit() {
		robotDriveOmni.robotInit();
		elevator.robotInit();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		//testing code for xboxGamepad
		buttonPressed = xboxGamepad.getRawButton(1);
		
		
		robotDriveOmni.teleopPeriodic();
		elevator.teleopPeriodic();
		robotClaw.teleopPeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
