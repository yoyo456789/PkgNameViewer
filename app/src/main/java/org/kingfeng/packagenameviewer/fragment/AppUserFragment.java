package org.kingfeng.packagenameviewer.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.kingfeng.packagenameviewer.R;
import org.kingfeng.packagenameviewer.adapter.AppListAdapter;
import org.kingfeng.packagenameviewer.bean.AppInfo;
import org.kingfeng.packagenameviewer.util.CommonUtil;
import org.kingfeng.packagenameviewer.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: User App Fragment
 *
 * @author Jinfeng Lee
 */
public class AppUserFragment extends Fragment {

    RecyclerView recyclerView;
    private View mainView;
    private ArrayList<AppInfo> appUserInfos;
    private AppListAdapter appListAdapter;
    private Context context;

    public AppUserFragment(Context context) {
        // Required empty public constructor
        this.context = context.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_app_user, container, false);

        initViews();
        init();

        appListAdapter.setAppInfos(appUserInfos);
        recyclerView.setAdapter(appListAdapter);

        appListAdapter.setmItemClickListener(new AppListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                CommonUtil.unInstallApp(getContext(), appUserInfos.get(postion).getPackageName());
            }
        });

        Toast.makeText(context, "共安装" + appUserInfos.size() + "款用户应用", Toast.LENGTH_LONG).show();

        return mainView;
    }

    private void initViews() {
        recyclerView = (RecyclerView) mainView.findViewById(R.id.user_app_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        appListAdapter = new AppListAdapter(getActivity());
    }

    private void init() {
        appUserInfos = new ArrayList<AppInfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppInfo appInfo = new AppInfo(getActivity(), resolveInfo);
            PackageInfo packageInfo = appInfo.getPackageInfo();
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appInfo.setAppCategary(0); // user application
                appUserInfos.add(appInfo);
            } else {
//                appInfo.setAppCategary(1);
            }
        }

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getContext(), "共安装" + appUserInfos.size() + "款用户应用", Toast.LENGTH_LONG).show();
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Toast.makeText(getContext(), "共安装" + appUserInfos.size() + "款用户应用", Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
