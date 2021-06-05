package com.example.projekt_grupowy.Formats;

public class BibTeX_Inproceedings extends BibTeX {


    public BibTeX_Inproceedings() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "inproceedings";
        addField("author", true);
        addField("title", true);
        addField("booktitle", true);
        addField("year", true);

        addField("volume", false);
        addField("series", false);
        addField("pages", false);
        addField("month", false);
        addField("address", false);
        addField("publisher", false);
        addField("editor", false);
        addField("organization", false);
        addField("note", false);
        addField("key", false);
    }

}