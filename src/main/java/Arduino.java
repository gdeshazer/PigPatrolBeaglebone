import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.io.bus.i2c.I2cDevice;

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
 */


public class Arduino extends I2cDevice {
    public Arduino(I2cBus bus, int address) {
        super(bus, address);
    }

    public Arduino(I2cConnection connection) {
        super(connection);
    }

    public void writeTo(String string) {
        try {
            this.writeString("10");
        } catch (Exception e){
            System.err.println("Error: Failed to write to I2C bus");
            e.printStackTrace();
        }
    }

    public String readFrom() {
        String input;

        try {
            this.open();
            if(this.isOpen()) {
                input = this.readString();
            } else {
                System.err.println("Failed to open connection");
                input = "FAIL TO OPEN CONNECTION";
            }

            this.close();
        } catch (Exception e) {
            System.err.println("Error: Failed to read I2C bus");
            e.printStackTrace();

            input = "Failed to read";
        }


        return input;
    }

}