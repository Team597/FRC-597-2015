package Elevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Elevator {

	Talon talonElevator;
	Encoder encoderElevator;
	PIDController elevator;
	Joystick xboxGamepad;
	DigitalInput topSwitch;
	DigitalInput botSwitch;
	DoubleSolenoid brake;
	long elevatorprint = System.currentTimeMillis();

	int elevatorState = 1;

	double ELEVATORTALON_BOTSWITCH_FALSE = -0.75;
	double ELEVATORTALON_BOTSWITCH_TRUE = 0.2;

	double HEIGHT_AUTONOMOUS = 2.3;
	double HEIGHT_BOTTOM = 0.0;
	double HEIGHT_TOTEONE = 3.0;
	double HEIGHT_TOTETWO = 5.0;
	double HEIGHT_TOTETHREE = 7.0;
	double HEIGHT_TOTEFOUR = 9.0;
	double HEIGHT_TOP = 11.0;

	public Elevator() {
		talonElevator = new Talon(4);
		xboxGamepad = new Joystick(2);
		encoderElevator = new Encoder(0, 1);
		topSwitch = new DigitalInput(2);
		botSwitch = new DigitalInput(3);
		brake = new DoubleSolenoid(6, 7);

		elevator = new PIDController(0, 0, 0.1, encoderElevator, talonElevator);
	}

	public void autoDrop() {
		elevator.setSetpoint(HEIGHT_BOTTOM); // lowers elevator for autonomous

		if (encoderElevator.get() == HEIGHT_BOTTOM) {
			brake.set(Value.kForward);
		} else {
			brake.set(Value.kReverse);
		}
	}

	public void autoLift() {
		elevator.setSetpoint(HEIGHT_AUTONOMOUS); // lifts elevator for
													// autonomous
		if (encoderElevator.get() == HEIGHT_AUTONOMOUS) {
			brake.set(Value.kForward);
		} else {
			brake.set(Value.kReverse);
		}
	}

	public void elevatorPrint() {
		if (System.currentTimeMillis() >= elevatorprint) {
			System.out.println("top switch: " + topSwitch.get());
			System.out.println("bot switch: " + botSwitch.get());
			System.out.println("Elevator encoder: " + encoderElevator.get());
			System.out.println(" Elevator" + elevator.get());

			elevatorprint += 1000;
		}
	}

	public void robotInit() {

		elevator.enable();

	}
	public void teleopPeriodic() {

		if (xboxGamepad.getRawButton(1) == true) {
			elevator.setSetpoint(HEIGHT_BOTTOM); // sets pid to move to base
													// position
			if (encoderElevator.get() == HEIGHT_BOTTOM) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}
		if (xboxGamepad.getRawButton(2) == true) {
			elevator.setSetpoint(HEIGHT_TOTEONE); // sets pid to move to tote 1
													// position
			if (encoderElevator.get() == HEIGHT_TOTEONE) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kForward);
			}

		}
		if (xboxGamepad.getRawButton(3) == true) {
			elevator.setSetpoint(HEIGHT_TOTETWO); // sets pid to move to tote 2
													// position
			if (encoderElevator.get() == HEIGHT_TOTETWO) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}
		if (xboxGamepad.getRawButton(4) == true) {
			elevator.setSetpoint(HEIGHT_TOTETHREE); // sets pid to move to tote
													// 3 position
			if (encoderElevator.get() == HEIGHT_TOTETHREE) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}
		if (xboxGamepad.getRawAxis(2) > 0) {
			elevator.setSetpoint(HEIGHT_TOTEFOUR); // sets pid to move to tote 4
													// position
			if (encoderElevator.get() == HEIGHT_TOTEFOUR) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}
		if (xboxGamepad.getRawAxis(3) > 0) {
			elevator.setSetpoint(HEIGHT_TOP); // sets pid to move to top
												// position
			if (encoderElevator.get() == HEIGHT_TOP) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}
		if (xboxGamepad.getRawButton(1) && xboxGamepad.getRawButton(2)) {
			elevator.disable();
		}
		if (xboxGamepad.getRawAxis(3) > 0 && xboxGamepad.getRawAxis(2) > 0) {
			elevator.enable();
		}

		if (topSwitch.get() == true) {
			elevator.setSetpoint(HEIGHT_TOTEFOUR); // if top switch is pressed
													// lowers elevator
			if (encoderElevator.get() == HEIGHT_TOTEFOUR) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}
		if (botSwitch.get() == true) {
			elevator.setSetpoint(HEIGHT_TOTEONE); // if bot switch is pressed
													// lifts elevator
			if (encoderElevator.get() == HEIGHT_TOTEONE) {
				brake.set(Value.kForward);
			} else {
				brake.set(Value.kReverse);
			}
		}

		if (xboxGamepad.getRawButton(1) == true && botSwitch.get() == false
				&& topSwitch.get() == false) {
			elevator.disable(); // if button A is pressed and no switches are
								// being pressed it changes to manual control
			talonElevator.set(xboxGamepad.getY()); // change to another joystick
													// for better control
		} else {
			elevator.enable(); // when the conditions are not met enables the
								// PID Controller
		}

	}
}
