package com.example.projekt_grupowy.Formats;

public class BibTeX_Incollection  extends BibTeX {


    public BibTeX_Incollection() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "incollection";
        addField("author", true);
        addField("title", true);
        addField("booktitle", true);
        addField("year", true);
        addField("publisher", true);

        addField("editor", false);
        addField("volume", false);
        addField("series", false);
        addField("type", false);
        addField("chapter", false);
        addField("pages", false);
        addField("address", false);
        addField("edition", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}