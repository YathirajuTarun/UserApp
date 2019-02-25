package androiddeveloper.tarunkumar.userlistapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("avatar")
    private String avatar;

    protected UserData(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(avatar);
    }

    public static DiffUtil.ItemCallback<UserData> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserData>() {

        @Override
        public boolean areItemsTheSame(@NonNull UserData oldUser, @NonNull UserData newUser) {
            return oldUser.id == newUser.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserData oldUser, @NonNull UserData newUser) {
            return oldUser.equals(newUser);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        UserData userData = (UserData) obj;
        return userData.id == this.id;
    }

    public int getId() {
        return id;
    }

    private void setId() {

    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

