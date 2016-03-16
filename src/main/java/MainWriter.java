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

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;

public class MainWriter {
    private static final Logger  LOGGER = Logger.getLogger( MainWriter.class.getName() );
    private static FileHandler fh = null;
    private static DataLogFormater format = new DataLogFormater();

    private static void init(){
        try{
            fh = new FileHandler("data.log",false);
        }catch (IOException e){
            System.err.println("Failed to open file");
            e.printStackTrace();
        }
        LOGGER.addHandler(fh);
        fh.setFormatter(format);
        fh.setLevel(Level.INFO);
        LOGGER.log(Level.INFO,"Log Initiated");
    }


    public static void main(String[] args) throws IOException {
        init();


        final Board board = Platform.createBoard();

        I2cBus bus = board.getI2cBus(BBBNames.I2C_1);  //get i2c bus

        Arduino a1 = new Arduino(bus, 0x10); //initialize arduino object with address 0x10



        int counter = 0;

        while (counter != 100) {
            String input = a1.readFrom();

            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            LOGGER.log(Level.INFO, input);

            counter++;
        }
    }

}
