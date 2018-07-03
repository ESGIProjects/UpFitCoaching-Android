package com.mycoaching.mycoaching.Models.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kevin on 20/04/2018.
 */

public class UserRealm extends RealmObject {

    @PrimaryKey
    String id;

    public UserRealm(){

    }

    public UserRealm(String id, String mail, String firstName, String lastName, String sex, String birthDate,
                     String city, String phoneNumber){
        this.id = id;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    private String mail, firstName, lastName, sex, birthDate, address, city, phoneNumber;

    private String idCoach, mailCoach, firstNameCoach, lastNameCoach, sexCoach, addressCoach,
            cityCoach, phoneNumberCoach;

    private int type, typeCoach;

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailCoach() {
        return mailCoach;
    }

    public void setMailCoach(String mailCoach) {
        this.mailCoach = mailCoach;
    }

    public String getFirstNameCoach() {
        return firstNameCoach;
    }

    public void setFirstNameCoach(String firstNameCoach) {
        this.firstNameCoach = firstNameCoach;
    }

    public String getLastNameCoach() {
        return lastNameCoach;
    }

    public void setLastNameCoach(String lastNameCoach) {
        this.lastNameCoach = lastNameCoach;
    }

    public String getAddressCoach() {
        return addressCoach;
    }

    public void setAddressCoach(String addressCoach) {
        this.addressCoach = addressCoach;
    }

    public String getCityCoach() {
        return cityCoach;
    }

    public void setCityCoach(String cityCoach) {
        this.cityCoach = cityCoach;
    }

    public String getPhoneNumberCoach() {
        return phoneNumberCoach;
    }

    public void setPhoneNumberCoach(String phoneNumberCoach) {
        this.phoneNumberCoach = phoneNumberCoach;
    }

    public String getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(String idCoach) {
        this.idCoach = idCoach;
    }

    public int getTypeCoach() {
        return typeCoach;
    }

    public void setTypeCoach(int typeCoach) {
        this.typeCoach = typeCoach;
    }

    public String getSexCoach() {
        return sexCoach;
    }

    public void setSexCoach(String sexCoach) {
        this.sexCoach = sexCoach;
    }
}
