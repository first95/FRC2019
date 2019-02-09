package frc.robot.components;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CurrentDetectedTalon extends AdjustedTalon {
	public boolean query = false;
	private double cur = 0; // current
	private double cst = 0; // current start time
	private boolean last = false; // most recent status -- 0 is below threshold and 1 is at or above
	private boolean detect = false; // detection of high current for sufficient continuous time
	private double curThresh;
	private double minTimeMs;

	public CurrentDetectedTalon(int deviceNumber, double currentThresh, double minimumTimeMs) {
		super(deviceNumber);
		curThresh = currentThresh;
		minTimeMs = minimumTimeMs;
	}

	/**
	 * Get the current detection status
	 */
	public boolean getDetect() {
		return detect;
	}

	public void visit() {
		if (query) {
			updateStatus();
		}
	}

	/**
	 * Update the current and detection status
	 */		
	public void updateStatus() {
		cur = super.getOutputCurrent();
		if (cur >= curThresh) {
			// Went from below to above threshold so set time
			// Note: If System.nanoTime() doesn't work, try Timer.getFPGATimestamp() from WPILib
			if (!last) { cst = System.nanoTime()*1000*1000; }
			if (!detect) {
				// Check if detection time has elapsed
				if (System.nanoTime()*1000*1000 - cst >= minTimeMs) {
					detect = true;
				}
			} 
			last = true;
		} else {
			// Current is below detection level so re-set detect and cst
			if (last) { detect = false; }
			last = false;
		}
		//SmartDashboard.putNumber("Current (A)",cur);
		//SmartDashboard.putNumber("High Current Detected",detect ? 1: 0);
	}

}