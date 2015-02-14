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
	Talon oT = new Talon(2);
	Talon t3 = new Talon(3);
	//Compressor comp = new Compressor();
	DoubleSolenoid claw = new DoubleSolenoid(0, 7);
	DoubleSolenoid brake = new DoubleSolenoid(1, 6);
	DoubleSolenoid OD = new DoubleSolenoid(2, 5);
	Encoder en1 = new Encoder(7, 8);
	Encoder MM = new Encoder(1, 2);
	Encoder LT = new Encoder(5, 6);
	Encoder RT = new Encoder(3, 4);
	PIDController elev = new PIDController(-1 / 100.0, 0, -.01, en1, t3);
	DigitalInput lBot = new DigitalInput(0);
	DigitalInput lTop = new DigitalInput(9);
	int eS = 1;
	long print = System.currentTimeMillis();

	boolean lastBotState = false;
	boolean lastTopState = false;

	// Add this to every setpoint to zero it to the robot
	// This will subtract a bunch if the encoder was zeroed to the top
	// And be zero if it was zeroed to the bottom.
	int ENCODER_OFFSET = 0;
	int DIFFERENCE_TOP_BOTTOM_ENCODER = 3813;

	int BASE = -67;
	int TOTEONE = 1000;
	int TOTETWO = 2000;
	int TOTETHREE = 2500;
	int TOTEFOUR = 3000;
	int TOP = 3800;

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
			System.out.println("Bottm limit switch hit: " + lBot.get());
			System.out.println("Top limit switch hit: " + lTop.get());

			print = System.currentTimeMillis() + 1000;
		}

		if (lBot.get() != lastBotState) {
			int error = en1.get() + DIFFERENCE_TOP_BOTTOM_ENCODER;
			
			en1.reset();
			ENCODER_OFFSET = 0;
			// elev.disable();
			System.out.println("Botswitch has been RESET :) Error top vs bottom: "+error);
		}
		lastBotState = lBot.get();

		/*if (lTop.get() != lastTopState) {
			en1.reset();
			elev.disable();
			ENCODER_OFFSET = -DIFFERENCE_TOP_BOTTOM_ENCODER;
			System.out.println("Topswitch has been RESET :)");
		}
		lastTopState = lTop.get();
*/
		if (j3.getRawButton(2)) {
			elev.disable();
			t3.set(j3.getY());
		} else {
			// t3.set(0);
			// 3833
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

		if (j3.getRawButton(7) == true) {
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
			elev.enable();
			elev.setSetpoint(TOTEONE + ENCODER_OFFSET);
		}

		if (j4.getRawButton(2)) {
			elev.enable();
			elev.setSetpoint(TOTETWO + ENCODER_OFFSET);
		}
		if (j4.getRawButton(3)) {
			elev.enable();
			elev.setSetpoint(TOTETHREE + ENCODER_OFFSET);
		}
		if (j4.getRawButton(4)) {
			elev.enable();
			elev.setSetpoint(TOTEFOUR + ENCODER_OFFSET);
		}
		if (j4.getRawAxis(2) > 0) {
			elev.enable();
			elev.setSetpoint(BASE + ENCODER_OFFSET);
		}
		if (j4.getRawAxis(3) > 0) {
			elev.enable();
			elev.setSetpoint(TOP + ENCODER_OFFSET);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
