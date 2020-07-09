package com.propra.HealthAndSaftyBriefing.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.StreamResource;

public class DBExporter {
	
	public AbstractStreamResource getResource() {
		return new StreamResource("CoreDatabase.db", this::createExporter);
	}
	
	private InputStream createExporter() {
		try {
			File initialFile = new File("src/main/resources/database/CoreDatabase.db");
			InputStream fileStream = new FileInputStream(initialFile);
			return fileStream;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
