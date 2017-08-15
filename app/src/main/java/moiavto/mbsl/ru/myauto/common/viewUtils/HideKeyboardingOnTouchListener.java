package moiavto.mbsl.ru.myauto.common.viewUtils;

import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Fedor on 11.07.2017.
 */

public class HideKeyboardingOnTouchListener implements View.OnTouchListener {
    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;
    private HideKeyboardListener hideKeyboard;

    public HideKeyboardingOnTouchListener(HideKeyboardListener hideKeyboard) {
        this.hideKeyboard = hideKeyboard;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {
                    hideKeyboard.hideKeyboard();
                    return true;
                }
            }
        }
        return false;
    }

    public interface HideKeyboardListener {
        void hideKeyboard();
    }

}
