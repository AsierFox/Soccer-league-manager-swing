package com.devdream.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;

import com.devdream.ui.View;

/**
 * This class uploads an image to the project directory.
 * 
 * @author Asier Gonzalez
 */
public class UploadImage {

	private final String FILE_PATH;
	private final String FILE_NAME;
	
	public UploadImage(final String FILE_PATH) {
		this.FILE_PATH = FILE_PATH;
		FILE_NAME = StringHelper.getFileNameFromPath(FILE_PATH);
	}
	
	/**
	 * Uploads the image and returns the image final name.
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String uploadImage() throws IOException, URISyntaxException {
		saveResource(new File(FILE_PATH), new File("assets" + View.ImagePath.LOGOS, FILE_NAME)); // TODO PATH
		return FILE_NAME;
	}
	
	/**
	 * I choose the FileChannels to save the image due to images are considered large files and
	 * FileChannels are the best way to copy large files.
	 * @param source The source file path
	 * @param dest The destination file path
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static void saveResource(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

}
