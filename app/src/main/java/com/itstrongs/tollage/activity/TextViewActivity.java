package com.itstrongs.tollage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itstrongs.tollage2.R;
import com.itstrongs.utils.ToastUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by itstrong on 2017/6/4.
 */

public class TextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        TextView mTextTitle = (TextView) findViewById(R.id.text_web_title);
        TextView mTextContent = (TextView) findViewById(R.id.text_web_content);
        String type = getIntent().getStringExtra("text_type");
        switch (type) {
            case "gzzd_0":
                mTextTitle.setText("办税服务厅办税引导制度");
                break;
            case "gzzd_5":
                mTextTitle.setText("廉政规定");
                break;
            case "gzzd_8":
                mTextTitle.setText("税控系统专用设备等有关事项的公告");
                break;
            case "gzzd_9":
                mTextTitle.setText("宜丰县国家税务局办税服务厅公开服务承诺书");
                break;
            case "main_0":
                mTextTitle.setText("宜丰县国税地税联合办税服务厅情况介绍");
                break;
            case "main_1":
                mTextTitle.setText("纳税人权利和义务");
                break;
        }
        mTextContent.setText(getAssetsString(type + ".txt"));
    }

    private String getAssetsString(String fileName) {
        InputStream open = null;
        StringBuilder sb = null;
        try {
            open = getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(open));

            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            ToastUtils.show(this, "assets资源没有找到");
            e.printStackTrace();
        } finally {
            try {
                open.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
