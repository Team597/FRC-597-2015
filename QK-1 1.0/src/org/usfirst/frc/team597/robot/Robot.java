package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

	Joystick j1 = new Joystick(0);
	Joystick j2 = new Joystick(1);
	Joystick j3 = new Joystick(2);
	Talon t1 = new Talon(0);
	Talon t2 = new Talon(1);
	Talon t3 = new Talon(4);
	Compressor comp = new Compressor();
	DoubleSolenoid claw = new DoubleSolenoid(0, 7);
	DoubleSolenoid brake = new DoubleSolenoid(1, 6);

	public void robotInit() {

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

		t1.set(j1.getY() * -1);
		t2.set(j2.getY());
		t3.set(j3.getY());

		if (j1.getRawButton(1) == true) {
			claw.set(Value.kReverse);
		} else {
			claw.set(Value.kForward);
		}

		if (j3.getRawButton(1) == true) {
			brake.set(Value.kForward);
		} else {
			brake.set(Value.kReverse);
		}
	}

	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
