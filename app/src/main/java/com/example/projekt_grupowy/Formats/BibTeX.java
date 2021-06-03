// by Szymon Krawczyk

package com.example.projekt_grupowy.Formats;

public abstract class BibTeX extends Format {

    public BibTeX() {
        super();
        this.fileFormat = "bib";
    }

    @Override
    public String makeFormat(){
        String output = "";

        output += "@" + this.fileType + "{" + documentName;

        for (String key : getKeys()) {

            if (!formatFields.get(key).equals("")) {
                output += ",\n\t" + key + " = \"" + formatFields.get(key) + "\"";
            }
        }


        output += "\n}";
        return output;
    }
}
