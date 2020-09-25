import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class SoundGenerator {
	public static void main(String[] args) {
		try {
			// Handle base errors
			if (args.length == 0) {
				throw new Exception();
			}
			File readFile = new File(args[0]);
			if (!readFile.exists()) {
				throw new Exception();
			}
			
			// General Setup things
			Random r = new Random(readFile.getName().hashCode());
			Hashtable<String, Integer> key = new Hashtable<String, Integer>();
	        String contents = new String(Files.readAllBytes(readFile.toPath()), StandardCharsets.UTF_8);
			contents.replaceAll("[\\s\n]+", " ");
			FileWriter out = new FileWriter(readFile.getName() + "Parsed");
	        
	        // Variables Setting
	        String output = "";
		    int volume = 80;
		    int bpm = r.nextInt(151) + 50;
		    ArrayList<Double> noteTypes = new ArrayList<Double>();
		    noteTypes.add(2.0);
		    noteTypes.add(1.0);
		    noteTypes.add(.5);
		    noteTypes.add(.25);
		    noteTypes.add(.125);
		    double noteType = noteTypes.get(r.nextInt(5));
	        
	        // Actual code for sound and parsing
	        /** 
	         * . , " = change note type
	         * : ; ! ( = change tempo
	         * ) ? ' = change volume
	         * */
	        for(String word : contents.split("[ ]+")) {
	        	if (word.contains(",") || word.contains(".") || word.contains("\"")
	        		|| word.contains("“")) {
	        		word = word.replaceAll("[,.\"“]", "");
	        		noteType = noteTypes.get(r.nextInt(5));
	        	}
	        	if (word.contains(":") || word.contains(";") || word.contains("!")
	        			|| word.contains("(")) {
	        		word = word.replaceAll("[:;!(]", "");
	        		bpm = r.nextInt(151) + 50;
	        	}
	        	if (word.contains(")") || word.contains("?") || word.contains("'")) {
	        		word = word.replaceAll("[)?']", "");
	        		volume = r.nextInt(128);
	        	}
	        	word = word.toLowerCase();
	        	word.replaceAll("\n", "");
	        	word.replaceAll("\\r$", "");
	        	System.out.println(word);
	        	if (!key.containsKey(word)) {
	        		key.put(word, r.nextInt(129));
	        	}
	        	output += key.get(word) + " " + volume + " "
	        			+ 60000.0 / bpm * noteType + "\n";
	        }
	        out.write(output);
	        out.close();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
