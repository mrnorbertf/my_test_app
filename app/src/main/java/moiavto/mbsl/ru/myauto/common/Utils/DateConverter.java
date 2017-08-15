package moiavto.mbsl.ru.myauto.common.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fedor on 13.07.2017.
 */

public class DateConverter {

    public static String shortDate(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date.getTime());
    }

    public static String serverDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(date.getTime());
    }
}
