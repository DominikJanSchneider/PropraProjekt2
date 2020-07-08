package com.propra.HealthAndSaftyBriefing.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@SuppressWarnings("serial")
@Tag("object")
public class EmbeddedPdfDocument extends Component implements HasSize {
	public EmbeddedPdfDocument(StreamResource resource) {
		this();
		getElement().setAttribute("data", resource);
	}
	
	public EmbeddedPdfDocument(String url) {
		this();
		getElement().setAttribute("data", url);
	}
	
	protected EmbeddedPdfDocument() {
		getElement().setAttribute("type", "application/pdf");
	    setSizeFull();
	}
}
