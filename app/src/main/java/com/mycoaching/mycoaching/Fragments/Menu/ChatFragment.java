package com.mycoaching.mycoaching.Fragments.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Util.Singletons.OkHttpSingleton;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.Adapters.MessageAdapter;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.getJSONFromString;

/**
 * Created by kevin on 28/04/2018.
 */


public class ChatFragment extends Fragment {

    View v;
    RecyclerView rv;
    static MessageAdapter ma;
    static List<Message> lm = new ArrayList<>();
    Realm r;
    UserRealm ur;
    WebSocket ws = null;

    @BindView(R.id.input)
    EditText et;

    private final class CustomWSListener extends WebSocketListener {


        @Override
        public void onOpen(WebSocket ws, Response r){
            Log.i("TEST WS : ", r.toString());
        }

        @Override
        public void onMessage(WebSocket ws, String text){
            Log.i("TEST OM :", text);
            try{
                JSONObject message = getJSONFromString(text);
                JSONObject sender = message.getJSONObject("sender");
                JSONObject receiver = message.getJSONObject("receiver");
                String content = message.getString("content");
                addMessageToList(String.valueOf(sender.getInt("id")),
                        String.valueOf(receiver.getInt("id")),content);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            Log.i("CLOSE: ", code + " & " + reason);
        }

        @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            t.printStackTrace();
        }

    }


    @OnClick(R.id.send) void sendMessage(){
        if(!et.getText().toString().equals("")){
            JSONObject object = new JSONObject();
            try{
                JSONObject sender = new JSONObject();
                sender.put("id",Integer.valueOf(ur.getId()));
                sender.put("type",Integer.valueOf(ur.getType()));
                sender.put("mail",ur.getMail());
                sender.put("firstName",ur.getFirstName());
                sender.put("lastName",ur.getLastName());
                sender.put("city",ur.getCity());
                sender.put("phoneNumber",ur.getPhoneNumber());
                sender.put("birthDate",ur.getBirthDate());
                object.put("sender",sender);

                JSONObject receiver = new JSONObject();
                receiver.put("id",Integer.valueOf(ur.getIdCoach()));
                receiver.put("type",2);
                receiver.put("mail",ur.getMailCoach());
                receiver.put("firstName",ur.getFirstNameCoach());
                receiver.put("lastName",ur.getLastNameCoach());
                receiver.put("city",ur.getCityCoach());
                receiver.put("phoneNumber",ur.getPhoneNumberCoach());
                receiver.put("address",ur.getAddressCoach());
                object.put("receiver",receiver);

                object.put("date", getDate());
                object.put("content", et.getText().toString());
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            ws.send(object.toString());
            addMessageToList(ur.getId(),ur.getIdCoach(),et.getText().toString());
            et.getText().clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
        Log.i("TEST COACH : ", ur.getMailCoach());

        rv = v.findViewById(R.id.list);

        ma = new MessageAdapter(lm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()
                ,LinearLayoutManager.VERTICAL, true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ma);

        getConversation();

        Request request = new Request.Builder().url("ws://212.47.234.147/ws?id=2").build();
        ws = OkHttpSingleton.getInstance().newWebSocket(request, new CustomWSListener());

        ButterKnife.bind(this,v);

        return v;
    }

    public void getConversation(){
        ApiCall.getConversation(ur.getId(), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    lm.addAll(ar.getListMessage());
                    ma.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getContext(),"Veuillez réessayer ultérieurement",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addMessageToList(String senderID, String receiverID, String content) {
        UserRetrofit sender = new UserRetrofit(senderID, null, null, null,
                null, null, null, null, null, null);
        UserRetrofit receiver = new UserRetrofit(receiverID, null, null, null, null,
                null, null, null, null, null);
        Message m = new Message(null, sender, receiver, getDate(), content);
        lm.add(0, m);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ma.notifyDataSetChanged();
            }
        });
    }
}