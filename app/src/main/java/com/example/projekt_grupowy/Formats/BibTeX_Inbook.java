package com.example.projekt_grupowy.Formats;

public class BibTeX_Inbook  extends BibTeX {


    public BibTeX_Inbook() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "inbook";
        addField("author", true);
        addField("title", true);
        addField("chapter", true);
        addField("year", true);
        addField("publisher", true);

        addField("volume", false);
        addField("series", false);
        addField("type", false);
        addField("address", false);
        addField("edition", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}