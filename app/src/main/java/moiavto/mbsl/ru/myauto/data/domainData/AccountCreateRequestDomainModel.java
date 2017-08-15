package moiavto.mbsl.ru.myauto.data.domainData;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountCreateRequestModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fedor on 26.06.2017.
 */

public class AccountCreateRequestDomainModel
        extends BaseObservable {
    @Bindable
    public final ObservableField<String> phoneNumber = new ObservableField<>();
    @Bindable
    public final ObservableField<Boolean> isConditionsAccept = new ObservableField<>();

    public AccountCreateRequestDomainModel(String phoneNumber, Boolean isConditionsAccept) {
        this.phoneNumber.set(phoneNumber);
        this.isConditionsAccept.set(isConditionsAccept);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountCreateRequestDomainModel {\n");
        sb.append("  phoneNumber: ").append(phoneNumber.get()).append("\n");
        sb.append("  isConditionsAccept: ").append(isConditionsAccept.get()).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    public AccountCreateRequestModel convert() {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(phoneNumber.get());
        String finalPhone = m.replaceAll("");

        return new AccountCreateRequestModel(finalPhone, null);
    }

}
