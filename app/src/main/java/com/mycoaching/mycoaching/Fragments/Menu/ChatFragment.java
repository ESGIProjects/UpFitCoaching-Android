package com.mycoaching.mycoaching.Fragments.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Api.WebSocketSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import okhttp3.WebSocket;

import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;

public class ChatFragment extends Fragment{

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this,v);


        WebSocketSingleton.getInstance().send(sendMessage());

        return v;
    }

    public String sendMessage(){
        JSONObject object = new JSONObject();
        try{
            JSONObject sender = new JSONObject();
            sender.put("id",2);
            object.put("sender",sender);

            JSONObject receiver = new JSONObject();
            receiver.put("id",15);
            object.put("receiver",receiver);

            object.put("date", getDate());
            object.put("content", "Test onche 2 : le retour");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }
}
