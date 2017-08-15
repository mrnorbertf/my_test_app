package moiavto.mbsl.ru.myauto.common.viewUtils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import timber.log.Timber;

/**
 * Created by Fedor on 12.07.2017.
 */

public class HideKeyboardMethod {
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText()) {
                Timber.d("keyboard is showing");
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } else {
                Timber.d("keyboard is not showing");
            }
        } catch (NullPointerException e) {
            Timber.d("NullPointerException");
            e.printStackTrace();
        }
    }
}
