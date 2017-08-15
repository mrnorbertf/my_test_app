package moiavto.mbsl.ru.myauto.common.viewUtils;

import android.widget.EditText;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

/**
 * Created by Fedor on 17.07.2017.
 */

public class PhoneMaskedTextChangedListener extends MaskedTextChangedListener {
    private static final String PHONE_TEMPLATE = "+7 (9[00]) [000] [00] [00]";
    private static final MaskedTextChangedListener.ValueListener valueListener = (maskFilled, extractedValue) -> {
        Timber.d(String.valueOf(maskFilled));
        Timber.d(extractedValue);
    };

    public PhoneMaskedTextChangedListener(@NotNull EditText field) {
        super(PHONE_TEMPLATE, true, field, null, valueListener);
    }
}
