package simpol.utils;

import java.io.File;
import java.io.IOException;

public class SIMFile {

	private File file;
	private String source;
	private int[] linesLength;

	public SIMFile(File file) {
		this.file = file;
	}

	public void load() throws IOException {
		source = FileUtils.loadFileInString(file);

		String[] lines = source.split("\\n");
		linesLength = new int[lines.length];
		for (int i = 0; i < lines.length; i++)
			linesLength[i] = lines[i].length() + 1;

	}

	public int getLineNumber(int absPosition) {
		// String tmp = source.substring(0, absPosition);
		// tmp = tmp.replace("\n", "");

		int sum = 0;
		for (int i = 0; i < linesLength.length; i++) {
			if (absPosition > sum && absPosition < sum + linesLength[i])
				return i + 1;
			sum += linesLength[i];
		}
		return -1;
	}

	public int getPositionInLine(int absPosition) {
		int sum = 0;
		for (int i = 0; i < linesLength.length; i++) {
			if (absPosition > sum && absPosition < sum + linesLength[i])
				return (absPosition - sum) + 1;
			sum += linesLength[i];
		}
		return -1;
	}

	public String getSource() {
		return source;
	}
}
