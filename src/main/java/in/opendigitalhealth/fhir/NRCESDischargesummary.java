package in.opendigitalhealth.fhir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Composition;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Timing;
import org.hl7.fhir.r4.model.Appointment.AppointmentStatus;
import org.hl7.fhir.r4.model.Appointment.ParticipationStatus;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CarePlan.CarePlanActivityComponent;
import org.hl7.fhir.r4.model.CarePlan.CarePlanIntent;
import org.hl7.fhir.r4.model.CarePlan.CarePlanStatus;
import org.hl7.fhir.r4.model.Composition.CompositionStatus;
import org.hl7.fhir.r4.model.Composition.DocumentConfidentiality;
import org.hl7.fhir.r4.model.Composition.SectionComponent;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus;
import org.hl7.fhir.r4.model.DocumentReference.DocumentReferenceContentComponent;
import org.hl7.fhir.r4.model.DocumentReference.ReferredDocumentStatus;
import org.hl7.fhir.r4.model.Encounter.EncounterHospitalizationComponent;
import org.hl7.fhir.r4.model.Encounter.EncounterStatus;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Enumerations.DocumentReferenceStatus;
import org.hl7.fhir.r4.model.MedicationRequest.MedicationRequestIntent;
import org.hl7.fhir.r4.model.MedicationRequest.MedicationRequestStatus;
import org.hl7.fhir.r4.model.Procedure.ProcedureStatus;
import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;
import org.hl7.fhir.r4.model.Timing.UnitsOfTime;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class NRCESDischargesummary {
public static void main(String[] args) throws ParseException {

////////////////////////////////////////////// Composition
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////

		Composition composition = new Composition();
		CodeableConcept cc = new CodeableConcept();
		cc.addCoding().setDisplay("Discharge summary").setCode("373942005").setSystem("http://snomed.info/sct");
		composition.setId("1");
		composition.setType(cc);
		// composition.setText(new Narrative().setDiv(value));
		composition.addAuthor(new Reference().setReference("Practitioner/1"));
		composition.setLanguage("en-IN");
		composition.setDate(new Date());
		composition.setStatus(CompositionStatus.FINAL);
		Meta meta = new Meta();
		meta.addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/DischargeSummaryRecord");
		meta.setVersionId("1");
		meta.setLastUpdated(new Date());
		composition.setMeta(meta);
		composition.setTitle("Discharge summary");
		composition.setConfidentiality(DocumentConfidentiality.N);
		composition.setCustodian(new Reference().setReference("Organization/1"));
		composition.setEncounter(new Reference().setReference("Encounter/1"));
		composition.setSubject(new Reference().setReference("Subject/1"));

		List<SectionComponent> sl = new ArrayList<SectionComponent>();

		CodeableConcept cc2 = new CodeableConcept();
		cc2.addCoding().setDisplay("Chief complaint section").setCode("422843007").setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Chief complaints").setCode(cc2)
				.addEntry(new Reference().setReference("Condition/2")));

		CodeableConcept cc3 = new CodeableConcept();
		cc3.addCoding().setDisplay("History and physical report").setCode("371529009")
				.setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Medical History").setCode(cc3)
				.addEntry(new Reference().setReference("Procedure/1"))
				.addEntry(new Reference().setReference("Condition/1")));

		CodeableConcept cc4 = new CodeableConcept();
		cc4.addCoding().setDisplay("Diagnostic studies report").setCode("721981007")
				.setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Investigations").setCode(cc4)
				.addEntry(new Reference().setReference("DiagnosticReport/1")));
		CodeableConcept cc5 = new CodeableConcept();
		cc5.addCoding().setDisplay("Clinical procedure report").setCode("371525003")
				.setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Procedures").setCode(cc5)
				.addEntry(new Reference().setReference("Procedure/2")));

		CodeableConcept cc6 = new CodeableConcept();
		cc6.addCoding().setDisplay("Medication summary document").setCode("721912009")
				.setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Medications").setCode(cc6)
				.addEntry(new Reference().setReference("MedicationRequest/1")));

		CodeableConcept cc7 = new CodeableConcept();
		cc7.addCoding().setDisplay("Care Plan").setCode("734163000").setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Care Plan").setCode(cc7)
				.addEntry(new Reference().setReference("CarePlan/1")));

		CodeableConcept cc8 = new CodeableConcept();
		cc8.addCoding().setDisplay("Discharge summary").setCode("373942005").setSystem("http://snomed.info/sct");
		sl.add(new SectionComponent().setTitle("Document Reference").setCode(cc8)
				.addEntry(new Reference().setReference("DocumentReference/1")));

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		composition.setSection(sl);

		////////////////////////////////////////////// Composition
		////////////////////////////////////////////// END//////////////////////////////////////////////////////////////////

		////////////////////////////////////////////// Practitioner
		////////////////////////////////////////////// START//////////////////////////////////////////////////////////////////

		Practitioner practitioner = new Practitioner();
		practitioner.setId("1");
		practitioner.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner")
				.setVersionId("1").setLastUpdated(new Date()));

		CodeableConcept c1 = new CodeableConcept();
		c1.addCoding().setDisplay("Medical License number").setCode("MD")
				.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203");
		practitioner.addIdentifier().setType(c1).setSystem("https://doctor.ndhm.gov.in").setValue("21-1521-3828-3227");
		practitioner.addName().setText("DEF");
		////////////////////////////////////////////// Practitioner
		////////////////////////////////////////////// END//////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////// Organization 1
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////////

		Organization organization = new Organization();
		organization.setId("1");
		organization.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization"));
		CodeableConcept p1 = new CodeableConcept();
		p1.addCoding().setDisplay("Provider number").setCode("PRN")
				.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203");
		organization.addIdentifier().setType(p1).setSystem("https://facility.ndhm.gov.in").setValue("4567823");
		organization.setName("UVW Hospital");

		organization.addTelecom().setSystem(ContactPointSystem.PHONE).setUse(ContactPointUse.WORK)
				.setValue("+91 273 2139 3632");
		organization.addTelecom().setSystem(ContactPointSystem.EMAIL).setUse(ContactPointUse.WORK)
				.setValue("contact@facility.uvw.org");

		////////////////////////////////////////////// Organization 2
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////////

		Organization organization2 = new Organization();
		organization2.setId("1");
		organization2.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization"));
		CodeableConcept o2 = new CodeableConcept();
		p1.addCoding().setDisplay("Provider number").setCode("PRN")
				.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203");
		organization2.addIdentifier().setType(o2).setSystem("https://facility.ndhm.gov.in").setValue("4567878");
		organization2.setName("XYZ Lab Pvt.Ltd.");
		organization2.addTelecom().setSystem(ContactPointSystem.PHONE).setUse(ContactPointUse.WORK)
				.setValue("+91 243 2634 1234");
		organization2.addTelecom().setSystem(ContactPointSystem.EMAIL).setUse(ContactPointUse.WORK)
				.setValue("contact@labs.xyz.org");

		///////////////////////////////////////// Patient
		///////////////////////////////////////// Start//////////////////////////////////

		Patient patient = new Patient();
		patient.setId("1");
		patient.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient")
				.setVersionId("1").setLastUpdated(new Date()));
		CodeableConcept p5 = new CodeableConcept();
		p5.addCoding().setDisplay("Medical record number").setCode("MR")
				.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203");
		patient.addIdentifier().setType(p5).setSystem("https://healthid.ndhm.gov.in").setValue("22-7225-4829-5255");
		patient.addName().setText("ABC");
		patient.setGender(AdministrativeGender.MALE);
		patient.setBirthDate(new Date());
		patient.addTelecom().setSystem(ContactPointSystem.PHONE).setUse(ContactPointUse.HOME)
				.setValue("+91 243 2634 1234");

		//////////////////////////////////////////// Encounter
		//////////////////////////////////////////// Start///////////////////////////////////////////////////////
		Encounter encounter = new Encounter();
		encounter.setId("1");
		encounter.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Encounter")
				.setLastUpdated(new Date()));

		encounter.setStatus(EncounterStatus.FINISHED);

		CodeableConcept e1 = new CodeableConcept();
		e1.addCoding().setDisplay("inpatient encounter").setCode("IMP")
				.setSystem("http://terminology.hl7.org/CodeSystem/v3-ActCode");
		encounter.setClass_(new Coding().setDisplay("inpatient encounter")
				.setSystem("http://terminology.hl7.org/CodeSystem/v3-ActCode").setCode("IMP"));

		encounter.setSubject(new Reference().setReference("Patient/1"));
		encounter.setPeriod(new Period().setStart(new Date()).setEnd(new Date()));
		CodeableConcept e5 = new CodeableConcept();

		e5.setText("Discharged to Home Care").addCoding().setDisplay("HOME").setCode("HOME")
				.setSystem("http://terminology.hl7.org/CodeSystem/discharge-disposition");
		encounter.setHospitalization(new EncounterHospitalizationComponent().setDischargeDisposition(e5));

		///////////////////////////////////////////////////////////////////////
		/////////////////////////////////// Appointment////////////////////////////////////

		Appointment appointment = new Appointment();
		appointment.setId("1");
		appointment.setStatus(AppointmentStatus.BOOKED);
		appointment.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Appointment"));
		CodeableConcept a5 = new CodeableConcept();
		a5.addCoding().setDisplay("General medical practice").setCode("11429006").setSystem("http://snomed.info/sct");
		CodeableConcept a6 = new CodeableConcept();
		a6.addCoding().setDisplay("Consultation").setCode("408443003").setSystem("http://snomed.info/sct");
		CodeableConcept a7 = new CodeableConcept();
		a7.addCoding().setDisplay("Follow-up visit").setCode("185389009").setSystem("http://snomed.info/sct");

		appointment.addServiceCategory(a5);
		appointment.addServiceType(a6);
		appointment.setAppointmentType(a7);
		appointment.addReasonReference(new Reference().setReference("Condition/1"));
		appointment.setDescription("Discussion on the results of your recent Lab Test and further consultation");
		appointment.setStart(new Date());
		appointment.setEnd(new Date());
		appointment.setCreated(new Date());
		appointment.addParticipant().setActor(new Reference().setReference("Patient/1"))
				.setStatus(ParticipationStatus.ACCEPTED);
		appointment.addParticipant().setActor(new Reference().setReference("Practitioner/1"))
				.setStatus(ParticipationStatus.ACCEPTED);

		/////////////////////////////////////// Condition1
		/////////////////////////////////////// Start///////////////////////////////////////////////

		Condition condition = new Condition();
		condition.setId("1");
		condition.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition"));
		CodeableConcept con = new CodeableConcept();
		con.addCoding().setDisplay("recurrence").setCode("recurrence")
				.setSystem("http://terminology.hl7.org/CodeSystem/condition-clinical");
		con.addCoding().setDisplay("History of aortoiliac atherosclerosis").setCode("440700005")
				.setSystem("http://snomed.info/sct");
		con.setText("Patient complained about pain in left arm");
		condition.setClinicalStatus(con);
		condition.setSubject(new Reference().setReference("Patient/1"));

		/////////////////////////////////////// Condition 2
		/////////////////////////////////////// Start///////////////////////////////////////////////

		Condition condition2 = new Condition();
		condition2.setId("2");
		condition2.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition"));
		CodeableConcept con2 = new CodeableConcept();
		con2.addCoding().setDisplay("Active").setCode("active")
				.setSystem("http://terminology.hl7.org/CodeSystem/condition-clinical");
		con2.addCoding().setDisplay("Myocardial infarction").setCode("22298006").setSystem("http://snomed.info/sct");
		con2.setText(
				"pain in the chest, neck, back or arms, as well as fatigue, lightheadedness, abnormal heartbeat and anxiety.");
		condition2.setClinicalStatus(con2);
		condition2.setSubject(new Reference().setReference("Patient/1"));
		//////////////////////////////////////////////////////////////////////////////////////

		DiagnosticReport diagnosticReport = new DiagnosticReport();
		diagnosticReport.setId("1");
		diagnosticReport
				.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportLab"));
		diagnosticReport.addIdentifier().setSystem("https://xyz.com/lab/reports").setValue("5234342");
		diagnosticReport.setStatus(DiagnosticReportStatus.FINAL);
		CodeableConcept dr1 = new CodeableConcept();
		dr1.setText("Lipid Panel").addCoding().setDisplay("Hematology service").setCode("708196005")
				.setSystem("http://snomed.info/sct");
		dr1.addCoding().setDisplay("Lipid 1996 panel - Serum or Plasma").setCode("24331-1")
				.setSystem("http://loinc.org");
		diagnosticReport.addCategory(dr1);
		diagnosticReport.setSubject(new Reference().setReference("Patient/1").setDisplay("ABC"));
		diagnosticReport.setIssued(new Date());
		diagnosticReport.addPerformer(new Reference().setReference("Organization/2").setDisplay("XYZ Lab Pvt.Ltd."));
		diagnosticReport.addResultsInterpreter(new Reference().setReference("Practitioner/1").setDisplay("Dr. DEF"));
		diagnosticReport.addSpecimen(new Reference().setReference("Specimen/1"));
		diagnosticReport.addResult(new Reference().setReference("Observation/cholesterol"))
				.addResult(new Reference().setReference("Observation/triglyceride"));
		diagnosticReport.setConclusion("Elevated cholesterol/high density lipoprotein ratio");
		Attachment attachment = new Attachment();
		attachment.setContentType("application/pdf");
		attachment.setLanguage("en-IN");
		attachment.setTitle("Diagnostic Report");
		try {
			attachment.setData(Files.readAllBytes(Paths.get("C:\\Users\\ved\\Music\\CA\\1.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		diagnosticReport.addPresentedForm(attachment);
		CodeableConcept dc = new CodeableConcept();
		dc.addCoding().setDisplay("Elevated cholesterol/high density lipoprotein ratio").setCode("439953004")
				.setSystem("http://snomed.info/sct");
		diagnosticReport.addConclusionCode(dc);
		////////////////////////////////////////////// Observation
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////////

		Observation observation = new Observation();
		observation.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation"));
		observation.setId("cholesterol");
		observation.setStatus(Observation.ObservationStatus.FINAL);
		CodeableConcept obs = new CodeableConcept();
		obs.setText("Cholesterol").addCoding().setDisplay("Cholesterol [Moles/​volume] in Serum or Plasma")
				.setCode("35200-5").setSystem("http://loinc.org");
		observation.setCode(obs);
		observation.setSubject(new Reference().setReference("Patient/1"));
		Quantity quantity2 = new Quantity();
		quantity2.setValue(6.3).setUnit("mmol/L").setCode("258813002").setCode("http://snomed.info/sct");
		observation.setValue(quantity2);
		observation.addPerformer(new Reference().setReference("Organization/2"));
		observation.addReferenceRange().setHigh(new Quantity().setUnit("mmol/L").setSystem("http://snomed.info/sct")
				.setCode("258813002").setValue(4.5));

		//////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////// Observation 2
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////////

		Observation observation2 = new Observation();
		observation2.setId("triglyceride");
		observation2.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation"));

		observation2.setStatus(Observation.ObservationStatus.FINAL);
		CodeableConcept obs2 = new CodeableConcept();
		obs2.setText("Triglycerides, serum by Enzymatic method").addCoding()
				.setDisplay("Triglyceride [Moles/​volume] in Serum or Plasma").setCode("736368003")
				.setSystem("http://snomed.info/sct");

		observation2.setCode(obs2);
		observation2.setSubject(new Reference().setReference("Patient/1"));
		observation2.addPerformer(new Reference().setReference("Organization/2"));
		Quantity quantity = new Quantity();
		quantity.setValue(1.3).setUnit("mmol/L").setCode("258813002").setCode("http://snomed.info/sct");
		observation2.setValue(quantity);
		observation2.addReferenceRange().setHigh(new Quantity().setUnit("mmol/L").setSystem("http://snomed.info/sct")
				.setCode("258813002").setValue(2.0));
		////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////

		Procedure procedure = new Procedure();
		procedure.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Procedure"));
		procedure.setId("1");
		procedure.setStatus(ProcedureStatus.COMPLETED);
		CodeableConcept pro = new CodeableConcept();
		pro.setText("Placement of stent in coronary artery").addCoding()
				.setDisplay("Placement of stent in coronary artery").setCode("36969009")
				.setSystem("http://snomed.info/sct");

		procedure.setCode(pro);
		procedure.setSubject(new Reference().setReference("Patient/1"));
		DateTimeType dateTime1 = new DateTimeType();
		dateTime1.setValue(new Date());
		procedure.setPerformed(dateTime1);
		CodeableConcept pr = new CodeableConcept();

		pr.addCoding().setDisplay("Bleeding").setCode("131148009").setSystem("http://snomed.info/sct");
		procedure.addComplication(pr);

		Procedure procedure2 = new Procedure();
		procedure2.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/Procedure"));
		procedure2.setId("1");
		procedure2.setStatus(ProcedureStatus.COMPLETED);
		CodeableConcept pro2 = new CodeableConcept();
		pro2.setText("Coronary artery bypass grafting").addCoding().setDisplay("Coronary artery bypass grafting")
				.setCode("232717009").setSystem("http://snomed.info/sct");

		procedure2.setCode(pro2);
		procedure2.setSubject(new Reference().setReference("Patient/1"));
		DateTimeType dateTime = new DateTimeType();
		dateTime.setValue(new Date());
		procedure2.setPerformed(dateTime);

		////////////////////////////////////////////// CarePlan
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////////

		CarePlan carePlan = new CarePlan();
		carePlan.setId("1");
		carePlan.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/CarePlan"));
		carePlan.setStatus(CarePlanStatus.ACTIVE);
		carePlan.setIntent(CarePlanIntent.PLAN);
		CodeableConcept cpl = new CodeableConcept();
		cpl.addCoding().setDisplay("Coronary heart disease care plan").setCode("736368003")
				.setSystem("http://snomed.info/sct");
		carePlan.addCategory(cpl);
		carePlan.setTitle("Coronary heart disease care plan");
		carePlan.setDescription("Treatment of coronary artery and related disease problems");
		carePlan.setSubject(new Reference().setReference("Patient/1").setDisplay("ABC"));
		carePlan.addActivity(
				new CarePlanActivityComponent().addOutcomeReference(new Reference().setReference("Appointment/1")));

		/////////////////////////////////////////////////////////////////////////////////////////////////////

		Specimen specimen = new Specimen();
		specimen.setId("1");
		///////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////

		//////////////////////////////////////

		MedicationRequest medicationRequest = new MedicationRequest();
		medicationRequest.setId("1");
		medicationRequest
				.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest"));
		medicationRequest.setStatus(MedicationRequestStatus.ACTIVE);
		medicationRequest.setIntent(MedicationRequestIntent.ORDER);
		medicationRequest.setSubject(new Reference().setReference("Patient/1").setDisplay("ABC"));
		medicationRequest.setAuthoredOn(new Date());
		medicationRequest.setRequester(new Reference().setReference("Practitioner/1").setDisplay("DEF"));
		medicationRequest.addReasonReference(new Reference().setReference("Condition/2"));
		CodeableConcept ed = new CodeableConcept();
		ed.addCoding().setDisplay("With or after food").setCode("311504000").setSystem("http://snomed.info/sct");

		CodeableConcept meth = new CodeableConcept();
		meth.addCoding().setDisplay("Swallow").setCode("421521009").setSystem("http://snomed.info/sct");
		CodeableConcept rout = new CodeableConcept();
		rout.addCoding().setDisplay("Oral Route").setCode("26643006").setSystem("http://snomed.info/sct");
		medicationRequest.addDosageInstruction(new Dosage().setText("One tablet at once").addAdditionalInstruction(ed)
				.setTiming(new Timing().setRepeat(
						new TimingRepeatComponent().setFrequency(1).setPeriod(1).setPeriodUnit(UnitsOfTime.D)))
				.setRoute(rout).setMethod(meth));

		///////////////////////////////////////////////

		DocumentReference documentReference = new DocumentReference();
		documentReference
				.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentRefrence"));
		documentReference.setStatus(DocumentReferenceStatus.CURRENT);
		documentReference.setDocStatus(ReferredDocumentStatus.FINAL);
		CodeableConcept dr = new CodeableConcept();
		dr.setText("Discharge Summary");
		dr.addCoding().setDisplay("Discharge Summary").setCode("373942005").setSystem("http://snomed.info/sct");
		documentReference.setType(dr);
		documentReference.setSubject(new Reference().setReference("Patient/1"));

		Attachment attachment2 = new Attachment();

		attachment2.setContentType("application/pdf");
		attachment2.setLanguage("en-IN");
		attachment2.setTitle("Discharge Summary");
		attachment2.setSize(72);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			attachment2.setHash(md.digest(Files.readAllBytes(Paths.get("C:\\Users\\ved\\Music\\CA\\1.jpg"))));

		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		attachment2.setCreation(new Date());
		try {
			attachment2.setData(Files.readAllBytes(Paths.get("C:\\Users\\ved\\Music\\CA\\1.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documentReference.addContent(new DocumentReferenceContentComponent().setAttachment(attachment2));

		////////////////////////////////////////////// Bundle
		////////////////////////////////////////////// Start//////////////////////////////////////////////////////////////////

		Bundle bundle = new Bundle();
		bundle.setMeta(new Meta().addProfile("http://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle")
				.setVersionId("1")
				.addSecurity("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));
		bundle.setIdentifier(new Identifier().setSystem("http://hip.in").setValue(UUID.randomUUID().toString()));

		bundle.setType(BundleType.DOCUMENT);
		bundle.setId("DischargeSummary-example-04");
		bundle.setTimestamp(new Date());
		List<BundleEntryComponent> bec = new ArrayList<BundleEntryComponent>();
		bec.add(new BundleEntryComponent().setFullUrl("Composition/1"));
		bec.add(new BundleEntryComponent().setResource(composition));
		bec.add(new BundleEntryComponent().setFullUrl("Practitioner/1"));
		bec.add(new BundleEntryComponent().setResource(practitioner));

		bec.add(new BundleEntryComponent().setFullUrl("Organization/1"));
		bec.add(new BundleEntryComponent().setResource(organization));

		bec.add(new BundleEntryComponent().setFullUrl("Organization/2"));
		bec.add(new BundleEntryComponent().setResource(organization2));
		bec.add(new BundleEntryComponent().setFullUrl("Patient/1"));
		bec.add(new BundleEntryComponent().setResource(patient));

		bec.add(new BundleEntryComponent().setFullUrl("Encounter/1"));
		bec.add(new BundleEntryComponent().setResource(encounter));

		bec.add(new BundleEntryComponent().setFullUrl("Appointment/1"));
		bec.add(new BundleEntryComponent().setResource(appointment));

		bec.add(new BundleEntryComponent().setFullUrl("Condition/1"));
		bec.add(new BundleEntryComponent().setResource(condition));
		bec.add(new BundleEntryComponent().setFullUrl("Condition/2"));
		bec.add(new BundleEntryComponent().setResource(condition2));

		bec.add(new BundleEntryComponent().setFullUrl("DiagnosticReport/1"));
		bec.add(new BundleEntryComponent().setResource(diagnosticReport));
		bec.add(new BundleEntryComponent().setFullUrl("Observation/cholesterol"));
		bec.add(new BundleEntryComponent().setResource(observation));
		bec.add(new BundleEntryComponent().setFullUrl("Obsevation/triglyceride"));
		bec.add(new BundleEntryComponent().setResource(observation2));
		bec.add(new BundleEntryComponent().setFullUrl("Procedure/1"));
		bec.add(new BundleEntryComponent().setResource(procedure));
		bec.add(new BundleEntryComponent().setFullUrl("Procedure/2"));
		bec.add(new BundleEntryComponent().setResource(procedure2));

		bec.add(new BundleEntryComponent().setFullUrl("MedicationRequest/1"));
		bec.add(new BundleEntryComponent().setResource(medicationRequest));

		bec.add(new BundleEntryComponent().setFullUrl("CarePlan/1"));
		bec.add(new BundleEntryComponent().setResource(carePlan));
		bec.add(new BundleEntryComponent().setFullUrl("DocumentReference/1"));
		bec.add(new BundleEntryComponent().setResource(documentReference));

		bundle.setEntry(bec);
		
		FhirContext ctx = FhirContext.forR4();
		System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));

		IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		

		MethodOutcome outcome = client
				.create()
				.resource(bundle)
				.execute();

			IIdType id = outcome.getId();
			System.out.println("Created new Patient, got ID: " + id);
	}

}
	
