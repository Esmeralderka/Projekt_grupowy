package com.example.projekt_grupowy.Formats;

public class BibTeX_Article extends BibTeX {


    public BibTeX_Article() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "article";

        addField("author", true);
        addField("title", true);
        addField("journal", true);
        addField("year", true);

        addField("volume", false);
        addField("number", false);
        addField("pages", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}
