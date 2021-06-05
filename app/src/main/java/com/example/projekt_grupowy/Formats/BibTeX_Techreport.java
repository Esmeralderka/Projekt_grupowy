package com.example.projekt_grupowy.Formats;

public class BibTeX_Techreport extends BibTeX {


    public BibTeX_Techreport() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "techreport";
        addField("author", true);
        addField("title", true);
        addField("institution", true);
        addField("year", true);

        addField("number", false);
        addField("type", false);
        addField("address", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);

    }

}