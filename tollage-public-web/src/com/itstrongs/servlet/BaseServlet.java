package com.itstrongs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
        System.out.println("uri:" + uri);
        doOperate(uri, req, resp);
    }

    protected abstract void doOperate(String uri, HttpServletRequest req, HttpServletResponse resp);

    protected void requestDispatcher(HttpServletRequest request,
			HttpServletResponse response, String name) {
		try {
			request.getRequestDispatcher("/" + name + ".jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
