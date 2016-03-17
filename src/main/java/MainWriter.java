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

import mockClass.ArduinoMock;

//import org.bulldog.beagleboneblack.BBBNames;
//import org.bulldog.core.io.bus.i2c.I2cBus;
//import org.bulldog.core.platform.Board;
//import org.bulldog.core.platform.Platform;

public class MainWriter {
    //using a logger for data storage...simple set up and relatively fast
    //easier than file IO
    //set up for logger
    private static final Logger  LOGGER = Logger.getLogger( MainWriter.class.getName() );
    private static FileHandler fh = null;
    private static DataLogFormater form = new DataLogFormater();

    //Logger congfiguration.
    private static void init(){
        try{
            //path here is specific to beaglebone
            fh = new FileHandler("/media/usb/GRANT/data.log",false);
        }catch (IOException e){
            System.err.println("Failed to open file");
            e.printStackTrace();
        }

        Logger l = Logger.getLogger("");
        l.addHandler(fh);
        fh.setFormatter(form);
        fh.setLevel(Level.INFO);

    }


    public static void main(String[] args) throws IOException {
        init();

        //commented out for testing purposes.  ArduinoMock
        //provides fake values generated by java random number gen
//        final Board board = Platform.createBoard();
//
//        I2cBus bus = board.getI2cBus(BBBNames.I2C_1);  //get i2c bus

        ArduinoMock a1 = new ArduinoMock(); //mock arduino...returns random number

        int counter = 0;

        while (counter != 100) {
            String input = a1.readFrom();

            try{
                Thread.sleep(100);  //like arduion delay (delay(100))
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            LOGGER.log(Level.INFO, input);  //Store collected data in log file

            counter++;
        }
    }

}
