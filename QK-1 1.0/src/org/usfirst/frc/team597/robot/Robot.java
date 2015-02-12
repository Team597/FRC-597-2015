package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

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
	Talon oT = new Talon(2);
	Talon t3 = new Talon(4);
	Compressor comp = new Compressor();
	DoubleSolenoid claw = new DoubleSolenoid(0, 7);
	DoubleSolenoid brake = new DoubleSolenoid(1, 6);
	DoubleSolenoid OD = new DoubleSolenoid(2, 5);
	Encoder en1 = new Encoder(0, 1);
	PIDController elev = new PIDController(0, 0, 0, en1, t3);
	DigitalInput lBot = new DigitalInput(2);
	int eS = 1;

	public void robotInit() {

		eS = 1;
		if (eS == 1) {
			t3.set(-0.5);
			if (lBot.get() == true) {
				t3.set(0);
				en1.reset();
				eS = 2;
			}
			if (eS == 2) {
				t3.set(.80);
				Timer.delay(1);
				elev.enable();
			}

		}
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
		if (j2.getRawButton(2)) {
			t3.set(j3.getY());

		}
		if (j2.getRawButton(1)) {
			OD.set(Value.kForward);
			oT.set(j2.getX());
			t1.set(0);
			t2.set(0);

		}

		if (j3.getRawButton(10) == true) {
			claw.set(Value.kReverse);
		} else {
			claw.set(Value.kForward);
		}

		if (j3.getRawButton(1) == true) {
			brake.set(Value.kForward);
		} else {
			brake.set(Value.kReverse);
		}

		if (j3.getRawButton(2)) {
			elev.setSetpoint(2.0);
		}
		if (j3.getRawButton(3)) {
			elev.setSetpoint(4.0);
		}
		if (j3.getRawButton(4)) {
			elev.setSetpoint(5.0);
		}
		if (j3.getRawButton(5)) {
			elev.setSetpoint(6.0);
		}
		if (j2.getRawButton(2)) {
			elev.disable();
			t3.set(0);
		}
		if (j3.getRawButton(7)) {
			elev.enable();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
