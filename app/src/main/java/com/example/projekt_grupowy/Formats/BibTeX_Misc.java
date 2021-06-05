package com.example.projekt_grupowy.Formats;

public class BibTeX_Misc extends BibTeX {


    public BibTeX_Misc() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "misc";


        addField("author", false);
        addField("howpublished", false);
        addField("author", false);
        addField("year", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}