package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.Message;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Singletons.OkHttpSingleton;
import com.mycoaching.mycoaching.Views.Adapters.MessageAdapter;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ListChatFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.getJSONFromString;
import static com.mycoaching.mycoaching.Util.Constants.WEB_SOCKET_ENDPOINT;
import static com.mycoaching.mycoaching.Util.Constants.WEB_SOCKET_TIMER;

/**
 * Created by kevin on 28/04/2018.
 */

public class ChatFragment extends Fragment {

    private MessageAdapter ma;
    private List<Message> lm = new ArrayList<>();
    private Realm r;
    private UserRealm ur;
    private WebSocket ws = null;
    private Request request;
    private boolean isCoach = false;
    public static boolean isActive = false;
    View v;
    RecyclerView rv;
    ProgressDialog pd;

    @BindView(R.id.input)
    EditText et;

    @OnClick(R.id.send)
    void sendMessage() {
        if (!et.getText().toString().equals("")) {
            JSONObject object = new JSONObject();
            try {
                object.put("date",getDate());
                JSONObject sender = new JSONObject();
                sender.put("id", Integer.valueOf(ur.getId()));
                sender.put("type", Integer.valueOf(ur.getType()));
                sender.put("mail", ur.getMail());
                sender.put("firstName", ur.getFirstName());
                sender.put("lastName", ur.getLastName());
                sender.put("sex", Integer.valueOf(ur.getSex()));
                sender.put("city", ur.getCity());
                sender.put("phoneNumber", ur.getPhoneNumber());
                if (!isCoach) {
                    sender.put("birthDate", ur.getBirthDate());
                } else {
                    sender.put("address", ur.getAddress());
                }
                object.put("sender", sender);

                JSONObject receiver = new JSONObject();

                if (!isCoach) {
                    receiver.put("id", Integer.valueOf(ur.getIdCoach()));
                    receiver.put("type", 2);
                    receiver.put("mail", ur.getMailCoach());
                    receiver.put("firstName", ur.getFirstNameCoach());
                    receiver.put("lastName", ur.getLastNameCoach());
                    receiver.put("sex",Integer.valueOf(ur.getSexCoach()));
                    receiver.put("city", ur.getCityCoach());
                    receiver.put("phoneNumber", ur.getPhoneNumberCoach());
                    receiver.put("address", ur.getAddressCoach());
                } else {
                    receiver.put("id", Integer.valueOf(lm.get(lm.size() - 1).getSender().getId()));
                    receiver.put("type", lm.get(lm.size() - 1).getSender().getType());
                    receiver.put("mail", lm.get(lm.size() - 1).getSender().getMail());
                    receiver.put("firstName", lm.get(lm.size() - 1).getSender().getFirstName());
                    receiver.put("lastName", lm.get(lm.size() - 1).getSender().getLastName());
                    receiver.put("sex", Integer.valueOf(lm.get(lm.size() - 1).getSender().getSex()));
                    receiver.put("city", lm.get(lm.size() - 1).getSender().getCity());
                    receiver.put("phoneNumber", lm.get(lm.size() - 1).getSender().getPhoneNumber());
                    receiver.put("birthDate", lm.get(lm.size() - 1).getSender().getBirthDate());
                }
                object.put("receiver", receiver);
                object.put("content", et.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(isCoach) {
                ListChatFragment lcf = (ListChatFragment) getActivity().getSupportFragmentManager().findFragmentByTag("LCF");
                lcf.getWs().send(object.toString());
                lcf.addMessageToList(ur.getId(), lm.get(lm.size() - 1).getSender().getId(), ur.getFirstName(),
                        ur.getLastName(), lm.get(lm.size() - 1).getSender().getFirstName(),
                        lm.get(lm.size() - 1).getSender().getLastName(), et.getText().toString(),ur.getMail(),
                        lm.get(lm.size() - 1).getSender().getMail());
                addMessageToList(ur.getId(), lm.get(lm.size() - 1).getSender().getId(), ur.getFirstName(),
                        ur.getLastName(), lm.get(lm.size() - 1).getSender().getFirstName(),
                        lm.get(lm.size() - 1).getSender().getLastName(), et.getText().toString(),ur.getMail(),
                        lm.get(lm.size() - 1).getSender().getMail());
            } else {
                ws.send(object.toString());
                Log.i("WS TEST : ",object.toString());
                addMessageToList(ur.getId(), ur.getIdCoach(), ur.getFirstName(), ur.getLastName(),
                        ur.getFirstNameCoach(), ur.getLastNameCoach(), et.getText().toString(),ur.getMail(),
                        ur.getMailCoach());
            }
            et.getText().clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isActive = true;
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, v);
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = v.findViewById(R.id.list);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            lm.addAll(bundle.<Message>getParcelableArrayList("listMessages"));
            ma = new MessageAdapter(lm);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ma.notifyDataSetChanged();
                }
            });
            isCoach = true;
        } else {
            request = new Request.Builder().url(WEB_SOCKET_ENDPOINT + ur.getId()).addHeader("Authorization",
                    "Bearer " + ur.getToken()).build();
            ws = OkHttpSingleton.getInstance().newWebSocket(request, new CustomWSListener());
            getConversation();
            ma = new MessageAdapter(lm);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ma);
        return v;
    }

    public WebSocket getWs() {
        return ws;
    }

    public void getConversation() {
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setMessage("Récupération des messages en cours...");
        pd.show();
        ApiCall.getConversation("Bearer " + ur.getToken(),Integer.valueOf(ur.getId()), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if (ar.getResponseCode() == 200) {
                    lm.addAll(ar.getListMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ma.notifyDataSetChanged();
                        }
                    });
                    r.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            ur.setMailCoach(lm.get(lm.size()-1).getReceiver().getMail());
                            ur.setFirstNameCoach(lm.get(lm.size()-1).getReceiver().getFirstName());
                            ur.setLastNameCoach(lm.get(lm.size()-1).getReceiver().getLastName());
                            ur.setCityCoach(lm.get(lm.size()-1).getReceiver().getCity());
                            ur.setAddressCoach(lm.get(lm.size()-1).getReceiver().getAddress());
                            ur.setPhoneNumberCoach(lm.get(lm.size()-1).getReceiver().getPhoneNumber());
                            RealmList<Message> messages = new RealmList<>();
                            messages.addAll(lm);
                            r.insert(messages);
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Veuillez réessayer ultérieurement", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pd.dismiss();
    }

    public void addMessageToList(String senderID, String receiverID, String firstNameS, String lastNameS,
                                 String firstNameR, String lastNameR, String content, String emailSender,
                                 String emailReceiver) {
        UserRetrofit sender = new UserRetrofit(senderID, null, emailSender, firstNameS,
                lastNameS, null, null, null, null, null, null);
        UserRetrofit receiver = new UserRetrofit(receiverID, null, emailReceiver, firstNameR, lastNameR,
                null, null, null, null, null, null);
        Message m = new Message(null, sender, receiver, getDate(), content);
        lm.add(0, m);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ma.notifyDataSetChanged();
            }
        });
    }


    private class CustomWSListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket ws, Response r) {
            Log.i("CF ws is open : ", r.toString());
        }

        @Override
        public void onMessage(WebSocket ws, String text) {
            Log.i("Message on CF :", text);
            try {
                JSONObject message = getJSONFromString(text);
                JSONObject sender = message.getJSONObject("sender");
                JSONObject receiver = message.getJSONObject("receiver");
                String content = message.getString("content");
                addMessageToList(String.valueOf(sender.getInt("id")),
                        String.valueOf(receiver.getInt("id")), sender.getString("firstName"),
                        sender.getString("lastName"), receiver.getString("firstName"),
                        receiver.getString("lastName"), content,sender.getString("mail"),
                        receiver.getString("mail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            Log.i("CLOSE CF ws : ", code + " & " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            ws.close(1000,"Network issue");
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ws = OkHttpSingleton.getInstance().newWebSocket(request, new CustomWSListener());
                }
            },WEB_SOCKET_TIMER);
            t.printStackTrace();
        }
    }
}