import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.io.bus.i2c.I2cDevice;

/**
 * Created by grantdeshazer on 3/16/16.
 */
public class Arduino extends I2cDevice{
    public Arduino(I2cBus bus, int address){ super(bus, address); }

    public Arduino(I2cConnection connection) { super(connection); }



}
