package androiddeveloper.tarunkumar.userlistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CreateUser implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("job")
    private String job;

    @SerializedName("id")
    private String id;

    @SerializedName("createdAt")
    private String createdAt;


    private CreateUser(Parcel in) {
        name = in.readString();
        job = in.readString();
        id = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<CreateUser> CREATOR = new Creator<CreateUser>() {
        @Override
        public CreateUser createFromParcel(Parcel in) {
            return new CreateUser(in);
        }

        @Override
        public CreateUser[] newArray(int size) {
            return new CreateUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(job);
        parcel.writeString(id);
        parcel.writeString(createdAt);
    }
}
