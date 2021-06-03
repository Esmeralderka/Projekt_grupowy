package com.example.projekt_grupowy.Formats;

public class BibTeX_Book extends BibTeX {


    public BibTeX_Book() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "book";

        addField("author", true);
        addField("title", true);
        addField("publisher", true);
        addField("year", true);

        addField("volume", false);
        addField("series", false);
        addField("address", false);
        addField("edition", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}
