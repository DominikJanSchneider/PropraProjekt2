package com.propra.HealthAndSaftyBriefing.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.upload.Receiver;

public class DBUploader implements Receiver {
	
	private static final long serialVersionUID = 1L;
	private File file;
	private String path = "src/main/resources/upload/";
	
	@Override
	public FileOutputStream receiveUpload(final String filename, final String MIMEType) {
		try {
			if (file == null) {
				file = new File(filename);
			}
			return new FileOutputStream(path+file, false) {
				
			};
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] getUploadedFiles() {
		String[] filenames;
		File uploadPath = new File(path);
		filenames = uploadPath.list();
		
		return filenames;
	}
}
