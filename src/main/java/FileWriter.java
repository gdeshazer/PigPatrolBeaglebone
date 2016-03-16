/**
 * Created by grantdeshazer on 3/15/16.
 */

import java.io.*;
import java.io.FileInputStream;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;

public class FileWriter {

    public static void main(String[] args) throws IOException{
        final Board board = Platform.createBoard();

        I2cBus bus = board.getI2cBus(BBBNames.I2C_1);



        try{
            InputStream data = new FileInputStream("data.txt");


            Timer myTime = new Timer();

            myTime.getTime();

        } catch(Exception e){
            System.out.print("Exception");
        }



    }

}
