package kz.mycrm.android.ui.info;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.mycrm.android.R;

/**
 * Created by asset on 12/7/17.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

    List<ServiceObj> serviceList;
    Context context;

    public ServiceAdapter(List<ServiceObj> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.price.setText(serviceList.get(position).price);
        holder.title.setText(serviceList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.service_title)
        TextView title;
        @BindView(R.id.service_price)
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "service number " + itemView.getId(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
