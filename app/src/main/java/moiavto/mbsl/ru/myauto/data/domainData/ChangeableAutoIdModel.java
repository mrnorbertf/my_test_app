package moiavto.mbsl.ru.myauto.data.domainData;

import moiavto.mbsl.ru.myauto.data.serverModel.AutoIdModel;

import java.io.Serializable;

/**
 * Created by Fedor on 17.07.2017.
 */

public class ChangeableAutoIdModel implements Serializable {
    private boolean isEdit;
    private AutoIdModel autoIdModel;

    public ChangeableAutoIdModel(boolean isEdit, AutoIdModel autoIdModel) {
        this.isEdit = isEdit;
        this.autoIdModel = autoIdModel;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public AutoIdModel getAutoIdModel() {
        return autoIdModel;
    }

    public void setAutoIdModel(AutoIdModel autoIdModel) {
        this.autoIdModel = autoIdModel;
    }
}
