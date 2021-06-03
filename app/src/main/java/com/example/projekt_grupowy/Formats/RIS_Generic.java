// by Szymon Krawczyk

package com.example.projekt_grupowy.Formats;

public class RIS_Generic extends RIS {

    public RIS_Generic(String type) {
        super(type);
    }

    @Override
    protected void initialize() {

        //this.fileType = "Generic";

        //addField("Author", "%A");
        addField("Type of reference", "TY");
        addField("Primary Authors", "A1");
        addField("Secondary Authors", "A2");
        addField("Tertiary Authors ", "A3");
        addField("Subsidiary Authors", "A4");


        addField("Abstract", "AB");
        addField("Author Address", "AD");
        addField("Accession Number", "AN");
        addField("Author", "AU");
        addField("Location in Archives", "AV");

        addField("field maps to T2 ", "BT");

        addField("Custom 1", "C1");
        addField("Custom 2", "C2");
        addField("Custom 3", "C3");
        addField("Custom 4", "C4");
        addField("Custom 5", "C5");
        addField("Custom 6", "C6");
        addField("Custom 7", "C7");
        addField("Custom 8", "C8");


        addField("Caption", "CA");
        addField("Call Number", "CN");
        addField("alphanumeric characters", "CP");
        addField("Title of unpublished reference", "CT");
        addField("Place Published", "CY");
        addField("Date", "DA");
        addField("Name of Database", "DB");
        addField("DOI", "DO");
        addField("Database Provider", "DP");

        addField("Editor", "ED");
        addField("End Page", "EP");
        addField("Edition", "ET");
        addField("Reference ID", "ID");
        addField("Issue number", "IS");

        addField("Periodical name: user abbreviation", "J1");
        addField("Alternate Title", "J2");
        addField("Periodical name: standard abbreviation", "JA");
        addField("Journal/Periodical name: full format", "JF");
        addField("Journal/Periodical name: full format", "JO");
        addField("Keywords", "KW");
        addField("Link to PDF", "L1");
        addField("Link to Full-text", "L2");
        addField("Related Records", "L3");
        addField("Image(s)", "L4");
        addField("Language", "LA");
        addField("Label", "LB");
        addField("Website Link", "LK");
        addField("Number", "M1");
        addField("Miscellaneous 2", "M2");
        addField("Type of Work", "M3");
        addField("Notes", "N1");
        addField("Abstract", "N2");
        addField("Number of Volumes", "NV");
        addField("Original Publication", "OP");
        addField("Publisher", "PB");
        addField("Publishing Place", "PP");
        addField("Publication year (YYYY)", "PY");
        addField("Reviewed Item", "RI");
        addField("Research Notes", "RN");
        addField("Reprint Edition", "RP");
        addField("Section", "SE");
        addField("ISBN/ISSN", "SN");
        addField("Start Page", "SP");
        addField("Short Title", "ST");
        addField("Primary Title", "T1");
        addField("Secondary Title", "T2");
        addField("Tertiary Title", "T3");
        addField("Translated Author", "TA");
        addField("Title", "TI");
        addField("Translated Title", "TT");
        addField("User definable 1", "U1");
        addField("User definable 2", "U2");
        addField("User definable 3", "U3");
        addField("User definable 4", "U4");
        addField("User definable 5", "U5");
        addField("URL", "UR");
        addField("Volume number", "VL");
        addField("Published Standard number", "VO");
        addField("Primary Date", "Y1");
        addField("Access Date", "Y2");
        addField("End of Reference", "ER");


    }
}
