package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkEmail;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.clearFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getSHAPassword;

/**
 * Created by kevin on 23/06/2018.
 */
public class EditProfileFragment extends Fragment{

    View v;
    Realm realm;
    UserRealm ur;
    Bundle b;

    @BindView(R.id.mail)
    EditText mail;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.addressContainer)
    TextInputLayout til;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @OnClick(R.id.confirm_edit)
    public void edit(){
        if(checkFields(mail.getText().toString(),firstName.getText().toString(),lastName.getText().toString(),
                city.getText().toString(),city.getText().toString(),phoneNumber.getText().toString()) &&
                checkEmail(mail.getText().toString())){
            if(!password.getText().toString().equals("") && !checkPassword(password.getText().toString())){
                Toast.makeText(getContext(),"Ce mot de passe n'est pas valide",Toast.LENGTH_LONG).show();
                clearFields(password);
            }
            else{
                String pwd = null;
                if (checkPassword(password.getText().toString())){
                    pwd = getSHAPassword(password.getText().toString());
                }

                if(b.getBoolean("isCoach",false)){
                    if(!checkFields(address.getText().toString())){
                        Toast.makeText(getContext(),"Veuillez saisir une adresse",Toast.LENGTH_LONG).show();
                    }
                }
                ApiCall.updateUser("Bearer " + ur.getToken(),ur.getId(), mail.getText().toString(), pwd,
                        firstName.getText().toString(), lastName.getText().toString(), city.getText().toString(),
                        phoneNumber.getText().toString(), address.getText().toString(), new ServiceResultListener() {
                            @Override
                            public void onResult(ApiResults ar) {
                                if(ar.getResponseCode() == 200){
                                    executeTransaction(realm);
                                    Toast.makeText(getContext(),"Le profil a été mis à jour !",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        else{
            Toast.makeText(getContext(),"Au moins un des champs n'est pas valide !",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        realm = Realm.getDefaultInstance();
        b = this.getArguments();
        ur = realm.where(UserRealm.class).findFirst();
        mail.setText(ur.getMail());
        firstName.setText(ur.getFirstName());
        lastName.setText(ur.getLastName());
        city.setText(ur.getCity());
        phoneNumber.setText(ur.getPhoneNumber());
        if(b.getBoolean("isCoach",false)){
            til.setVisibility(View.VISIBLE);
            address.setText(ur.getAddress());
        }
        return v;
    }

    public void executeTransaction(Realm r) {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ur.setMail(mail.getText().toString());
                ur.setFirstName(firstName.getText().toString());
                ur.setLastName(lastName.getText().toString());
                ur.setCity(city.getText().toString());
                ur.setPhoneNumber(phoneNumber.getText().toString());
                if(b.getBoolean("isCoach",false)){
                    ur.setAddress(address.getText().toString());
                }
            }
        });
    }
}
