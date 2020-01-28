package com.itstrongs.tollage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itstrongs.servlet.BaseServlet;
import com.itstrongs.holder.ConstantHolder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itstrongs.jdbc.DirectoryDaoImpl;

/**
 * 办税公开后台逻辑处理
 * @author itstrongs
 *
 */
@SuppressWarnings("serial")
public class TollageServlet extends BaseServlet {

	private DirectoryDaoImpl mDirectoryDaoImpl = new DirectoryDaoImpl();
	private int mInsertKey;

	@Override
	protected void doOperate(String uri, HttpServletRequest req, HttpServletResponse resp) {
		switch (uri) {
			case "tollage":
				showHomePage(req, resp);
				break;
			case "home":
				showHomePage(req, resp);
				break;
			case "add":
				addDirector(req, resp);
				break;
			case "delete":
				deleteDirector(req, resp);
				break;
			case "upload":
				uploadFile(req, resp);
				break;
			case "data":
				returnHomePageData(req, resp);
				break;
			case "back":
				returnLashPageData(req, resp);
				break;
			default:
				//404
				break;
		}
	}

	//返回上一页数据
	private void returnLashPageData(HttpServletRequest request, HttpServletResponse response) {
		String parentId = request.getParameter("parent_id");
		if (parentId != null) {
			List<Directory> lastDirectory = mDirectoryDaoImpl.queryLastDirectory(parentId);
			JSONObject json = new JSONObject();
			json.put("is_file", false);
			json.put("data", JSONArray.fromObject(lastDirectory));
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//返回首页数据
	private void returnHomePageData(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("key");
		if (key != null && !key.equals("0")) {
			Directory directory = mDirectoryDaoImpl.queryDirectory(key);
			if (key != null && directory.getExtension() != null && !directory.getExtension().isEmpty()) {	//非首页非目录
				String fileName = directory.getId() + "." + directory.getExtension();
				String url = ConstantHolder.URL_BASE + "file/" + fileName;
				JSONObject json = new JSONObject();
				json.put("is_file", true);
				json.put("file_url", url);
				json.put("file_name", fileName);
				json.put("extension", directory.getExtension());
				try {
					response.getWriter().write(json.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {	//非首页目录
				List<Directory> mDirectories = mDirectoryDaoImpl.queryChildDirectory(key == null ? "0" : key);
				request.getSession().setAttribute("handle_parent_id", key);
				System.out.println(mDirectories);
				JSONArray fromObject = JSONArray.fromObject(mDirectories);
				System.out.println("json:" + fromObject.toString());

				JSONObject json = new JSONObject();
				json.put("is_file", false);
				json.put("data", fromObject);
				try {
					response.getWriter().write(json.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {	//首页的情况
			List<Directory> mDirectories = mDirectoryDaoImpl.queryChildDirectory(key == null ? "0" : key);
			request.getSession().setAttribute("handle_parent_id", key);
			System.out.println(mDirectories);
			JSONArray fromObject = JSONArray.fromObject(mDirectories);
			System.out.println("json:" + fromObject.toString());

			JSONObject json = new JSONObject();
			json.put("is_file", false);
			json.put("data", fromObject);
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//上传文件
	private void uploadFile(HttpServletRequest request, HttpServletResponse response) {
		String title = null;
		String fileName = null;
		String extension = null;
		String id = UUID.randomUUID().toString();
		try {
			File file = new File(getServletContext().getRealPath(File.separator + "WEB-INF") + File.separator + "file");
			if (!file.exists()) file.mkdirs();
			DiskFileItemFactory dif = new DiskFileItemFactory(); // 使用默认的临时目录
			ServletFileUpload sfu = new ServletFileUpload(dif);
			sfu.setHeaderEncoding("utf-8");
			@SuppressWarnings("unchecked")
			List<FileItem> items = sfu.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) { // 说明是普通字段
					String name = item.getFieldName(); // 拿到文本框的名字
					String value = item.getString("UTF-8"); // 拿到文本框中的内容
					System.out.println(name + "=" + value);
					title = value;
				} else { // 说明是上传字段
					fileName = item.getName(); // 拿到文件名 ：不包括路径
					extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
					System.out.println("上传文件名；" + fileName);
					if (title.isEmpty()) title = fileName.substring(0, fileName.lastIndexOf("."));
					File f = new File("mPathRoot" + File.separator + id + "." + extension);
					item.write(f);
				}
			}
			
			Directory directory = new Directory();
			directory.setId(id);
			directory.setKey(mInsertKey + 1);
			directory.setName(title);
			directory.setExtension(extension);
			String parentId = (String)request.getSession().getAttribute("parent_id");
			if (parentId == null) parentId = "0";
			directory.setParent_id(parentId);
			System.out.println("插入的文件：" + directory);
			mDirectoryDaoImpl.addDirectory(mInsertKey, directory);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getDirectoryData(request, response, (String)request.getSession().getAttribute("parent_id"));
		}
	}
	
	//增加目录
	private void addDirector(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String parentId = (String)request.getSession().getAttribute("parent_id");
		if (parentId == null) parentId = "0";
		if (title == null) {
			mInsertKey = Integer.parseInt(request.getParameter("key"));
			System.out.println("key:" + mInsertKey);
			requestDispatcher(request, response, "add");
		} else {
			Directory directory = new Directory();
			directory.setId(UUID.randomUUID().toString());
			directory.setName(title);
			directory.setKey(mInsertKey + 1);
			directory.setParent_id(parentId);
			mDirectoryDaoImpl.addDirectory(mInsertKey, directory);
			getDirectoryData(request, response, parentId);
		}
	}	

	//删除目录
	private void deleteDirector(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		int key = Integer.parseInt(request.getParameter("key"));
		System.out.println("id+key:" + id + ",key:" + key);
		mDirectoryDaoImpl.deleteDirectory(key, id);
		getDirectoryData(request, response, (String)request.getSession().getAttribute("parent_id"));
	}

	//显示目录
	private void showHomePage(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("key");
		if (key != null) {
			Directory directory = mDirectoryDaoImpl.queryDirectory(key);
			if (key != null && directory.getExtension() != null && !directory.getExtension().isEmpty()) {	//非首页非目录
				try {
					String url = "http://localhost:8080/file/" + directory.getId() + "." + directory.getExtension();
					System.out.println("url: " + url);
					response.sendRedirect(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {	//非首页目录
				getDirectoryData(request, response, key);
			}
		} else {	//首页的情况
			getDirectoryData(request, response, key);
		}
	}

	//查询目录数据
	private void getDirectoryData(HttpServletRequest request, HttpServletResponse response, String key) {
		List<Directory> mDirectories = mDirectoryDaoImpl.queryChildDirectory(key == null ? "0" : key);
		request.getSession().setAttribute("parent_id", key);
		request.getSession().setAttribute("menu_list", mDirectories);
		requestDispatcher(request, response, "tollage");
	}

}
