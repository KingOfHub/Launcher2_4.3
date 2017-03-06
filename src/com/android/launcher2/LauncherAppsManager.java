package com.android.launcher2;
import com.android.launcher.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.content.IntentFilter;
import java.util.List;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
public class LauncherAppsManager extends Activity {
    private ListView system_appList;
    private AppsAdapter adapter; 
    private Button mDone;
    private Button mCancel;
    private StringBuffer listApps;
    public Launcher mluncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_app_manager);
        system_appList = (ListView) findViewById(R.id.system_applist);
        mDone = (Button) findViewById(R.id.done);
        mDone.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                listApps = new StringBuffer();
                final SparseArray<String> addApps = adapter.getAddItems();
                for(int i = 0; i < addApps.size(); i++) {
                    int key = addApps.keyAt(i);
                    listApps.append(addApps.get(key));
                    listApps.append(",");
                } 
                if(adapter.max_positon<adapter.getCount()-1)
                {
                	for(int i=adapter.max_positon+1;i<=adapter.getCount()-1;i++)
                	{
                		ResolveInfo Info = launcherApps.get(i);
                		String pckName = Info.activityInfo.packageName;
                		if( Utils.checkApp(getApplicationContext(), pckName))
                		{
                			listApps.append(pckName);
                			listApps.append(",");
                		}
                	}
                }
                if(listApps.toString().equals(""))
                {
                   new AlertDialog.Builder(LauncherAppsManager.this).setTitle(getString(R.string.operation_tips)).setMessage(getString(R.string.operation_message))
                          .setPositiveButton(getString(R.string.Done),null)
                   .show();
                }
                else{
                   Utils.updateApp(LauncherAppsManager.this, listApps.toString());
                   mluncher.changeforlist();
                   LauncherAppsManager.this.finish();
                }
            }
        });
        mCancel = (Button) findViewById(R.id.cancel);
        mCancel.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LauncherAppsManager.this.finish();
            }
        });
        
    }
    
    List<ResolveInfo> launcherApps = null;
    private void getLauncherApps() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager packageManager = this.getPackageManager();
        launcherApps = packageManager.queryIntentActivities(mainIntent, 0);
        for(int i=0;i<launcherApps.size();i++)
        {
            ResolveInfo Info=launcherApps.get(i);
            if(Info.activityInfo.packageName.equals("com.android.development"))
            {
        	    launcherApps.remove(i);
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        system_appList.setAdapter(null);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume(); 
        getLauncherApps();
        adapter = new AppsAdapter(this, launcherApps);
        system_appList.setAdapter(adapter);
    }

}
