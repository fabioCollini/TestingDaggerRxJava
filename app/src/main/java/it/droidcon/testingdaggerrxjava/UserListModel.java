package it.droidcon.testingdaggerrxjava;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

public class UserListModel implements Parcelable {
    public final ObservableField<String> userList = new ObservableField<>();

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userList.get());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserListModel> CREATOR = new Creator<UserListModel>() {
        @Override
        public UserListModel createFromParcel(Parcel in) {
            UserListModel userListModel = new UserListModel();
            userListModel.userList.set(in.readString());
            return userListModel;
        }

        @Override
        public UserListModel[] newArray(int size) {
            return new UserListModel[size];
        }
    };
}
