package com.pse.mowitnow;

import com.pse.mowitnow.business.MowerDriver;
import com.pse.mowitnow.exceptions.InstructionsNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;

public class MowItnowApp {

    public static void main(String[] args) throws IOException, URISyntaxException, InstructionsNotFoundException {
        MowerDriver mowerDriver = new MowerDriver();
        mowerDriver.drive("instructions.txt");
    }

}
