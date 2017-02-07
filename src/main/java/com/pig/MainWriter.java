package com.pig;

/**
 * Created by grantdeshazer on 3/15/16.
 *
 * Initial coding.  Logger has specialized formating
 * that includes a runtime time stamp in it.
 *
 * Timer Class is unused at the moment
 *
 */

import java.io.*;
import java.util.logging.*;

//import mockClass.ArduinoMock;


public class MainWriter {
    //using a logger for data storage...simple set up and relatively fast
    //easier than file IO
    //set up for logger
    private static final Logger  LOGGER = Logger.getLogger( MainWriter.class.getName() );
    private static FileHandler fh = null;
    private static ConsoleHandler ch = new ConsoleHandler();
    private static DataLogFormater form = new DataLogFormater();

    //Logger congfiguration
    private static void init(){

        int fileSize = (int) (5 * Math.pow(10,6));
        int numberOfFiles = 10;

        try{
            //path here is specific to beaglebone
            //boolean param controls whether file is appended to
            //or is overwritten
            fh = new FileHandler("/var/log/pig/data%g.log",fileSize,numberOfFiles, true);
        }catch (IOException e){
            System.err.println("Failed to open file");
            e.printStackTrace();
        }

        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(ch);
        LOGGER.addHandler(fh);
        fh.setFormatter(form);
        fh.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter());
        ch.setLevel(Level.OFF);  //disables console output

    }


    public static void main(String[] args) throws IOException {
        init();
        Timer timer = new Timer();


        Arduino a1 = new Arduino();

        int counter = 0;


        timer.setStartTime();
        while (counter != 10000) {
            String input = "";

            float[] returnFloat = new float[2];
            returnFloat = a1.readFloats();

            for(float i : returnFloat){
                input = input + Float.toString(i) + "\t";
            }
            input = input +  "\t" + Long.toString(timer.getDeltaTime());

            try{
                Thread.sleep(100);  //like arduion delay (delay(10))
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            LOGGER.log(Level.INFO, input);  //Store collected data in log file

            counter++;
        }
    }

}
