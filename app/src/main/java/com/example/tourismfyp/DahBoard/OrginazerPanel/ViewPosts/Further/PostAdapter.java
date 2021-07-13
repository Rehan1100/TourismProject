package com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Edit.EditPostActivity;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Viewholder> implements Filterable {
    private List<PostModelCLass> modelCLassList;
    List<PostModelCLass> modelCLassListAll;
    Context context;
    public String Roll;

    public PostAdapter(List<PostModelCLass> modelCLassList, Context context) {
        this.modelCLassList = modelCLassList;
        this.modelCLassListAll = new ArrayList<>(modelCLassList);
        this.context = context;
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
        final String loginuser = modelCLassList.get(position).getLoginuser();
        final String longitude = modelCLassList.get(position).getLongitude();
        final String perhead = modelCLassList.get(position).getPerhead();
        final String phoneNumber = modelCLassList.get(position).getPhoneNumber();
        final String title = modelCLassList.get(position).getTitle();
        final String url = modelCLassList.get(position).getUrl();
        final String lastDate = modelCLassList.get(position).getDeadlinedate();
        final String city = modelCLassList.get(position).getCity();
        final String category = modelCLassList.get(position).getCategory();


        holder.setData(cnic, currentdate, desc,id,item,latitude,loginuser,longitude,perhead,phoneNumber,title,url,lastDate);
        SharedPreferences getShared2 = context.getApplicationContext().getSharedPreferences("RollName", MODE_PRIVATE);
        Roll = getShared2.getString("Roll", null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence option[] = new CharSequence[]
                        {
                                "Edit", "Delete"
                        };

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Modification");
                dialog.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            Intent intent = new Intent(context, EditPostActivity.class);
                            intent.putExtra("category",category);
                            intent.putExtra("cnic",cnic);
                            intent.putExtra("currentdate",currentdate);
                            intent.putExtra("desc",desc);
                            intent.putExtra("id",id);
                            intent.putExtra("item",item);
                            intent.putExtra("latitude",latitude);
                            intent.putExtra("loginuser",loginuser);
                            intent.putExtra("longitude",longitude);
                            intent.putExtra("perhead",perhead);
                            intent.putExtra("phoneNumber",phoneNumber);
                            intent.putExtra("url",url);
                            intent.putExtra("title",title);
                            intent.putExtra("lastDate",lastDate);
                            intent.putExtra("city",city);
                            context.startActivity(intent);
                            ((ViewAllOnCategory)context).finish();

                        }
                        if (which == 1) {
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference node = db.getReference("Post");
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
            List<PostModelCLass> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredlist.addAll(modelCLassListAll);
            } else {
                for (PostModelCLass data : modelCLassListAll) {
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
            modelCLassList.addAll((Collection<? extends PostModelCLass>) results.values);
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
