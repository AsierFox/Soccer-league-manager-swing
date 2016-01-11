package com.devdream.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.devdream.controller.Controller;
import com.devdream.controller.SeasonGameController;
import com.devdream.exception.OperationCancelledException;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Generates a PDF of a season game using iText-pdf.
 * 
 * @author Asier Gonzalez
 */
public class PDFBuilder {
	public static final String FILE_EXT = ".pdf";
	
	private static String FILE_SAVE_PATH;
	
	private static Font sBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font sSubFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font sSmallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	private SeasonGame season;
	private Team homeTeam;
	private Team awayTeam;
	private Team winnerTeam;
	
	private Document document;
	private Chapter chapter;
	
	public PDFBuilder() throws OperationCancelledException {
		FILE_SAVE_PATH = Alert.showFileChooser(FILE_EXT);
	}
	
	public PDFBuilder(SeasonGame season) throws FileNotFoundException, DocumentException, OperationCancelledException {
		this();
		SeasonGameController controller = new SeasonGameController(season);
		this.season = season;
		homeTeam = controller.getHomeTeam();
		awayTeam = controller.getAwayTeam();
		winnerTeam = season.getGame().getWinnerTeam();
		
		document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(FILE_SAVE_PATH));
		chapter = new Chapter(1);
	}

	public void generate() throws FileNotFoundException, DocumentException {
		document.open();
		
		addContent();
	}
	
	private void addContent() throws DocumentException {
		// Title
		Paragraph preface = new Paragraph();
	    preface.add(new Paragraph("Season " + season.getDate() + "  :   " + homeTeam.getName()
	    		+ " vs. " + awayTeam.getName(), sBoldFont));
	    addEmptyLine(preface, 0);
	    preface.add(new Paragraph("Report generated by: " + Controller.getLoggedUser().getFirstName() + ", "
	    		+ DateHelper.getCurrentDate(), sSmallBold));
	    
	    addEmptyLine(preface, 1);
	    
		chapter.add(preface);

		// Game teams
		Paragraph teams = new Paragraph();
		teams.add(new Paragraph("Teams", sSubFont));
		teams.add(new Paragraph("Home Team: " + homeTeam.getName() + (homeTeam.isUserTeam() ? " (Your team)" : "")));
		teams.add(new Paragraph("Away Team: " + awayTeam.getName() + (awayTeam.isUserTeam() ? " (Your team)" : "")));
		addEmptyLine(teams, 1);

		chapter.add(teams);
		
		// Winner
		Paragraph result = new Paragraph();
		
		result.add(new Paragraph("Winner", sSubFont));
		result.add(new Paragraph(winnerTeam == null ? "Draw" : winnerTeam.getName()));
		addEmptyLine(result, 1);
		
		chapter.add(result);
		
		// Result
		Paragraph score = new Paragraph();
		score.add(new Paragraph("Result", sSubFont));
		score.add(new Paragraph(homeTeam.getName() + "  | " + homeTeam.getScore()
				+ "   -   " + awayTeam.getScore() + " |  " + awayTeam.getName()));
		addEmptyLine(score, 1);
		
		chapter.add(score);
		
		// Performances table
		Paragraph performances = new Paragraph();
		performances.add(new Paragraph("Game performances", sSubFont));
		addEmptyLine(performances, 1);
		
		chapter.add(performances);
		
		createPerformancesTable();
		
		document.add(chapter);
	}

	private void createPerformancesTable() throws BadElementException {
		PdfPTable table = new PdfPTable(3);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Actions"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Home team (" + homeTeam.getName() + ")"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Away team (" + awayTeam.getName() + ")"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		table.addCell(c1);
		table.setHeaderRows(1);
		
		table.addCell("Shots");
		table.addCell(homeTeam.getShots()+"");
		table.addCell(awayTeam.getShots()+"");
		
		table.addCell("Passes");
		table.addCell(homeTeam.getPasses()+"");
		table.addCell(awayTeam.getPasses()+"");
		
		table.addCell("Fouls");
		table.addCell(homeTeam.getFouls()+"");
		table.addCell(awayTeam.getFouls()+"");
		
		table.addCell("Offsides");
		table.addCell(homeTeam.getOffsides()+"");
		table.addCell(awayTeam.getOffsides()+"");
		
		table.addCell("Corners");
		table.addCell(homeTeam.getCorners()+"");
		table.addCell(awayTeam.getCorners()+"");
		
		chapter.add(table);
	}
	
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) paragraph.add(new Paragraph(" "));
	}
	
	public void close() {
		document.close();
	}

}