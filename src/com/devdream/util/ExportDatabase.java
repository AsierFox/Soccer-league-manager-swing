package com.devdream.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.devdream.db.DBConnectionManager;

/**
 * This class export the database to a selected path.
 * 
 * @author Asier Gonzalez
 */
public class ExportDatabase {
	private final String DEST_PATH;
	
	public ExportDatabase(final String DESTINATION_PATH) {
		DEST_PATH = DESTINATION_PATH;
	}
	
	public void export() throws IOException {
		Files.copy(Paths.get(DBConnectionManager.DB_PATH + DBConnectionManager.DB_FILENAME),
				Paths.get(DEST_PATH));
	}

}
