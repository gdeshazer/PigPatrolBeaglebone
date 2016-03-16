package mockClass;

/**
 * Created by grantdeshazer on 3/16/16.
 */

import java.util.Random;

public class ArduinoMock {

    public String readFrom(){
        String in;
        Random r = new Random();

        int rand = r.nextInt(10);

        return Integer.toString(rand);
    }

}
