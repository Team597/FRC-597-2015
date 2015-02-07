package Elevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Elevator {

	Talon talonElevator;
	Encoder encoderElevator;
	PIDController elevator;
	Joystick joystickElevator;
	DigitalInput topSwitch;
	DigitalInput botSwitch;
	
	int elevatorState = 1;

	public Elevator() {
		talonElevator = new Talon(3);
		joystickElevator = new Joystick(3);
		encoderElevator = new Encoder(0, 1);
		topSwitch = new DigitalInput(2);
		botSwitch = new DigitalInput(3);
		
		elevator = new PIDController(0, 0, 0.1, encoderElevator, talonElevator);
	}

	public void autoDrop() {

		elevator.setSetpoint(0.0); // lowers elevator for autonomous
	}

	public void autoLift() {
		elevator.setSetpoint(2.3); // lifts elevator for autonomous
	}

	public void robotInit() {

		elevatorState = 1; // elevator state is set to 1
		if (elevatorState == 1) { 
			if ( botSwitch.get() == false) { 
				talonElevator.set(-0.75);// if switch state is 1 and bot switch is not true lowers elevator
			}
			if ( botSwitch.get() == true) {
				talonElevator.set(0); // if bot switch state is pressed stops elevator
				
				talonElevator.set(0.2);
				Timer.delay(0.5); // lifts elevator
				 
				encoderElevator.reset(); //resets encoder to zero
				elevatorState = 2; // changes elevator state to 2
			}

		}
		if (elevatorState == 2) {
			elevator.enable(); // if switch state is 2 enables PID Controller
		}
	}

	public void teleopPeriodic() {

		if (joystickElevator.getRawButton(2) == true) {
			elevator.setSetpoint(0.0); // sets pid to move to base position
		}
		if (joystickElevator.getRawButton(3) == true) {
			elevator.setSetpoint(3.0); // sets pid to move to tote 1 position
		}
		if (joystickElevator.getRawButton(4) == true) {
			elevator.setSetpoint(5.0); // sets pid to move to tote 2 position
		}
		if (joystickElevator.getRawButton(1) == true) {
			elevator.setSetpoint(7.0); // sets pid to move to tote 3 position

		}
		if (joystickElevator.getRawButton(10) == true) {
			elevator.setSetpoint(9.0); // sets pid to move to tote 4 position
		}
		if (joystickElevator.getRawButton(11) == true) {
			elevator.setSetpoint(11.0); // sets pid to move to top position

		}

	
		if(topSwitch.get() == true){
			elevator.setSetpoint(9.0); // if top switch is pressed lowers elevator
		}
		if(botSwitch.get() == true){
			elevator.setSetpoint(3.0); // if bot switch is pressed lifts elevator
		}
		
		if(joystickElevator.getRawButton(7) == true && botSwitch.get() == false && topSwitch.get() == false){
			elevator.disable(); // if button 7 is pressed and no switches are being pressed it changes to manual control
			talonElevator.set(joystickElevator.getY());
		}
		else{
			elevator.enable(); // when the conditions are not met enables the PID Controller
		}
		
	}
}
