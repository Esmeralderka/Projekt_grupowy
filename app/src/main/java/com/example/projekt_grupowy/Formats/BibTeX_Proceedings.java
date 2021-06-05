package com.example.projekt_grupowy.Formats;

public class BibTeX_Proceedings extends BibTeX {


    public BibTeX_Proceedings() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "proceedings";
        addField("title", true);
        addField("year", true);




        addField("editor", false);
        addField("volume", false);
        addField("series", false);
        addField("address", false);
        addField("month", false);
        addField("publisher", false);
        addField("organization", false);
        addField("note", false);
        addField("key", false);

    }

}