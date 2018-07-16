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

import org.joda.time.LocalDate;
import org.joda.time.Years;

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
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
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
        if (isNetworkAvailable(getContext())) {
            if (checkFields(firstName.getText().toString(), lastName.getText().toString(),
                    birthDate.getText().toString(), city.getText().toString(), phoneNumber.getText().toString()) &&
                    sex != null){
                pd = new ProgressDialog(getContext(), R.style.StyledDialog);
                pd.setMessage("Création du compte en cours...");
                pd.setCancelable(false);
                pd.show();
                try{
                    String splitBirth[] = birthDate.getText().toString().split("-");
                    LocalDate birth = new LocalDate(Integer.valueOf(splitBirth[0])
                            ,Integer.valueOf(splitBirth[1]),Integer.valueOf(splitBirth[2]));
                    LocalDate now = new LocalDate();
                    Years age = Years.yearsBetween(birth,now);
                    if(formatterDate.parse(birthDate.getText().toString()).compareTo(formatterDate.parse(getDate())) > 0){
                        Toast.makeText(getContext(),"Votre date de naissance est supérieure à la date actuelle",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                    else if(age.getYears() < 18 || age.getYears() > 100){
                        Toast.makeText(getContext(),"Vous devez avoir entre 18 et 100 ans pour utiliser cette application",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                    else if(phoneNumber.getText().toString().length() != 10){
                        Toast.makeText(getContext(),"Le format du numéro de téléphone est invalide",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                    else{
                        ApiCall.signUp(b.getString("type"), b.getString("mail"),
                                b.getString("password"), firstName.getText().toString(), lastName.getText().toString(), sex,
                                birthDate.getText().toString(), city.getText().toString(), null, phoneNumber.getText().toString(),
                                new ServiceResultListener() {
                                    @Override
                                    public void onResult(ApiResults ar) {
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
                        pd.dismiss();
                    }
                }
                catch (ParseException pe){
                    pe.printStackTrace();
                }
            }
            else{
                Toast.makeText(getContext(), R.string.missing_fields, Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
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
                UserRealm ur = realm.createObject(UserRealm.class, ar.getUt().getId());
                ur.setCity(city.getText().toString().substring(0,1).toUpperCase()
                        + city.getText().toString().substring(1).toLowerCase());
                ur.setFirstName(firstName.getText().toString().substring(0,1).toUpperCase()
                + firstName.getText().toString().substring(1).toLowerCase());
                ur.setLastName(lastName.getText().toString().substring(0,1).toUpperCase()
                        + lastName.getText().toString().substring(1).toLowerCase());
                ur.setSex(sex);
                ur.setToken(ar.getUt().getToken());
                ur.setBirthDate(birthDate.getText().toString());
                ur.setMail(b.getString("mail"));
                ur.setPhoneNumber(phoneNumber.getText().toString());
                ur.setType(Integer.valueOf(b.getString("type")));
                ur.setIdCoach(ar.getUt().getUrc().getId());
                ur.setMailCoach(ar.getUt().getUrc().getMail());
                ur.setFirstNameCoach(ar.getUt().getUrc().getFirstName());
                ur.setLastNameCoach(ar.getUt().getUrc().getLastName());
                ur.setSexCoach(ar.getUt().getUrc().getSex());
                ur.setCityCoach(ar.getUt().getUrc().getCity());
                ur.setPhoneNumberCoach(ar.getUt().getUrc().getPhoneNumber());
                ur.setTypeCoach(ar.getUt().getUrc().getType());
                ur.setAddressCoach(ar.getUt().getUrc().getAddress());
            }
        });
    }
}
