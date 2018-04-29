package com.mycoaching.mycoaching.Fragments.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Activities.CoachActivity.CoachMainActivity;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Api.ApiResults;
import com.mycoaching.mycoaching.Util.Api.CallService;
import com.mycoaching.mycoaching.Util.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Util.Model.Realm.UserRealm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;

public class CoachDataFragment extends Fragment {

    View v;
    Bundle b;
    ProgressDialog pd = null;
    Intent i;
    Realm realm = null;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @OnClick(R.id.account_creation) void createAccount(){
        if(checkFields(firstName.getText().toString(),lastName.getText().toString(),
                city.getText().toString(),address.getText().toString(),phoneNumber.getText().toString())){
            pd = new ProgressDialog(getContext(),R.style.StyledDialog);
            pd.setMessage("Création du compte en cours...");
            pd.show();
            CallService.signUp(b.getString("type"), b.getString("mail"),
                    b.getString("password"), firstName.getText().toString(), lastName.getText().toString(),
                    null, city.getText().toString(), address.getText().toString(), phoneNumber.getText().toString(),
                    new ServiceResultListener() {
                        @Override
                        public void onResult(final ApiResults ar) {
                            pd.dismiss();
                            if(ar.getResponseCode() == 201){
                                realm = realm.getDefaultInstance();
                                executeTransaction(realm,ar);
                                i = new Intent(getContext(),CoachMainActivity.class);
                                performTransition(i,R.animator.slide_from_left,R.animator.slide_to_right);
                                Toast.makeText(getContext(),"Compte créé !",Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(getContext(),R.string.error,Toast.LENGTH_LONG).show();
                            }
                        }});
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_coachdata, container, false);
        ButterKnife.bind(this,v);
        b = getArguments();
        return v;
    }

    public void performTransition(Intent i, int from, int to){
        startActivity(i);
        getActivity().overridePendingTransition(from,to);
    }

    public void executeTransaction(Realm r, final ApiResults ar){
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealm ur = realm.createObject(UserRealm.class,ar.getUr().getId());
                ur.setCity(city.getText().toString());
                ur.setAddress(address.getText().toString());
                ur.setFirstName(firstName.getText().toString());
                ur.setLastName(lastName.getText().toString());
                ur.setMail(b.getString("mail"));
                ur.setPhoneNumber(phoneNumber.getText().toString());
                ur.setType(Integer.valueOf(b.getString("type")));
                ur.setPhoneNumber(phoneNumber.getText().toString());
            }
        });
    }
}
