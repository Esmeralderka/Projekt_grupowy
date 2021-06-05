package com.example.projekt_grupowy.Formats;

public class BibTeX_Booklet extends BibTeX {


    public BibTeX_Booklet() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "booklet";
        addField("title", true);

        addField("author", false);
        addField("address", false);
        addField("howpublished", false);
        addField("year", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}
