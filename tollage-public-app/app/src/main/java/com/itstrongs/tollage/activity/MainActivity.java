package com.itstrongs.tollage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itstrongs.tollage.Directory;
import com.itstrongs.tollage.utils.SwitchUtils;
import com.itstrongs.tollage.utils.URLHolder;
import com.itstrongs.tollage2.R;
import com.itstrongs.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Directory.DataBean> mDirectories;
    private boolean isFirstBackEvent = false;
    private ListView mListView;
    private Activity mActivity;
    private MyAdapter mAdapter;
    private String mParentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        mDirectories = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        getHomePageData("0");
    }

    //获取首页数据
    private void getHomePageData(final String id) {
        Map<String, String> map = new HashMap<>();
        map.put("key", id);
        HttpUtils.doPost(URLHolder.URL_HOME, map, new HttpUtils.HttpsCallback() {
            @Override
            public void onSuccess(String data) {
                Directory directory = new Gson().fromJson(data, Directory.class);
                if (directory.isIs_file()) {    //处理文件
                    switch (directory.getExtension()) {
                        case "pdf":
                            SwitchUtils.switchPDFActivity(mActivity,
                                    directory.getFile_url(), directory.getFile_name());
                            break;
                        case "png":
                            SwitchUtils.switchImageActivity(mActivity, directory.getFile_url());
                            break;
                        case "jpg":
                            SwitchUtils.switchImageActivity(mActivity, directory.getFile_url());
                            break;
                        case "html":
                            SwitchUtils.switchWebActivity(mActivity, directory.getFile_url());
                            break;
                    }
                } else {    //处理目录
                    mDirectories = directory.getData();
                    mAdapter.notifyDataSetChanged();
                    mParentId = id;
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDirectories.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.item_list_view_menu, null);
                holder.btnMenu = (Button) convertView.findViewById(R.id.btn_item_menu);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Directory.DataBean directory = mDirectories.get(position);
            holder.btnMenu.setText(directory.getName());
            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getHomePageData(directory.getId());
                }
            });
            return convertView;
        }

        class ViewHolder {
            Button btnMenu;
        }
    }

    @Override
    public void onBackPressed() {
        if (mParentId.equals("0")) {
            if (!isFirstBackEvent) {
                isFirstBackEvent = true;
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isFirstBackEvent = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();
            }
        } else {
            getLastPageData();
        }
    }

    //获取上一页数据
    private void getLastPageData() {
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", mParentId);
        HttpUtils.doPost(URLHolder.URL_BACK, map, new HttpUtils.HttpsCallback() {
            @Override
            public void onSuccess(String data) {
                Directory directory = new Gson().fromJson(data, Directory.class);
                mDirectories = directory.getData();
                mAdapter.notifyDataSetChanged();
                mParentId = directory.getData().get(0).getParent_id();
            }
        });
    }
}
