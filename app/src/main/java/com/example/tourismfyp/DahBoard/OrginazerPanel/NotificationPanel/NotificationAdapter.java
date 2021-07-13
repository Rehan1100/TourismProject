package com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartModelClass;
import com.example.tourismfyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> implements Filterable {
    private List<NotificationModelClass> modelCLassList;
    List<NotificationModelClass> modelCLassListAll;
    Context context;
    String key;
    public String Roll;

    public NotificationAdapter(List<NotificationModelClass> modelCLassList, Context context) {
        this.modelCLassList = modelCLassList;
        this.modelCLassListAll = new ArrayList<>(modelCLassList);
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationrow, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final String desc = modelCLassList.get(position).getDesc();
        final String id = modelCLassList.get(position).getId();
        final String title = modelCLassList.get(position).getTitle();
        final String status = modelCLassList.get(position).getStatus();
        final String bookusername = modelCLassList.get(position).getBookinguserfirstname();
        final String bookinguseremail=modelCLassList.get(position).getBookinguseremail();

        holder.setData(title,desc,status,bookusername);
        SharedPreferences getShared2 = context.getApplicationContext().getSharedPreferences("RollName", MODE_PRIVATE);
        Roll = getShared2.getString("Roll", null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence option[] = new CharSequence[]
                        {
                                "Accept",
                                "Reject"
                        };

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("UnBooked");
                dialog.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference node = db.getReference("Booking");
                            Query query = node.orderByChild("id").equalTo(id);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        for (DataSnapshot datas : snapshot.getChildren()) {
                                            String key = datas.getKey();
                                            node.child(key).removeValue();
                                            removeItem(position);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        if(which==0)
                        {

                            FirebaseDatabase node = FirebaseDatabase.getInstance();
                            DatabaseReference reference = node.getReference("Booking");
                            Query query = reference.orderByChild("id").equalTo(modelCLassList.get(position).getId());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            key = dataSnapshot.getKey();
                                            if (snapshot.child(key).child("bookinguseremail").getValue(String.class).equals(modelCLassList.get(position).getBookinguseremail())) {
                                                reference.child(key).child("status").setValue("Accepted");

                                                removeItem(position);

                                                /*Intent intent=new Intent(context, dashboard.class);
                                                context.startActivity(intent);
                                                ((Notifications)context).finish();
                                                */
                                            }
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                });
                dialog.show();


            }
    });

    }

    public void removeItem(int position) {

        modelCLassList.remove(position);
        notifyItemRemoved(position);

    }
    @Override
    public int getItemCount() {
        return modelCLassList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //Run on BackgroundThread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NotificationModelClass> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredlist.addAll(modelCLassListAll);
            } else {
                for (NotificationModelClass data : modelCLassListAll) {
                    if (data.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredlist.add(data);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;
            return filterResults;
        }

        //Run on UIThread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelCLassList.clear();
            modelCLassList.addAll((Collection<? extends NotificationModelClass>) results.values);
            notifyDataSetChanged();
        }
    };

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView title,desc,status,bookusername;
        private ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title);
            desc = itemView.findViewById(R.id.desc);
            status = itemView.findViewById(R.id.status);
            bookusername = itemView.findViewById(R.id.bookusername);
            imageView = itemView.findViewById(R.id.postImage);


        }


        private void setData( String Title, String Desc,String Status,String BookUsername) {
            status.setText(Status);
            title.setText(Title);
            desc.setText(Desc);
            bookusername.setText(BookUsername);


        }
    }



}
