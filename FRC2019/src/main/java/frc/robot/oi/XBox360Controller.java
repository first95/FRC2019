package frc.robot.oi;

public class XBox360Controller {
    public enum Axis {
        LEFT_STICK_X(0), // rest=0.0, left = -1.0, right=1.0
        LEFT_STICK_Y(1), // rest=0.0, forward = -1.0, backward=1.0
        LEFT_TRIGGER(2), // rest = 0.0, full trigger pull = 1.0
        RIGHT_TRIGGER(3), // rest=0.0, left = -1.0, right=1.0
        RIGHT_STICK_X(4), // rest = 0.0, full trigger pull = 1.0
        RIGHT_STICK_Y(5), // rest=0.0, forward = -1.0, backward=1.0

        ;
        private final int axisValue;

        private Axis(int value) {
            this.axisValue = value;
        }

        public int Number() {
            return axisValue;
        }
    }
    
    public enum Button {
        A(1),
        B(2),
        X(3),
        Y(4),

        LEFT_BUMPER(5),
        RIGHT_BUMPER(6),
        BACK(7),
        START(8),

        LEFT_STICK_PRESS(9),
        RIGHT_STICK_PRESS(10),
        ;
        private final int buttonNumber;

        private Button(int value) {
            this.buttonNumber = value;
        }

        public int Number() {
            return buttonNumber;
        }
    }

    // POV/DPAD on the weapons controller || IT IS IN DEGREES!
	public static final int POV_NONE = -1; // No DPAD button pressed
	public static final int POV_UP = 0;
	public static final int POV_UP_RIGHT = 45;
	public static final int POV_RIGHT = 90;
	public static final int POV_RIGHT_DOWN = 135;
	public static final int POV_DOWN = 180;
	public static final int POV_DOWN_LEFT = 225;
	public static final int POV_LEFT = 270;
	public static final int POV_LEFT_UP = 315;


    
}