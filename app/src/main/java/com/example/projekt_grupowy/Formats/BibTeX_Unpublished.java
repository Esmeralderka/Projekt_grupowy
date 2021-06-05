package com.example.projekt_grupowy.Formats;

public class BibTeX_Unpublished  extends BibTeX {


    public BibTeX_Unpublished() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "unpublished";
        addField("author", true);
        addField("title", true);
        addField("note", true);

        addField("year", false);
        addField("month", false);
        addField("key", false);

    }

}
