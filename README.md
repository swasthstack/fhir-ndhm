# NDHM FHIR - Client Starter

This project is a simple "skeleton project" (a Maven project containing all dependencies needed to run the NDDHM FHIR client).

Instructions:

* Import this project into your IDE
* Try executing the class `.java`

You will see that this project loads the resource `Discharge Summary` from the R4 testing endpoint hosted at http://hapi.fhir.org/baseR4

# Let's Build - NDHM FHIR Discharge Summary Introduction

Check out the [Client Documentation](http://hapi.fhir.org/baseR4/Bundle/1441944/_history/1) to see the JSON syntax for DischargeSummary FHIR Rsource published by NRCES https://nrces.in/ndhm/fhir/r4/Bundle-DischargeSummary-example-04.json.html.

Here are some things to try:

* [ ] Perform a FHIR Search for DischargeSummary resources where the patient has a name of "ABC". 

   * [ ] Print the resource ID for each matching resource
   
* [ ] Use a FHIR **Create** operation to create a new DischargeSummary resource on the server. Give it your name, or a fictional name you make up.

   * [ ] Log the ID that the server assigned to this resource
   
   * [ ] Use a FHIR **Read** to read it back
   
   * [ ] Use a FHIR **Update** to update it
   
   * [ ] Use a FHIR **Delete** to delete it
   
   * [ ] Try modifying your *Read* code so that it displays a useful error if the resource has been deleted

