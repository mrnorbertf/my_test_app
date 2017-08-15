package moiavto.mbsl.ru.myauto.data.domainData;

import moiavto.mbsl.ru.myauto.data.serverModel.AccountInfoModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AutoListResponseModel;

import java.util.List;

/**
 * Created by Fedor on 17.07.2017.
 */

public class SettingsData {
    private AccountInfoModel user;
    private List<AutoListResponseModel> cars;

    public SettingsData(AccountInfoModel user, List<AutoListResponseModel> cars) {
        this.user = user;
        this.cars = cars;
    }

    public AccountInfoModel getUser() {
        return user;
    }

    public SettingsData setUser(AccountInfoModel user) {
        this.user = user;
        return this;
    }

    public List<AutoListResponseModel> getCars() {
        return cars;
    }

    public SettingsData setCars(List<AutoListResponseModel> cars) {
        this.cars = cars;
        return this;
    }
}
