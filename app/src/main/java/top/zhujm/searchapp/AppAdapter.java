package top.zhujm.searchapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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
        Drawable iconDraw = appInfo.appIcon;
        if (iconDraw == null) {
            iconDraw = holder.itemView.getContext().getDrawable(R.mipmap.ic_launcher);
        }
        holder.ivIcon.setImageDrawable(iconDraw);
        holder.tvName.setText(appInfo.appName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openApp(v.getContext(), appInfo.pkgName);
                ((Activity) v.getContext()).finish();
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