import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by grantdeshazer on 3/16/16.
 *
 * Custom loggger formater.  Data recorded first
 * timestamp recorded second.
 *
 */
public class DataLogFormater extends Formatter{

    @Override
    public String format(LogRecord record){
        String out;
        long t = System.currentTimeMillis();
        out = record.getMessage() + " : " + Long.toString(t);
        return out;
    }

}
