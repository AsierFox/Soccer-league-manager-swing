package com.devdream.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.devdream.ui.View;

/**
 * This class uploads an image to the project directory.
 * 
 * @author Asier Gonzalez
 */
public class UploadImage {

	private final String FILE_PATH;
	private final String TARGET_PATH;
	
	public UploadImage(final String FILE_PATH) {
		this.FILE_PATH = FILE_PATH;
		TARGET_PATH = View.ImagePath.LOGOS;
	}
	
	public void copyFileToPath() throws IOException, URISyntaxException {
		Files.copy(new File(FILE_PATH).toPath(),
                new File("img/logo").toPath(), // TODO UPLOAD FILE
				StandardCopyOption.REPLACE_EXISTING);
	}

}
