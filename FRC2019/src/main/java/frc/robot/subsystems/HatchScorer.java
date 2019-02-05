package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.hscorer.ManuallyControlHatchScorer;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls mechanism to score hatches.
 */
public class HatchScorer extends Subsystem {

  // The solenoids for the cylinders that open the scorer and that push the scorer
  // Named to allow for future possibility of adding one more solenoid per motion
  // to have slow/fast options
  private Solenoid openA, pushA;

  public HatchScorer() {
    // False means closed and true means open so default is closed when robot off
    openA = new Solenoid(Constants.HS_OPEN_A);

    // False means retracted, and true means extended
    pushA = new Solenoid(Constants.HS_PUSH_A);

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManuallyControlHatchScorer());
  }

  public void openHS(boolean open) {
    openA.set(open);
  }

  public void toggleOpenHS() {
    openA.set(!openA.get());
  }

  public void pushHS(boolean push) {
    pushA.set(push);
  }

  public void togglePushHS() {
    pushA.set(!pushA.get());
  }
}
