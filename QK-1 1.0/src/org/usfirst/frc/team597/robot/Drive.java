package org.usfirst.frc.team597.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

public class Drive {
	Joystick jsLeft;
	Joystick jsRight;
	Talon talonLeft;
	Talon talonRight;
	Talon talonStrafe;
	DoubleSolenoid strafePiston;
	Gyro gyro = new Gyro(0);
	StrafeComp strafeComp;
	PIDController strafePID;
	double gyroSetpoint;
	int mode;

	public Drive() {
		jsLeft = new Joystick(0);
		jsRight = new Joystick(1);
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
		talonStrafe = new Talon(2);
		strafePiston = new DoubleSolenoid(2, 7);
		strafeComp = new StrafeComp(talonLeft, talonRight);
		strafePID = new PIDController(1 / 45.0, 0, 0, gyro, strafeComp);
		gyroSetpoint = 0;
		mode = 0;
	}

	public void tankDrive() {
		if (jsRight.getRawButton(7) == false) {
			strafePiston.set(Value.kReverse);
			talonLeft.set(jsLeft.getY());
			talonRight.set(jsRight.getY());
		} 
		else if (jsRight.getRawButton(7)) {
			strafe();
		}

	}

	public void strafe() {
		strafePiston.set(Value.kForward);
		compensate();
		talonStrafe.set(jsRight.getX() / 2);
		if (jsLeft.getRawButton(7)) {
			talonStrafe.set(jsRight.getX());
		}
	}

	public void compensate() {
		gyroSetpoint = gyro.getAngle();
		strafePID.enable();
		strafePID.setSetpoint(gyroSetpoint);
	}

}
