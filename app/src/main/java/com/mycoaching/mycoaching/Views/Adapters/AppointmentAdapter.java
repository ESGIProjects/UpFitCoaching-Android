package com.mycoaching.mycoaching.Views.Adapters;

/**
 * Created by kevin on 28/04/2018.
 */

/*
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private List<Event> listEvents;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, address;

        public MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            address = view.findViewById(R.id.address);
        }
    }

    public AppointmentAdapter(List<Event> listEvents) {
        this.listEvents = listEvents;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = listEvents.get(position);
        holder.date.setText(event.getDate());
        holder.address.setText(event.getAddress());
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }
}
*/
