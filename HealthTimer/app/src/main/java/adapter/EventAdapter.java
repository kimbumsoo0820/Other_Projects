package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthtimer.Add_event;
import com.example.healthtimer.R;
import java.util.ArrayList;



    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

        private ArrayList<Add_event> arrayList;
        private Context context;


        public EventAdapter(ArrayList<Add_event> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_item, parent, false);
            EventViewHolder holder = new EventViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            holder.tv_events.setText(arrayList.get(position).getEvents());
        }

        @Override
        public int getItemCount() {
            return (arrayList != null ? arrayList.size() : 0);
        }

        public class EventViewHolder extends RecyclerView.ViewHolder {

            TextView tv_events;

            public EventViewHolder(@NonNull View itemView) {
                super(itemView);

                this.tv_events = itemView.findViewById(R.id.tv_events);
            }
        }



    }


