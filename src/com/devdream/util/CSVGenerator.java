package com.devdream.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.devdream.controller.SeasonGameController;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;

/**
 * Generates a CSV of a season game or all season games.
 * 
 * @author Asier Gonzalez
 */
public class CSVGenerator {
	public static final String SEPARATOR = ",";

	private ArrayList<SeasonGame> seasons;
	private FileWriter fw;

	public CSVGenerator(ArrayList<SeasonGame> seasons) throws IOException {
		File f = new File("C:/dev/workspace/wales/java/Tema11Project/filePath.csv");
		fw = new FileWriter(f);
		this.seasons = seasons;
	}

	public void generate() throws IOException {
		// Columns
		fw.write("Season date"+SEPARATOR+"Home team"+SEPARATOR+"Away team"
				+SEPARATOR+"HGoals"+SEPARATOR+"AGoals"+SEPARATOR+"HShots"+SEPARATOR
				+"AShots"+SEPARATOR+"HPasses"+SEPARATOR+"APasses"+SEPARATOR+"HFouls"+SEPARATOR
				+ "AFouls"+SEPARATOR+"HOffsides"+SEPARATOR+"AOffsides"+SEPARATOR+"HCorners"
				+SEPARATOR+"ACorners"+SEPARATOR+"\n");
		// Content
		for (int i = 0, size = seasons.size(); i < size; ++i) {
			SeasonGame s = seasons.get(i);
			SeasonGameController controller = new SeasonGameController(s);
			Team ht = controller.getHomeTeam();
			Team at = controller.getAwayTeam();
			String line = s.getDate() + SEPARATOR;
			line += ht.getName() + SEPARATOR;
			line += at.getName() + SEPARATOR;
			line += ht.getScore() + SEPARATOR;
			line += at.getScore() + SEPARATOR;
			line += ht.getShots() + SEPARATOR;
			line += at.getShots() + SEPARATOR;
			line += ht.getPasses() + SEPARATOR;
			line += at.getPasses() + SEPARATOR;
			line += ht.getFouls() + SEPARATOR;
			line += at.getFouls() + SEPARATOR;
			line += ht.getOffsides() + SEPARATOR;
			line += at.getOffsides() + SEPARATOR;
			line += ht.getCorners() + SEPARATOR;
			line += at.getCorners() + SEPARATOR;
			line += "\n";
			fw.write(line);
		}
	}
	
	public void close() throws IOException {
		fw.flush();
		fw.close();
	}

}
