package com.devdream.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.devdream.util.CSVGenerator;
import com.devdream.util.PDFBuilder;
import com.devdream.util.StringHelper;
import com.itextpdf.text.DocumentException;

public class SeasonGame {

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
	public void exportToCSV() throws IOException {
		ArrayList<SeasonGame> season = new ArrayList<>();
		season.add(this);
		CSVGenerator csvGenerator = new CSVGenerator(season);
		csvGenerator.generate();
		csvGenerator.close();
	}
	
	public void exportToPDF() throws FileNotFoundException, DocumentException {
		PDFBuilder pdfBuilder = new PDFBuilder(this);
		pdfBuilder.generate();
		pdfBuilder.close();
	}
	
	public static void exportToCSV(ArrayList<SeasonGame> seasons) throws IOException {
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
		return StringHelper.isStringNull(date) || date.equals("null") ? "Not set" : date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
