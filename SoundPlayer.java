import javax.sound.midi.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class SoundPlayer {
	public static void main(String[] args) {
		try {
			// Midi Setup Things
	        Synthesizer synth = MidiSystem.getSynthesizer();
	        synth.open();
	        MidiChannel[] channels = synth.getChannels();
	        
	        // General Setup
	        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
	        while (true) {
	        	String line = reader.readLine();
	        	if (line == null) {
	        		break;
	        	}
	        	System.out.println(line);
	        	String[] splitLine = line.split(" ");
	        	if (Integer.parseInt(splitLine[0]) == 129) {
	        		Thread.sleep(Long.parseLong(splitLine[2]));
	        	} else {
	        		int note = Integer.parseInt(splitLine[0]);
	        		int volume = Integer.parseInt(splitLine[1]);
	        		double length = Double.parseDouble(splitLine[2]);
	        		channels[0].noteOn(note, volume);
			        Thread.sleep((long) length);
			        channels[0].noteOff(note);
	        	}
	        }
	        reader.close();
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
}
