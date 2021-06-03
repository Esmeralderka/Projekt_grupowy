// by Szymon Krawczyk

package com.example.projekt_grupowy.Formats;


public abstract class EndNote extends Format {

    public EndNote() {
        super();
        this.fileFormat = "enw";
    }

    @Override
    public String makeFormat(){
        String output = "";

        for (String key : getKeys()) {

            if (!formatFields.get(key).equals("")) {
                output += formatFieldsShort.get(key) + " " + formatFields.get(key) + "\n";
            }
        }

        return output;
    }
}
