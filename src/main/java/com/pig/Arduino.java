package com.pig;

import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.io.bus.i2c.I2cDevice;

import java.io.IOException;

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


public class Arduino extends I2cDevice {
    //constructors inherited from I2cDevice
    //look for examples in the LibBulldog for this
    //arduino is being treated here as a "sensor"
    public Arduino(I2cBus bus, int address) {
        super(bus, address);
    }

    public Arduino(I2cConnection connection) {
        super(connection);
    }

    public void writeTo(String string) {
        try {
            this.writeString("10");
        } catch (Exception e) {
            System.err.println("Error: Failed to write to I2C bus");
            e.printStackTrace();
        }
    }

    public String readFrom() {
        String input = "";
        byte[] buff = new byte[2];

        try {
            this.getBusConnection().readBytes(buff);
        } catch (IOException e) {
            System.err.println("Failled to read bytes");
            e.printStackTrace();
        }

//        byte inputByteArray[] = new byte[2];

//        try {
////            this.open();
////
////            this.writeByte(0);
////            if(this.isOpen()){
////                byte b = this.readByte();
////                input = Byte.toString(b);
////            } else {
////                System.err.println("Failed to open connection");
////                input = "FAIL TO OPEN CONNECTION";
////            }
////
////            this.close();
////        } catch (Exception e) {
////            System.err.println("Error: Failed to read I2C bus");
////            e.printStackTrace();
////
////            input = "Failed to read";
////        }
//
//
//        return input;
//    }

        for(byte b : buff){
            input = input + Byte.toString(b);
        }

        return input;
    }
}