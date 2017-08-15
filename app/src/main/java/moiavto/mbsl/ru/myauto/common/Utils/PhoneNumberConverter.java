package moiavto.mbsl.ru.myauto.common.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fedor on 18.07.2017.
 */

public class PhoneNumberConverter {
    public static String conver(String input) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(input);
        return m.replaceAll("");
    }
}
