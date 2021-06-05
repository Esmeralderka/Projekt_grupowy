package com.example.projekt_grupowy.Formats;

public class BibTeX_Mastersthesis  extends BibTeX {


    public BibTeX_Mastersthesis() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "mastersthesis";
        addField("author", true);
        addField("title", true);
        addField("school", true);
        addField("year", true);

        addField("type", false);
        addField("address", false);
        addField("month", false);
        addField("note", false);
        addField("key", false);
    }

}
