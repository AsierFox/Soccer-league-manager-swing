package com.devdream.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.devdream.exception.OperationCancelledException;
import com.devdream.util.CSVGenerator;
import com.devdream.util.PDFBuilder;
import com.devdream.util.StringHelper;
import com.itextpdf.text.DocumentException;

/**
 * A SeasonGame represents a game of an specific seasons of a league,
 * contains the date of it and the game that happened on that season.
 * 
 * @author SkyFoXx
 */
public class SeasonGame {

	//
	// Global
	public final static String EMPTY_DATE = "Not set";
	
	//
	// Attributes
	private Game game;
	private String date;

	//
	// Constructors
	public SeasonGame(Game game, String date) {
		this.game = game;
		this.date = date;	
	}
	
	//
	// Methods
	public void exportToCSV() throws IOException, OperationCancelledException {
		ArrayList<SeasonGame> season = new ArrayList<>();
		season.add(this);
		CSVGenerator csvGenerator = new CSVGenerator(season);
		csvGenerator.generate();
		csvGenerator.close();
	}
	
	public void exportToPDF() throws FileNotFoundException, DocumentException, OperationCancelledException {
		PDFBuilder pdfBuilder = new PDFBuilder(this);
		pdfBuilder.generate();
		pdfBuilder.close();
	}
	
	public static void exportToCSV(ArrayList<SeasonGame> seasons) throws IOException, OperationCancelledException {
		CSVGenerator csvGenerator = new CSVGenerator(seasons);
		csvGenerator.generate();
		csvGenerator.close();
	}
	
	//
	// Getters and setters
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getDate() {
		return StringHelper.isStringNull(date) || date.equals("null") ? EMPTY_DATE : date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
