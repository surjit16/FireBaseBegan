package in.surjitsingh.firebasebegan.rtdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.surjitsingh.firebasebegan.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerVH> {

    List<DBData> dataList;
    Context context;

    MyAdapter(Context context, List<DBData> list) {
        this.context = context;
        this.dataList = list;
    }

    @NonNull
    @Override
    public RecyclerVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.view_list_item, viewGroup, false);
        return new RecyclerVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVH recyclerVH, final int i) {
        DBData curr = dataList.get(i);
        recyclerVH.uid.setText(curr.getId());
        recyclerVH.email.setText(curr.getEmail());
        recyclerVH.name.setText(curr.getName());
        recyclerVH.mess.setText(curr.getMess());
        recyclerVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerVH extends RecyclerView.ViewHolder {
        TextView name, email, uid, mess;

        public RecyclerVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            uid = itemView.findViewById(R.id.uid);
            mess = itemView.findViewById(R.id.mess);
        }
    }
}
