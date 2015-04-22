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
	
	Autonomous Auto;
	StrafeComp strafeComp;
	
	PIDController strafePID;
	double gyroSetpoint;
	int mode;
	boolean SHT = false;
	boolean gyroPoint = false;

	public Drive() {
		jsLeft = new Joystick(0);
		jsRight = new Joystick(1);
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
		talonStrafe = new Talon(2);
		strafePiston = new DoubleSolenoid(2, 7);

		Auto = new Autonomous(talonLeft, talonRight, talonStrafe, strafePiston);
		strafeComp = new StrafeComp(talonLeft, talonRight);
		
		strafePID = new PIDController(1 / 45.0, 0, 0, gyro, strafeComp);
		gyroSetpoint = 0;
		mode = 0;
	}

	public void tankDrive() {
		if (jsRight.getRawButton(7) == false) {
			strafePID.disable();
			strafePiston.set(Value.kReverse);
			talonLeft.set(jsLeft.getY());
			talonRight.set(jsRight.getY());
			if (SHT != jsRight.getRawButton(7)) {
				talonLeft.set(0);
				talonRight.set(0);
				talonStrafe.set(0);
			}
			SHT = jsRight.getRawButton(7);
		} else if (jsRight.getRawButton(7)) {
			if (SHT != jsRight.getRawButton(7)) {
				talonLeft.set(0);
				talonRight.set(0);
				talonStrafe.set(0);
			}
			SHT = jsRight.getRawButton(7);
			strafe();
		}

	}

	public void strafe() {
		strafePiston.set(Value.kForward);
		compensate();
		if (jsLeft.getRawButton(7)) {
			talonStrafe.set(jsRight.getX() / 2);
		}
		if (jsLeft.getRawButton(7)) {
			talonStrafe.set(jsRight.getX());
		}
	}
	
	public void autonomousPeriodic(){
		Auto.autonoNo(1);
	}
	
	public void compensate() {
		if (gyroPoint != jsRight.getRawButton(7)) {
			gyroSetpoint = gyro.getAngle();
		}
		gyroPoint = jsRight.getRawButton(7);
		strafePID.enable();
		strafePID.setSetpoint(gyroSetpoint);
	}

}