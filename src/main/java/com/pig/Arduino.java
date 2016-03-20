package com.pig;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;


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

        void getBytes(IntByReference numDev, PointerByReference returnArray);

        void cleanUp(Pointer p);
    }

    public float[] readFloats() {
        PointerByReference returnRefptr = new PointerByReference();
        IntByReference numDev = new IntByReference();


        Tmp102Library.INSTANCE.getBytes(numDev, returnRefptr);

        int numVals = numDev.getValue();

        this._numOfDev = numVals;

        System.out.println("Retrived " + numVals + " floats");

        if (0 < numVals) {
            Pointer ptrVal = returnRefptr.getValue();

            float[] rtVal = new float[numVals];
            for (int i = 0; i < numVals; i++) {
                rtVal[i] = ptrVal.getFloat(i * Native.getNativeSize(Float.TYPE));
            }
            Tmp102Library.INSTANCE.cleanUp(ptrVal);
            return rtVal;
        } else {
            return null;
        }

    }

    public int get_numOfDev(){
        return _numOfDev;
    }

    private int _numOfDev;
}

//public interface CLibrary extends Library {
//    public double example9_getDoubleArray(PointerByReference valsRef, IntByReference numValsRef);
//    public void example9_cleanup(Pointer p);
//}
//...
//        CLibrary clib = (CLibrary)Native.loadLibrary("testlib", CLibrary.class);
//        ...
//        PointerByReference ex9ValsRefPtr = new PointerByReference();
//        IntByReference ex9NumValsRef = new IntByReference();
//// call the C function
//        clib.example9_getDoubleArray(ex9ValsRefPtr, ex9NumValsRef);
////    // extract the number of values returned
//        int ex9NumVals = ex9NumValsRef.getValue();
//        System.out.println("example 9: retreived " + ex9NumVals + " values:");
//// make sure the C function returned some values
//        if (0 < ex9NumVals) {
//        // extract the pointed-to pointer
//        Pointer ex9pVals = ex9ValsRefPtr.getValue();
//        // look at each value
//        double ex9total = 0.0;
//        for (int ex9Loop=0; ex9Loop<ex9NumVals; ex9Loop++) {
//            double ex9val = ex9pVals.getDouble(ex9Loop * Native.getNativeSize(Double.TYPE));
//            ex9total += ex9val;
//        }
//        System.out.println("\ttotal: " + ex9total);
//
//        // call C code to clean up memory allocated in C
//        System.out.println("\t(example 9: cleanup)");
//        clib.example9_cleanup(ex9pVals);
//    }
//
//}
//
