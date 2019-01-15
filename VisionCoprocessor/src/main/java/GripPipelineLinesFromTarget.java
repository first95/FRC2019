// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

import edu.wpi.first.vision.VisionPipeline;

/**
* GripPipelineLinesFromTarget class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class GripPipelineLinesFromTarget implements VisionPipeline {
	public static final double MIN_LINE_LEN = 15;

	//Outputs
	private Mat hsvThresholdOutput = new Mat();
	private ArrayList<Line> findLinesOutput = new ArrayList<Line>();
	private LinkedList<Line> filterLines0Output = new LinkedList<Line>();
	private LinkedList<Line> filterLines1Output = new LinkedList<Line>();

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	@Override	public void process(Mat source0) {
		// Step HSV_Threshold0:
		Mat hsvThresholdInput = source0;
		double[] hsvThresholdHue = {45.69817278554671, 93.99989504410354};
		double[] hsvThresholdSaturation = {91.72661870503596, 255.0};
		double[] hsvThresholdValue = {57.32913669064751, 255.0};
		hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);

		// Step Find_Lines0:
		Mat findLinesInput = hsvThresholdOutput;
		findLines(findLinesInput, findLinesOutput);
		// System.out.println("Found " + findLinesOutput.size() + " lines.");

		// Step Filter_Lines0:
		ArrayList<Line> filterLines0Lines = findLinesOutput;
		double[] filterLines0Angle = {100, 125};
		filterLines0Output = new LinkedList<>();
		filterLines(filterLines0Lines, MIN_LINE_LEN, filterLines0Angle, filterLines0Output);
		// System.out.println("Found " + filterLines0Output.size() + " lines angled left.");

		// Step Filter_Lines1:
		ArrayList<Line> filterLines1Lines = findLinesOutput;
		double[] filterLines1Angle = {55, 80};
		filterLines1Output = new LinkedList<>();
		filterLines(filterLines1Lines, MIN_LINE_LEN, filterLines1Angle, filterLines1Output);
		// System.out.println("Found " + filterLines1Output.size() + " lines angled right.");

	}

	/**
	 * This method is a generated getter for the output of a HSV_Threshold.
	 * @return Mat output from HSV_Threshold.
	 */
	public Mat hsvThresholdOutput() {
		return hsvThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a Find_Lines.
	 * @return ArrayList<Line> output from Find_Lines.
	 */
	public ArrayList<Line> findLinesOutput() {
		return findLinesOutput;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Lines.
	 * @return LinkedList<Line> output from Filter_Lines.
	 */
	public LinkedList<Line> filterLines0Output() {
		return filterLines0Output;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Lines.
	 * @return ArrayList<Line> output from Filter_Lines.
	 */
	public LinkedList<Line> filterLines1Output() {
		return filterLines1Output;
	}


	/**
	 * Segment an image based on hue, saturation, and value ranges.
	 *
	 * @param input The image on which to perform the HSL threshold.
	 * @param hue The min and max hue
	 * @param sat The min and max saturation
	 * @param val The min and max value
	 * @param output The image in which to store the output.
	 */
	private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
	    Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
		Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
			new Scalar(hue[1], sat[1], val[1]), out);
	}

	public static class Line {
		public final double x1, y1, x2, y2;
		public Line(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		public double lengthSquared() {
			return Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
		}
		public double length() {
			return Math.sqrt(lengthSquared());
		}
		public double angle() {
			return Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
		}
		public Point startPoint() {
			return new Point(x1, y1);
		}
		public Point endPoint() {
			return new Point(x2, y2);
		}
		@Override
		public String toString() {
			return String.format("(%.1f,%.1f)-(%.1f,%.1f)", x1, y1, x2, y2);
		}
	}
	/**
	 * Finds all line segments in an image.
	 * @param input The image on which to perform the find lines.
	 * @param lineList The output where the lines are stored.
	 */
	private void findLines(Mat input, ArrayList<Line> lineList) {
		final LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
		final Mat lines = new Mat();
		lineList.clear();
		if (input.channels() == 1) {
			lsd.detect(input, lines);
		} else {
			final Mat tmp = new Mat();
			Imgproc.cvtColor(input, tmp, Imgproc.COLOR_BGR2GRAY);
			lsd.detect(tmp, lines);
		}
		if (!lines.empty()) {
			for (int i = 0; i < lines.rows(); i++) {
				lineList.add(new Line(lines.get(i, 0)[0], lines.get(i, 0)[1],
					lines.get(i, 0)[2], lines.get(i, 0)[3]));
			}
		}
	}

	/**
	 * Filters out lines that do not meet certain criteria.
	 * @param inputs The lines that will be filtered.
	 * @param minLength The minimum length of a line to be kept.
	 * @param angle The minimum and maximum angle of a line to be kept.
	 * @param outputs The output lines after the filter.
	 */
	private void filterLines(List<Line> inputs,double minLength,double[] angle,
		List<Line> outputs) {
		double minLenSquared = Math.pow(minLength,2);
		// outputs = new LinkedList<Line>();
		for(Line line : inputs) {
			if(line.lengthSquared() >= minLenSquared) {
				if((line.angle() >= angle[0] && line.angle() <= angle[1])
				|| (line.angle() + 180.0 >= angle[0] && line.angle() + 180.0 <= angle[1])) {
					// System.out.println("Accepted line");
					outputs.add(line);
				} else {
					// System.out.println("Rejected line with angle " + line.angle() + " outside of " + angle[0] + ", " + angle[1]);
				}
			}
		}
	}

	public static void main(String[] args) {
		// System.out.println("Hello world!");
		// FileOutputStream fs;
		// try {
		// 	fs = new FileOutputStream("temp.txt");
		// 	fs.write("hello".getBytes());
		// 	fs.close();	
		// } catch (FileNotFoundException e) {
		// 	e.printStackTrace();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

		String[] filesToProcess = {
			"test_images/19 inches.png",
			"test_images/29 inches.png",
			"test_images/near.png",
			"test_images/far.png",
		};

		Scalar unfilteredLineColor = new Scalar(255, 0, 0);
		Scalar leftLineColor = new Scalar(0, 255, 0);
		Scalar rightLineColor = new Scalar(0, 0, 255);
		int lineWidth = 1;
	
		GripPipelineLinesFromTarget processor = new GripPipelineLinesFromTarget();
		for (String file : filesToProcess) {
			Mat img = Imgcodecs.imread(file);
			processor.process(img);
			for(GripPipelineLinesFromTarget.Line line : processor.findLinesOutput()) {
				Imgproc.line(img, line.startPoint(), line.endPoint(), unfilteredLineColor, lineWidth);
			}
			for(GripPipelineLinesFromTarget.Line line : processor.filterLines0Output()) {
				Imgproc.line(img, line.startPoint(), line.endPoint(), leftLineColor, lineWidth);
			}
			for(GripPipelineLinesFromTarget.Line line : processor.filterLines1Output()) {
				Imgproc.line(img, line.startPoint(), line.endPoint(), rightLineColor, lineWidth);
			}
			HighGui.imshow(file, img);
			System.out.println(file + " has " + processor.filterLines0Output().size() + " left side lines: " + processor.filterLines0Output());
			System.out.println(file + " has " + processor.filterLines1Output().size() + " right side lines: " + processor.filterLines1Output());
		}
		HighGui.waitKey(10);
	}
}

