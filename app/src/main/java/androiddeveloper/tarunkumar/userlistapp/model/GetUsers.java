package androiddeveloper.tarunkumar.userlistapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUsers implements Parcelable {

    @SerializedName("page")
    private int page;

    @SerializedName("per_page")
    private int per_page;

    @SerializedName("total")
    private int total;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("data")
    private List<UserData> userDataList;

    private GetUsers(Parcel in) {
        page = in.readInt();
        per_page = in.readInt();
        total = in.readInt();
        total_pages = in.readInt();
        userDataList = in.createTypedArrayList(UserData.CREATOR);
    }

    public static final Creator<GetUsers> CREATOR = new Creator<GetUsers>() {
        @Override
        public GetUsers createFromParcel(Parcel in) {
            return new GetUsers(in);
        }

        @Override
        public GetUsers[] newArray(int size) {
            return new GetUsers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(per_page);
        parcel.writeInt(total);
        parcel.writeInt(total_pages);
        parcel.writeTypedList(userDataList);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<UserData> getUserDataList() {
        return userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }
}
