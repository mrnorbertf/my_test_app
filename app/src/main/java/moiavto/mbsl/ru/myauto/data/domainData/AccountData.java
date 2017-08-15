package moiavto.mbsl.ru.myauto.data.domainData;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import moiavto.mbsl.ru.myauto.common.Utils.PhoneNumberConverter;

/**
 * Created by Fedor on 17.07.2017.
 */

public class AccountData extends BaseObservable {
    @Bindable
    public final ObservableField<String> username = new ObservableField<>();
    @Bindable
    public final ObservableField<String> phone = new ObservableField<>();
    //    @Bindable
    //    public final ObservableField<String> email = new ObservableField<>();
    //    @Bindable
    //    public final ObservableField<String> avatarUrl = new ObservableField<>();

    public AccountData(String username, String phone
            //            , String email, String avatarUrl
    ) {
        this.username.set(username);
        this.phone.set(phone);
        //        this.email.set(email);
        //        this.avatarUrl.set(avatarUrl);
    }

    public String getPhone() {
        return PhoneNumberConverter.conver(phone.get());
    }

    public String getUserName() {
        return username.get();
    }
}
