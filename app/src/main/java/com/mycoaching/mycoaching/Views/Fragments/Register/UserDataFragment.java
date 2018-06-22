package com.mycoaching.mycoaching.Views.Fragments.Register;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.UserActivity.UserMainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;
import static com.mycoaching.mycoaching.Util.Constants.DATE_FORMATTER;

public class UserDataFragment extends Fragment {

    private Bundle b;
    private ProgressDialog pd;
    private Intent i;
    private Realm realm;
    private String sex;
    private SimpleDateFormat formatterDate = new SimpleDateFormat(DATE_FORMATTER, Locale.getDefault());

    View v;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.birthDate)
    TextView birthDate;

    @OnClick(R.id.birthDate)
    void setDate(final TextView tv){
        DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.customPicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                Date date = cal.getTime();
                tv.setText(formatterDate.format(date));
            }
        },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)
                ,Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

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
            try{
                if(formatterDate.parse(birthDate.getText().toString()).compareTo(formatterDate.parse(getDate())) > 0){
                    Toast.makeText(getContext(),"Votre date de naissance est supérieure à la date actuelle...",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else{
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
                                        performTransition(getActivity(),i, R.animator.slide_from_left, R.animator.slide_to_right);
                                        Toast.makeText(getContext(), "Compte créé !", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            catch (ParseException pe){
                pe.printStackTrace();
            }
        }
        else{
            Toast.makeText(getContext(), "Il manque un champs !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_userdata, container, false);
        ButterKnife.bind(this, v);
        b = getArguments();
        return v;
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
                ur.setBirthDate(ar.getUr().getBirthDate());
                ur.setAddress(ar.getUr().getAddress());
                ur.setIdCoach(ar.getUr().getCoach().getId());
                ur.setMailCoach(ar.getUr().getCoach().getMail());
                ur.setFirstNameCoach(ar.getUr().getCoach().getFirstName());
                ur.setLastNameCoach(ar.getUr().getCoach().getLastName());
                ur.setCityCoach(ar.getUr().getCoach().getCity());
                ur.setPhoneNumberCoach(ar.getUr().getCoach().getPhoneNumber());
                ur.setTypeCoach(ar.getUr().getCoach().getType());
                ur.setAddressCoach(ar.getUr().getCoach().getAddress());
            }
        });
    }
}
