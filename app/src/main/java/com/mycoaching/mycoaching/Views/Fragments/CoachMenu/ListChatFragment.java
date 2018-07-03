package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.Contact;
import com.mycoaching.mycoaching.Models.Realm.Message;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Singletons.OkHttpSingleton;
import com.mycoaching.mycoaching.Views.Adapters.ContactAdapter;
import com.mycoaching.mycoaching.Views.Fragments.Common.ChatFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
 * Created by kevin on 17/05/2018.
 */

public class ListChatFragment extends Fragment implements ContactAdapter.OnClick {

    private ContactAdapter ca;
    private List<Contact> lc = new ArrayList<>();
    private ArrayList<Message> lm = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    public static boolean isActive = false;
    private Realm r;
    private UserRealm ur;
    private WebSocket ws;
    private Request request;
    View v;
    RecyclerView rv;
    Contact c;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isActive = true;
        v = inflater.inflate(R.layout.fragment_list_contact, container, false);
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = v.findViewById(R.id.listContact);

        ca = new ContactAdapter(lc);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ca);

        request = new Request.Builder().url(WEB_SOCKET_ENDPOINT + ur.getId()).build();
        ws = OkHttpSingleton.getInstance().newWebSocket(request, new CustomWebSocketListener());

        getContacts();

        ca.setOnClick(this);

        return v;
    }

    public WebSocket getWs() {
        return ws;
    }

    private void getContacts() {
        ApiCall.getConversation(Integer.valueOf(ur.getId()), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                lm.addAll(ar.getListMessage());
                ids.add(Integer.valueOf(ur.getId()));
                r.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmList<Message> messages = new RealmList<>();
                        messages.addAll(lm);
                        r.insert(messages);
                    }
                });
                updateContacts();
            }
        });
    }

    public void updateContacts() {
        for (Message m : lm) {
            if (!ids.contains(Integer.valueOf(m.getSender().getId()))) {
                c = new Contact(m.getSender().getFirstName(), m.getSender().getLastName(), m.getContent(), m.getSender().getId(), m.getSender().getMail());
                ids.add(Integer.valueOf(m.getSender().getId()));
                lc.add(c);
            } else if (!ids.contains(Integer.valueOf(m.getReceiver().getId()))) {
                c = new Contact(m.getReceiver().getFirstName(), m.getReceiver().getLastName(), m.getContent(), m.getReceiver().getId(), m.getReceiver().getMail());
                ids.add(Integer.valueOf(m.getReceiver().getId()));
                lc.add(c);
            }
        }
        r = Realm.getDefaultInstance();
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(r.where(Contact.class).findFirst() != null){
                    r.delete(Contact.class);
                }
                RealmList<Contact> contacts = new RealmList<>();
                contacts.addAll(lc);
                r.insert(contacts);
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ca.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        String id = lc.get(position).getId();
        ArrayList<Message> listSpecificMessages = new ArrayList<>();
        for (Message m : lm) {
            if (m.getSender().getId().equals(id) || m.getReceiver().getId().equals(id)) {
                listSpecificMessages.add(m);
            }
        }
        fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle b = new Bundle();
        b.putParcelableArrayList("listMessages", listSpecificMessages);
        ChatFragment cf = new ChatFragment();
        cf.setArguments(b);
        ft.hide(getFragmentManager().findFragmentByTag("LCF"));
        ft.add(R.id.container, cf, "MESSAGES");
        ft.commit();
    }

    public class CustomWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket ws, Response r) {
            Log.i("LCF ws is open :", r.toString());
        }

        @Override
        public void onMessage(WebSocket ws, String text) {
            Log.i("Message on LCF :", text);
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
                if (getFragmentManager().findFragmentByTag("MESSAGES") != null) {
                    ChatFragment cf = (ChatFragment) getFragmentManager().findFragmentByTag("MESSAGES");
                    cf.addMessageToList(String.valueOf(sender.getInt("id")),
                            String.valueOf(receiver.getInt("id")), sender.getString("firstName"),
                            sender.getString("lastName"), receiver.getString("firstName"),
                            receiver.getString("lastName"), content,sender.getString("mail"),
                            receiver.getString("mail"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            Log.i("CLOSE LCF ws : ", code + " & " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            ws.close(1000,"Network issue");
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ws = OkHttpSingleton.getInstance().newWebSocket(request, new CustomWebSocketListener());
                }
            },WEB_SOCKET_TIMER);
            t.printStackTrace();
        }
    }

    public void addMessageToList(String senderID, String receiverID, String firstNameS, String lastNameS,
                                 String firstNameR, String lastNameR, String content,String emailSender,
                                 String emailReceiver) {
        UserRetrofit sender = new UserRetrofit(senderID, null, emailSender, firstNameS,
                lastNameS, null, null, null, null, null, null);
        UserRetrofit receiver = new UserRetrofit(receiverID, null, emailReceiver, firstNameR, lastNameR,
                null, null, null, null, null, null);
        Message m = new Message(null, sender, receiver, getDate(), content);
        lm.add(0, m);
        lc.clear();
        ids.clear();
        ids.add(Integer.valueOf(senderID));
        updateContacts();
    }
}
