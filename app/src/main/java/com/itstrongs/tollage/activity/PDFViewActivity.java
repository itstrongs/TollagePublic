package com.itstrongs.tollage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itstrongs.tollage2.R;
import com.itstrongs.utils.HttpUtils;
import com.joanzapata.pdfview.PDFView;

import java.io.File;

/**
 * Created by itstrongs on 2017/6/22.
 */

public class PDFViewActivity extends AppCompatActivity {

    private PDFView mPdfView;
    private File mFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        mPdfView = (PDFView) findViewById(R.id.pdfview);

        mFile = new File(getCacheDir(), getIntent().getStringExtra("file_name"));
        if (!mFile.exists()) {
            HttpUtils.doDownload(mFile, getIntent().getStringExtra("file_url"), new HttpUtils.HttpsCallback() {
                @Override
                public void onSuccess(String data) {
                    loadPDF();
                }
            });
        } else {
            loadPDF();
        }
    }

    private void loadPDF() {
        mPdfView.fromFile(mFile)
//        mPdfView.fromAsset("宜丰县国税地税联合办税服务厅情况介绍.pdf")
                .pages(0, 2, 1, 3, 3, 3)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onDraw(null)
                .onLoad(null)
                .onPageChange(null)
                .load();
    }
}
