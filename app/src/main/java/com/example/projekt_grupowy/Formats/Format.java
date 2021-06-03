// by Szymon Krawczyk

package com.example.projekt_grupowy.Formats;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Format {

    protected HashMap<String, String> formatFields;
    protected HashMap<String, Boolean> isFieldRequired;
    protected HashMap<String, String> formatFieldsShort;
    protected ArrayList<String> keys;
    protected String fileFormat;
    protected String documentName;
    protected String fileType;

    public Format() {
        this.formatFields = new HashMap<>();
        this.isFieldRequired = new HashMap<>();
        this.formatFieldsShort = new HashMap<>();
        this.keys = new ArrayList<>();
        this.fileFormat = "";
        this.documentName = "";
        this.fileType = "";

        initialize();
    }

    // dodawanie pola w formatach
    protected void addField(String fieldName, boolean isFieldRequired) {
        this.keys.add(fieldName);
        this.formatFields.put(fieldName, "");
        this.isFieldRequired.put(fieldName, isFieldRequired);
    }
    protected void addField(String fieldName) {
        addField(fieldName, false);
    }

    // dodawanie pola w formatach
    protected void addField(String fieldName, String fieldNameShort, boolean isFieldRequired) {
        this.keys.add(fieldName);
        this.formatFields.put(fieldName, "");
        this.formatFieldsShort.put(fieldName, fieldNameShort);
        this.isFieldRequired.put(fieldName, isFieldRequired);
    }
    protected void addField(String fieldName, String fieldNameShort) {
        addField(fieldName, fieldNameShort, false);
    }

    // zmiana jednego pola, w obiekcie
    public void setField(String key, String value) throws NullPointerException {
        if (! formatFields.containsKey(key)) throw new NullPointerException();

        formatFields.replace(key, value);
    }

    // walidacja hashmapy
    protected boolean validateHashMap(HashMap<String, String> hashMap) {
        boolean validate = true;

        for(String key : keys) {

            if (usesShortNames()) {
                if (!formatFields.containsKey(key) || formatFieldsShort.get(key).equals("")) {
                    validate = false;
                    break;
                }
            }

            if (!hashMap.containsKey(key)) {
                validate = false;
                break;
            }

            if (isFieldRequired.get(key)) {
                if (hashMap.get(key).equals("")) {
                    validate = false;
                    break;
                }
            }
        }

        return validate;
    }


    @Override
    public String toString() {
        return getFormat();
    }

    // pobranie formatu
    public String getFormat() {
        if (!validateHashMap(formatFields)) return null;

        return makeFormat();
    }

    protected abstract void initialize();

    // string z formatem
    protected abstract String makeFormat();

    // czy uzywa krotkich form nazw
    public boolean usesShortNames() {
        return !formatFieldsShort.isEmpty();
    }

    // klucze w kolejnosci dodania
    public ArrayList<String> getKeys() {
        return keys;
    }

    // pobranie hashmapy dla zmiany poza obiektem
    public HashMap<String, String> getFormatFields() {
        return formatFields;
    }

    // zapisanie hashmapy do obiektu (jezeli poprawna)
    public void setFormatFields(HashMap<String, String> formatFields) throws NullPointerException {
        if (! validateHashMap(formatFields)) throw new NullPointerException();
        this.formatFields = formatFields;
    }

    // pobranie hashmapy z info, czy pola sa wymagane
    public HashMap<String, Boolean> getIsFieldRequired() {
        return isFieldRequired;
    }


    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
