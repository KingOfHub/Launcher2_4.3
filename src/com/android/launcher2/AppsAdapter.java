package com.android.launcher2;

import com.android.launcher.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.HashSet;
import java.util.List;

public class AppsAdapter extends BaseAdapter {

    private ViewHolder holder;
    private LayoutInflater mInflater;
    private List<ResolveInfo> apps;
    private int appTotal;
    private Context context;
    PackageManager pm;
    private StringBuffer listAppss;
    static int max_positon=0;
    public AppsAdapter(Context context, List<ResolveInfo> apps) {
        this.mInflater = LayoutInflater.from(context);
        this.apps = apps;
        this.context = context;
        pm = context.getPackageManager(); // 得到pm对象
    }
    
    private static HashSet<Integer> addItem = new HashSet<Integer>();
    private  SparseArray<String> addItems = new SparseArray<String>();
     private  SparseArray<Integer> cancelItems = new SparseArray<Integer>();
    public  SparseArray<String> getAddItems() {
        return addItems;
    }
    
    public void clearAddItems() {
        addItems.clear();
        cancelItems.clear();
    }
    
    public static HashSet<Integer> getAddItem() {
        return addItem;
    }
    
    public void clearAddItem() {
        addItem.clear();
    }

    public void setData(List<ResolveInfo> apps) {
        this.apps = apps;
    }

    public int getCount() {
        appTotal = apps.size();
        return appTotal;
    }

    public Object getItem(int arg0) {
        return apps.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(position==0)
        {
          max_positon=0;
        }
      	if(position>max_positon)
    	{
    	  max_positon=position;
    	}
        holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.launcher_app_list, null);
            holder.AppIcon = (ImageView) convertView.findViewById(R.id.AppIcon);
            holder.AppName = (TextView) convertView.findViewById(R.id.AppName);
            holder.AppEnable = (CheckBox) convertView.findViewById(R.id.enable);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.AppEnable.setTag(position);
        holder.AppEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int pos = (Integer) buttonView.getTag();
                if (isChecked) {
                    String pck =  apps.get(pos).activityInfo.packageName;
                    addItems.put(pos, pck);
                    cancelItems.remove(pos);
                } else {
                    addItems.remove(pos);
                    cancelItems.put(pos, pos);
                }
            }
        });
        
        ResolveInfo Info = apps.get(position);
        holder.AppIcon.setImageDrawable(Info.loadIcon(pm));
        holder.AppName.setText(Info.loadLabel(pm).toString());
        String pckName = Info.activityInfo.packageName;
        if((Utils.checkApp(context, pckName) || addItems.indexOfKey(position) >= 0) && cancelItems.get(position, -1) != position) {
            holder.AppEnable.setChecked(true);
            addItems.put(position, pckName);
        } else {
            addItems.remove(position);
            holder.AppEnable.setChecked(false);
        }
        return convertView;
    }
    
    private final class ViewHolder {
        public ImageView AppIcon;
        public TextView AppName;
        public CheckBox AppEnable;
    }

}
