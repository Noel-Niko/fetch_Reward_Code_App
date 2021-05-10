package com.android.fetchdataparsing.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.fetchdataparsing.R;
import com.android.fetchdataparsing.model.fetchItem;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<fetchItem> items;

    public ItemListAdapter(Context ctx, List<fetchItem> items){
        this.inflater = LayoutInflater.from(ctx);
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        System.out.println(("Here is the position: " + position + " and the id: " + items.get(position).getId() + " and list id of :" + items.get(position).getListId() + " and name of : " + items.get(position).getName()));
        holder.id.setText(String.valueOf(items.get(position).getId()));
        holder.listId.setText(String.valueOf(items.get(position).getListId()));
        holder.name.setText(items.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView listId,name,id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.itemid);
            listId = itemView.findViewById(R.id.listId);
            name = itemView.findViewById(R.id.name);


            // handle onClick

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Click functionality TBD.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}