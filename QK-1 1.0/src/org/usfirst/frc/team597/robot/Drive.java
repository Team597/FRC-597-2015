package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	Gyro gyro;
	Joystick jsLeft;
	Joystick jsRight;
	Talon talonLeft;
	Talon talonRight;
	Talon talonOmni;
	DoubleSolenoid omniPiston;
	PIDController Omni;

	final Value OMNI_ON = Value.kReverse;
	final Value OMNI_OFF = Value.kForward;

	int omniState = 0;
	boolean lastGyroState = false;
	double gyroSetpoint = 0;

	public Drive(Gyro G, Joystick JL, Joystick JR, Talon TL, Talon TR,
			Talon TO, DoubleSolenoid OP, PIDController O) {
		gyro = G;
		jsLeft = JL;
		jsRight = JR;
		talonLeft = TL;
		talonRight = TR;
		talonOmni = TO;
		omniPiston = OP;
		Omni = O;
	}

	public void PRINT() {
		SmartDashboard.putNumber("gyro angle", gyro.getAngle());
	}

	public void teleopPeriodic() {
		// Enables omni(H) drive at half speed
		if (jsRight.getRawButton(7) == true) {
			omniPiston.set(OMNI_ON);
			if (lastGyroState != jsRight.getRawButton(7)) {
				gyroSetpoint = gyro.getAngle();
			}

			Omni.enable();
			Omni.setSetpoint(gyroSetpoint);

			if (jsLeft.getRawButton(7)) {
				omniState = 2;
			} else {
				omniState = 1;
			}
			if (omniState == 1) {
				talonOmni.set(jsRight.getX() / 2);
			}
			if (omniState == 2) {
				talonOmni.set(jsRight.getX());
			}
		}
		lastGyroState = jsRight.getRawButton(7);

		// Standard drive
		if (jsRight.getRawButton(7) == false) {
			Omni.disable();
			talonOmni.set(0);
			omniPiston.set(OMNI_OFF);
			talonLeft.set(jsLeft.getY() * 0.75);
			talonRight.set(jsRight.getY() * 0.75);
		}

	}
	
	public void move(double x, double y) {
		talonLeft.set(x);
		talonRight.set(x);
		Timer.delay(y);
	}
	public void strafe(double x, double y) {
		omniPiston.set(OMNI_ON);
		gyroSetpoint = gyro.getAngle();

		Omni.enable();
		Omni.setSetpoint(gyroSetpoint);
		
		talonOmni.set(x);
		Timer.delay(y);
	}

}
