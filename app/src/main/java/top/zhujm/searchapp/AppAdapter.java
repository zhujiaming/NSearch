package top.zhujm.searchapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import top.zhujm.searchapp.old.Utils;

class AppAdapter extends RecyclerView.Adapter<AppAdapter.Vh> {

    List<AppInfo> datas = new ArrayList<>();

    public void setDatas(List<AppInfo> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return datas.get(position).id;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false);
        Vh appVh = new Vh(itemView);
        return appVh;
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        final AppInfo appInfo = this.datas.get(position);
        holder.ivIcon.setImageDrawable(Utils.getIconByPackageName(holder.itemView.getContext(), appInfo.pkgName));
        holder.tvName.setText(appInfo.appName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goHome(((Activity) v.getContext()));
                Utils.openApp(v.getContext(), appInfo.pkgName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Vh extends RecyclerView.ViewHolder {
        public ImageView ivIcon;
        public TextView tvName;

        public Vh(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

}