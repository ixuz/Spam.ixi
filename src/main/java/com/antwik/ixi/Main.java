package com.antwik.ixi;

import org.iota.ict.Ict;
import org.iota.ict.utils.Constants;
import org.iota.ict.utils.properties.EditableProperties;

/**
 * This class is just for testing your IXI, so you don't have to run Ict manually.
 * */
public class Main {

    public static void main(String[] args) {
        System.out.println("Running Spam.ixi for Ict version " + Constants.ICT_VERSION);
        EditableProperties properties = new EditableProperties();
        properties.guiEnabled(false);
        Ict ict = new Ict(properties.toFinal());
        SpamIxi spamIxi = new SpamIxi(ict);
        new Thread(spamIxi).run();
    }
}