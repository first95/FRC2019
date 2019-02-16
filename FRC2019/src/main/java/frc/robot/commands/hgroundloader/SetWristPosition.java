package frc.robot.commands.hgroundloader;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SetWristPosition extends Command {
        private double wristPosition;
        private boolean waitForWristToReachTarget;

        /**
         * 
         * @param position the target position to seek
         * @param wait     when true, this command waits until the wrist reaches the
         *                 target before reporting completion. When false, this command
         *                 sets the setpoint and then ends immediately.
         */
        public SetWristPosition(double position, boolean wait) {
                super();
                requires(Robot.hGroundLoader);
                wristPosition = position;
                waitForWristToReachTarget = wait;
                System.out.println("HGL SWP Constructed");
        }

        @Override
        public void start() {
                super.start();
                System.out.println("HGL SWP Start");
                Robot.hGroundLoader.setWristRot(wristPosition);
        }

        @Override
        protected void initialize() {
                super.initialize();
                System.out.println("HGL SWP initialize");

        }
        @Override
        protected void execute() {
                super.execute();
                System.out.println("HGL SWP execute");
        }

        @Override
        protected boolean isFinished() {
                boolean finished =  (!waitForWristToReachTarget) || Robot.hGroundLoader.isWristPositionOnTarget();
                System.out.println("HGL SWP finished? " + (finished? "True":"False"));
                return finished;
        }

}