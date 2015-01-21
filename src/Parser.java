import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;

public class Parser {
	private File file;

	public Parser(File file) throws InvalidFileFormatException{
		this.file = file;
		if(!getExtension(file).equals("csv"))
			throw new InvalidFileFormatException();
	}

	public List<String[]> parse() {
		CSVReader reader;
		List<String[]> entries = null;
		try {
			reader = new CSVReader(new FileReader(file));
			try {
				entries = reader.readAll();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public String getExtension(File f){
		String extension = "";
		String fileName = f.getName();
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		
		return extension;
	}
}
