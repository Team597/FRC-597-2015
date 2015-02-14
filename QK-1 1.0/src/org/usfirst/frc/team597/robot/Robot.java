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
	Joystick j4 = new Joystick(3);
	Talon t1 = new Talon(0);
	Talon t2 = new Talon(1);
	Talon oT = new Talon(4); //not connected on roboRio
	Talon t3 = new Talon(2);
	Compressor comp = new Compressor();
	DoubleSolenoid claw = new DoubleSolenoid(0, 7);
	DoubleSolenoid brake = new DoubleSolenoid(1, 6);
	DoubleSolenoid OD = new DoubleSolenoid(2, 5);
	Encoder en1 = new Encoder(7, 8);
	Encoder MM = new Encoder(1, 2);
	Encoder LT = new Encoder(5, 6);
	Encoder RT = new Encoder(3, 4);
	PIDController elev = new PIDController(0, 0, 0, en1, t3);
	DigitalInput lBot = new DigitalInput(0);
	DigitalInput lTop = new DigitalInput(9);
	int eS = 1;
	long print = System.currentTimeMillis();

	double TOTEONE = 3.0;
	double TOTETWO = 5.0;
	double TOTETHREE = 7.0;
	double TOTEFOUR = 9.0;

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

		if (System.currentTimeMillis() >= print) {

			System.out.println("elevator: " + en1.get());

			print += 1000;
		}

		if (lBot.get() == true) {
			en1.reset();
			elev.enable();
			elev.setSetpoint(TOTEONE);
		}

		if(j3.getRawButton(1)){
			elev.disable();
			t3.set(j3.getY());
			
		
		}
		if (j2.getRawButton(1) == true) {
			OD.set(Value.kReverse);
			oT.set(j2.getX() / 2);
			t1.set(0);
			t2.set(0);

			if (j1.getRawButton(1)) {
				OD.set(Value.kReverse);
				oT.set(j2.getX());
				t1.set(0);
				t2.set(0);

			}

		} else {
			OD.set(Value.kForward);
			oT.set(0);
			t1.set(j1.getY() * -1);
			t2.set(j2.getY());
		}

		if (j3.getRawButton(2) == true) {
			claw.set(Value.kReverse);
		} else {
			claw.set(Value.kForward);
		}

		if (j3.getRawButton(1) == true) {
			brake.set(Value.kForward);
		} else {
			brake.set(Value.kReverse);
		}

		if (j4.getRawButton(1)) {
			elev.setSetpoint(TOTEONE);
			if (en1.get() == TOTEONE) {
				claw.set(Value.kReverse);
			} else {
				claw.set(Value.kForward);
			}
		}
		if (j4.getRawButton(2)) {
			elev.setSetpoint(TOTETWO);
			if (en1.get() == TOTETWO) {
				claw.set(Value.kReverse);
			} else {
				claw.set(Value.kForward);
			}
		}
		if (j4.getRawButton(3)) {
			elev.setSetpoint(TOTETHREE);
			if (en1.get() == TOTETHREE) {
				claw.set(Value.kReverse);
			} else {
				claw.set(Value.kForward);
			}
		}
		if (j4.getRawButton(4)) {
			elev.setSetpoint(TOTEFOUR);
			if (en1.get() == TOTEFOUR) {
				claw.set(Value.kReverse);
			} else {
				claw.set(Value.kForward);
			}
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
