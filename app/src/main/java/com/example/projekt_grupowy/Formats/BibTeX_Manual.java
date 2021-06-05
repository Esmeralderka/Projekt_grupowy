package com.example.projekt_grupowy.Formats;

public class BibTeX_Manual extends BibTeX {


    public BibTeX_Manual() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "manual";


        addField("title", true);


        addField("author", false);
        addField("organization", false);
        addField("address", false);
        addField("edition", false);
        addField("note", false);
        addField("key", false);



            }

}