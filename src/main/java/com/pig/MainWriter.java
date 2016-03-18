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

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;

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
        int numberOfFiles = 4;

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

        final Board board = Platform.createBoard();

        I2cBus bus = board.getI2cBus(BBBNames.I2C_1);  //get i2c bus

        //ArduinoMock a1 = new ArduinoMock(); //mock arduino...returns random number

        Arduino a1 = new Arduino(bus, 0x10);

        int counter = 0;

        timer.setStartTime();
        while (counter != 10) {
            System.out.println("Reading from arduino...");

            String input = a1.readFrom() + " : " + Long.toString(timer.getTime());

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
