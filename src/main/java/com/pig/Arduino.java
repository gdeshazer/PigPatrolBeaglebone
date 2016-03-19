package com.pig;

import com.sun.jna.Library;
import com.sun.jna.Native;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by grantdeshazer on 3/16/16.
 *
 * Arduino class represents each of the arduinos connected
 * to the I2C bus.  Functions of this class handle the actual
 * communication with the various Arduino's and will also
 * store the address's of the arduino that the particular object
 * represents.
 *
 * The writeTo function might eventually handle sending a timestamp to
 * each Arduino.  But this may not be necessary if Beagelebone is
 * able to collect data quickly enough from each Arduino.
 *
 * Potentialy this could be implemented as a state machine to add
 * layer of complexity... IE beaglebone requests specifc data first
 * before Arduino sends anything.  Doing so could very well be useful
 * in ensuring that the correct data is sent and that each device knows
 * what it will be receiving.
 *
 */


public class Arduino {
    public interface Tmp102Library extends Library {
        Tmp102Library INSTANCE = (Tmp102Library) Native.loadLibrary("tmp102.so", Tmp102Library.class);
        float getBytes();
    }

    public float readFloat(){
        float f = -2;

        try {
            f = Tmp102Library.INSTANCE.getBytes();

        } catch(Exception e) {
            e.printStackTrace();
        }


        return f;
    }


}