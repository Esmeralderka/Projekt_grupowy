package com.example.projekt_grupowy.Formats;

public class EndNote_Generic extends EndNote {

    public EndNote_Generic() {
        super();
    }

    @Override
    protected void initialize() {

        this.fileType = "Generic";

        addField("Author", "%A");
        addField("Secondary title", "%B");
        addField("Place published", "%C");
        addField("Year", "%D");
        addField("Editor/Secondary Editor", "%E");
        addField("Label", "%F");
        addField("Language", "%G");
        addField("Translated author", "%H");
        addField("Publisher", "%I");
        addField("Journal name", "%J");
        addField("Keywords", "%K");
        addField("Call number", "%L");
        addField("Accession number", "%M");
        addField("Number", "%N");
        addField("Alternate title", "%O");
        addField("Pages", "%P");
        addField("Translated title", "%Q");
        addField("DOI", "%R");
        addField("Teritiary title", "%S");
        addField("Title", "%T");
        addField("URL", "%U");
        addField("Volume", "%V");
        addField("Database provider", "%W");
        addField("Abstract", "%X");
        addField("Teritiary author/Translator", "%Y");
        addField("Notes", "%Z");

        addField("Reference type", "%0");
        addField("Custom 1", "%1");
        addField("Custom 2", "%2");
        addField("Custom 3", "%3");
        addField("Custom 4", "%4");
        addField("Number of volumes", "%6");
        addField("Edition", "%7");
        addField("Date", "%8");
        addField("Type of work", "%9");

        addField("Subsidiary author", "%?");
        addField("ISBN/ISSN", "%@");
        addField("Short title", "%!");
        addField("Custom 5", "%#");
        addField("Custom 6", "%$");
        addField("Custom 7", "%]");
        addField("Section", "%&");
        addField("Original publication", "%(");
        addField("Reprint edition", "%)");
        addField("Reviewed item", "%*");
        addField("Author address", "%+");
        addField("Caption", "%^");
        addField("File attachments", "%>");
        addField("Research notes", "%<");
        addField("Access date", "%[");
        addField("Custom 8", "%=");
        addField("Name of database", "%~");
    }
}
