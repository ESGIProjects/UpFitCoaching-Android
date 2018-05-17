package com.mycoaching.mycoaching.Fragments.UserMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.Views.Adapters.TopicAdapter;
import com.mycoaching.mycoaching.Models.Topic;
import com.mycoaching.mycoaching.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by kevin on 28/04/2018.
 */


public class ForumFragment extends Fragment {

    View v;
    List<Topic> listTopic = new ArrayList<>();
    RecyclerView rv;
    TopicAdapter ta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_forum, container, false);
        ButterKnife.bind(this,v);

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.topic_list);
        ta = new TopicAdapter(listTopic);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        rv.setAdapter(ta);

        prepareData();

        return v;
    }

    private void prepareData(){
        Topic t = new Topic(R.drawable.ic_trending_up_black_24dp, "Motivation");
        listTopic.add(t);
        t = new Topic(R.drawable.ic_fitness_center_black_24dp, "Conseils sportifs");
        listTopic.add(t);
        t = new Topic(R.drawable.ic_forum_black_24dp, "Venez discuter !");
        listTopic.add(t);
        ta.notifyDataSetChanged();
    }

}
