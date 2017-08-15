package moiavto.mbsl.ru.myauto.common.viewUtils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import moiavto.mbsl.ru.myauto.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fedor on 07.07.2017.
 */

public class DataBindingsUtils {
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String v) {
        Glide.with(imageView.getContext())
                .load(v)
                .error(R.drawable.temp_image_solid)
                .into(imageView);
    }

    @BindingAdapter("bind:imageCircleUrl")
    public static void loadCircleImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .override(250, 250)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }

    @BindingAdapter("bind:dateText")
    public static void setDateText(TextView textField, Date date) {
        String fieldText = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date.getTime());
        textField.setText(fieldText);
    }

    @BindingAdapter("bind:dateTextWithDots")
    public static void setDateTextWithDots(TextView textField, Date date) {
        String fieldText = new SimpleDateFormat("dd.MMMM.yyyy", Locale.getDefault()).format(date.getTime());
        textField.setText(fieldText);
    }

    @BindingAdapter("bind:dateTextShortWithDots")
    public static void setDateTextShortWithDots(TextView textField, Date date) {
        if (date == null)
            date = new Date();

        String fieldText = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date.getTime());
        textField.setText(fieldText);
    }

    @BindingAdapter("bind:imageDrawable")
    public static void loadImageFromDrawable(ImageView imageView, int id) {
        Glide.with(imageView.getContext())
                .load(featureIdToDrawable(id))
                .into(imageView);
    }

    private static int featureIdToDrawable(int id) {
        switch (id) {
            case 3:
                return R.drawable.icon_cofee;
            case 9:
                return R.drawable.icon_wifi;
            default:
                return R.drawable.icon_wifi;
        }
    }
}
