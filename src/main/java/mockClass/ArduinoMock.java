package mockClass;

/**
 * Created by grantdeshazer on 3/16/16.
 *
 * Mock Arduino class that returns random number
 *
 */

import java.util.Random;

public class ArduinoMock {

    public String readFrom(){
        Random r = new Random();

        int rand = r.nextInt(10);

        return Integer.toString(rand);
    }

}
