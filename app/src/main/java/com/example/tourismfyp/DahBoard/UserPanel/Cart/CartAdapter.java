package com.example.tourismfyp.DahBoard.UserPanel.Cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Edit.EditPostActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.PostModelCLass;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.ViewAllOnCategory;
import com.example.tourismfyp.DahBoard.UserPanel.BookingPost.AddToCartClass;
import com.example.tourismfyp.DahBoard.UserPanel.BookingPost.DetailsPostActivity;
import com.example.tourismfyp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> implements Filterable {
    private List<CartModelClass> modelCLassList;
    List<CartModelClass> modelCLassListAll;
    Context context;
    public String Roll;
    public String LoginUserEmail,LoginUserFirstName,LoginUserLastName;

    public CartAdapter(List<CartModelClass> modelCLassList, Context context,String Loginuseremail,String Loginfirstname,String LoginLastname) {
        this.modelCLassList = modelCLassList;
        this.modelCLassListAll = new ArrayList<>(modelCLassList);
        this.context = context;
        this.LoginUserEmail=Loginuseremail;
        this.LoginUserFirstName=Loginfirstname;
        this.LoginUserLastName=LoginLastname;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postrows, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final String cnic = modelCLassList.get(position).getCnic();
        final String currentdate = modelCLassList.get(position).getCurrentdate();
        final String desc = modelCLassList.get(position).getDesc();
        final String id = modelCLassList.get(position).getId();
        final String item = modelCLassList.get(position).getItem();
        final String latitude = modelCLassList.get(position).getLatitude();
        final String owner = modelCLassList.get(position).getOwner();
        final String longitude = modelCLassList.get(position).getLongitude();
        final String perhead = modelCLassList.get(position).getPerhead();
        final String phoneNumber = modelCLassList.get(position).getPhoneNumber();
        final String title = modelCLassList.get(position).getTitle();
        final String url = modelCLassList.get(position).getUrl();
        final String deadlinedate = modelCLassList.get(position).getLastDate();
        final String city = modelCLassList.get(position).getCity();
        final String category = modelCLassList.get(position).getCategory();



        holder.setData(cnic, currentdate, desc,id,item,latitude,owner,longitude,perhead,phoneNumber,title,url,deadlinedate);
        SharedPreferences getShared2 = context.getApplicationContext().getSharedPreferences("RollName", MODE_PRIVATE);
        Roll = getShared2.getString("Roll", null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence option[] = new CharSequence[]
                        {
                                 "Delete"
                                ,"Booked"
                        };

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Select Option you want");
                dialog.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference node = db.getReference("Cart");
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

                               /* Intent intent = new Intent(context, dashboard.class);
                                context.startActivity(intent);
                                ((dashboard)context).finish();
*/
                        }
                        if (which==1) {
                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Booking");
                            Query query2 = db2.orderByChild("id").equalTo(id);
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Toast.makeText(context, "You Already Booked", Toast.LENGTH_SHORT).show();

                                    }
                                    if (!snapshot.exists()) {
                                        //DataInsert
                                        AddToCartClass users = new AddToCartClass(LoginUserFirstName, LoginUserLastName, LoginUserEmail, category, cnic, currentdate, desc, perhead, latitude, longitude, phoneNumber, url, title, deadlinedate, city, item, owner, "Booked", id);

                                        db2.push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                removeItem(position);
                                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                DatabaseReference node = db.getReference("Cart");
                                                Query query = node.orderByChild("id").equalTo(id);
                                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        if (snapshot.exists()) {
                                                            for (DataSnapshot datas : snapshot.getChildren()) {
                                                                String key = datas.getKey();
                                                                node.child(key).removeValue();


                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                Toast.makeText(context, "Sucessfully Booked Wait For Admin Accept Response", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
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
            List<CartModelClass> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredlist.addAll(modelCLassListAll);
            } else {
                for (CartModelClass data : modelCLassListAll) {
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
            modelCLassList.addAll((Collection<? extends CartModelClass>) results.values);
            notifyDataSetChanged();
        }
    };

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView title,desc,deadlinedate,startdate;
        private ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title);
            desc = itemView.findViewById(R.id.desc);
            startdate = itemView.findViewById(R.id.startDate);
            deadlinedate = itemView.findViewById(R.id.DeadlineDate);
            imageView = itemView.findViewById(R.id.postImage);


        }


        private void setData(String cnic, String currentdate, String Desc, String id, String item, String latitude, String loginuser, String longitude, String perhead, String OrganizerName, String Title, String image,String lastDate) {
            startdate.setText(currentdate);
            title.setText(Title);
            desc.setText(Desc);
            deadlinedate.setText(lastDate);
            Picasso.get().load(image).into(imageView);


        }
    }

}
