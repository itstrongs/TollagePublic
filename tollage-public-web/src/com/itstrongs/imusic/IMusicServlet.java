package com.itstrongs.imusic;

import com.itstrongs.jdbc.MusicDaoImpl;
import com.itstrongs.servlet.BaseServlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 爱音乐后台处理
 * @author itstrongs
 */
public class IMusicServlet extends BaseServlet {

    private MusicDaoImpl mMusicDao = new MusicDaoImpl();
    private int mParentId;

    @Override
    protected void doOperate(String uri, HttpServletRequest req, HttpServletResponse resp) {
        switch (uri) {
            case "imusic":
                doIMusicHome(req, resp);
                break;
            case "upload":
                doUploadMusic(req, resp);
                break;
            case "playlist":
                doGetPlayList(req, resp);
                break;
            default:
                //404
                break;
        }
    }

    //显示首页
    private void doIMusicHome(HttpServletRequest req, HttpServletResponse resp) {
        mParentId = 0;
        requestDispatcher(req, resp, "imusic");
    }

    //获取播放列表
    private void doGetPlayList(HttpServletRequest req, HttpServletResponse resp) {

    }

    //上传音乐
    private void doUploadMusic(HttpServletRequest req, HttpServletResponse resp) {
        DiskFileItemFactory dif = new DiskFileItemFactory(); // 使用默认的临时目录
        ServletFileUpload sfu = new ServletFileUpload(dif);
        sfu.setHeaderEncoding("utf-8");
        List<FileItem> items = null;
        try {
            items = sfu.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        for (FileItem item : items) {
            if (!item.isFormField()) { // 说明是上传字段
                String fileName = item.getName(); // 拿到文件名 ：不包括路径
                System.out.println("fileName:" + fileName);
                String webPath = getServletContext().getRealPath(File.separator + "WEB-INF");
                File f = new File(webPath + File.separator + fileName);
                try {
                    item.write(f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //存入数据库
                Music music = new Music();
                music.setName(fileName);
                music.setParent_id(mParentId);
                mMusicDao.addMusic(music);
            }
        }
    }
}
