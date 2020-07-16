package com.propra.HealthAndSaftyBriefing.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.propra.HealthAndSaftyBriefing.printer.FormDoc;
import com.propra.HealthAndSaftyBriefing.printer.PrintData;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route("PrintView")
@PageTitle("Druckansicht | Sicherheitsunterweisung")
@SuppressWarnings("serial")
public class PrintView extends Div implements HasUrlParameter<String>{
	private ByteArrayInputStream inStream;
	private PersonManager personM;
	public PrintView() {
		personM = new PersonManager();
	}
	
	public InputStream getInputStream() {
		return inStream;
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Person person = personM.getPersonByID(Integer.parseInt(parameter));
		String lName = person.getLName();
		String fName = person.getFName();
		String date = "";
		if(person.getDate() != null) {
			date = df.format(person.getDate());
		}
		String ifwt = person.getIfwt();
		String mnaf = person.getMNaF();
		String intern = person.getIntern();
		String extern = person.getExtern();
		String genInstr = person.getGenInstr();
		String labComment = person.getLabComment();
		String labSetup = person.getLabSetup();
		String dangerSubstComment = person.getDangerSubstComment();
		String dangerSubsts = person.getDangerSubsts();
		
		if(!labComment.isEmpty()) {
			labSetup = labComment+"\n"+labSetup;
		}
		if(!dangerSubstComment.isEmpty()) {
			dangerSubsts = dangerSubstComment+"\n"+dangerSubsts;
		}
			
		//setup of printData
		PrintData printData = new PrintData(
					lName,
					fName,
					date,
					ifwt,
					mnaf,
					intern,
					extern,
					genInstr,
					labSetup,
					dangerSubsts
				);
		try {
			FormDoc doc = new FormDoc(printData);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.save(out);
			doc.close();
			inStream = new ByteArrayInputStream(out.toByteArray());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		add(new EmbeddedPdfDocument(new StreamResource("print.pdf", () -> getInputStream())));
		setHeight("100%");
	}
}
