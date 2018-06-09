package com.mycoaching.mycoaching.Models.Retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by kevin on 20/04/2018.
 */

public class UserRetrofit extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("mail")
    @Expose
    private String mail;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("birthDate")
    @Expose
    private String birthDate;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("coach")
    @Expose
    private UserRetrofit coach;

    public UserRetrofit(String id, Integer type, String mail, String firstName, String lastName, String birthDate,
                        String address, String city, String phoneNumber, UserRetrofit coach) {
        this.id = id;
        this.type = type;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.coach = coach;
    }

    public UserRetrofit(Parcel in) {
        this.id = in.readString();
        this.type = in.readInt();
        this.mail = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.birthDate = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.phoneNumber = in.readString();
        this.coach = in.readParcelable(UserRetrofit.class.getClassLoader());
    }

    public UserRetrofit(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRetrofit getCoach() {
        return coach;
    }

    public void setCoach(UserRetrofit coach) {
        this.coach = coach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<UserRetrofit> CREATOR = new Parcelable.Creator<UserRetrofit>() {
        @Override
        public UserRetrofit createFromParcel(Parcel source) {
            return new UserRetrofit(source);
        }

        @Override
        public UserRetrofit[] newArray(int size) {
            return new UserRetrofit[size];
        }
    };
}
