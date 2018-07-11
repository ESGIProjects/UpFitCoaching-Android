package com.mycoaching.mycoaching.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Retrofit.Exercise;
import com.mycoaching.mycoaching.R;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * Created by kevin on 08/07/2018.
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    private List<Exercise> listExercise;
    Context c;
    private OnClick onClick;

    public interface OnClick {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView exercise_name, time, serie, rep, intensity;

        public MyViewHolder(View view) {
            super(view);
            exercise_name = view.findViewById(R.id.exercise_name);
            time = view.findViewById(R.id.time);
            serie = view.findViewById(R.id.serie);
            rep = view.findViewById(R.id.rep);
            intensity = view.findViewById(R.id.intensity);
        }
    }

    public ExerciseAdapter(List<Exercise> listExercise, Context c) {
        this.listExercise = listExercise;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Exercise exercise = listExercise.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
        holder.exercise_name.setText(exercise.getExercise());
        if(exercise.getDuration() != null){
            LocalTime lt = new LocalTime(0, 0);
            lt = lt.plusSeconds(exercise.getDuration());
            String time = DateTimeFormat.forPattern("HH:mm:ss").print(lt);
            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText(c.getResources().getString(R.string.time_exercise,time));
        }
        if(exercise.getSeries() != null){
            holder.serie.setVisibility(View.VISIBLE);
            holder.serie.setText(c.getResources().getString(R.string.serie_exercise,exercise.getSeries()));
        }
        if(exercise.getRepetitions() != null){
            holder.rep.setVisibility(View.VISIBLE);
            holder.rep.setText(String.valueOf(c.getResources().getString(R.string.rep_exercise,exercise.getRepetitions())));
        }
        if(exercise.getIntensity() != null){
            holder.intensity.setVisibility(View.VISIBLE);
            switch(exercise.getIntensity()){
                case 0 :
                holder.intensity.setText(c.getResources().getString(R.string.intensity_exercise,"Faible"));
                break;
                case 1 :
                    holder.intensity.setText((c.getResources().getString(R.string.intensity_exercise,"Moyenne")));
                    break;
                case 2 :
                    holder.intensity.setText((c.getResources().getString(R.string.intensity_exercise,"Bonne")));
                    break;
                default:
                    holder.intensity.setText((c.getResources().getString(R.string.intensity_exercise,"Très bonne")));
            }
        }

    }

    public void setOnClick(OnClick oc) {
        this.onClick = oc;
    }

    @Override
    public int getItemCount() {
        return listExercise.size();
    }

    public void deleteItem(int index) {
        listExercise.remove(index);
        notifyItemRemoved(index);
    }
}
