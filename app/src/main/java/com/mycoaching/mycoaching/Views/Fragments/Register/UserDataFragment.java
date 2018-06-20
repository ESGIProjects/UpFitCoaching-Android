package com.mycoaching.mycoaching.Views.Fragments.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.UserActivity.UserMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;

public class UserDataFragment extends Fragment {

    View v;
    Bundle b;
    ProgressDialog pd = null;
    Intent i;
    Realm realm = null;
    String sex;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.birthDate)
    EditText birthDate;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @BindView(R.id.woman)
    CheckBox woman;

    @BindView(R.id.man)
    CheckBox man;

    @OnClick(R.id.woman)
    void setWoman(){
        woman.setChecked(true);
        man.setChecked(false);
        sex = "0";
    }

    @OnClick(R.id.man)
    void setMan(){
        man.setChecked(true);
        woman.setChecked(false);
        sex = "1";
    }

    @OnClick(R.id.account_creation)
    void createAccount() {
        if (checkFields(firstName.getText().toString(), lastName.getText().toString(),
                birthDate.getText().toString(), city.getText().toString(), phoneNumber.getText().toString()) &&
                sex != null){
            pd = new ProgressDialog(getContext(), R.style.StyledDialog);
            pd.setMessage("Création du compte en cours...");
            pd.show();
            ApiCall.signUp(b.getString("type"), b.getString("mail"),
                    b.getString("password"), firstName.getText().toString(), lastName.getText().toString(), sex,
                    birthDate.getText().toString(), city.getText().toString(), null, phoneNumber.getText().toString(),
                    new ServiceResultListener() {
                        @Override
                        public void onResult(ApiResults ar) {
                            pd.dismiss();
                            if (ar.getResponseCode() == 201) {
                                realm = Realm.getDefaultInstance();
                                executeTransaction(realm, ar);
                                i = new Intent(getContext(), UserMainActivity.class);
                                performTransition(i, R.animator.slide_from_left, R.animator.slide_to_right);
                                Toast.makeText(getContext(), "Compte créé !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        Toast.makeText(getContext(), "Il manque un champs !", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_userdata, container, false);
        ButterKnife.bind(this, v);
        b = getArguments();
        return v;
    }

    public void performTransition(Intent i, int from, int to) {
        startActivity(i);
        getActivity().overridePendingTransition(from, to);
    }

    public void executeTransaction(Realm r, final ApiResults ar) {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealm ur = realm.createObject(UserRealm.class, ar.getUr().getId());
                ur.setCity(city.getText().toString());
                ur.setFirstName(firstName.getText().toString());
                ur.setLastName(lastName.getText().toString());
                ur.setSex(sex);
                ur.setBirthDate(birthDate.getText().toString());
                ur.setMail(b.getString("mail"));
                ur.setPhoneNumber(phoneNumber.getText().toString());
                ur.setType(Integer.valueOf(b.getString("type")));
            }
        });
    }
}
