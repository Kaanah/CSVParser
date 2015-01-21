import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

public class Parser {
	private String path;

	public Parser(String path) {
		this.path = path;
	}

	public void parse() {
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(path));
			try {
				List<String[]> entries = reader.readAll();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
