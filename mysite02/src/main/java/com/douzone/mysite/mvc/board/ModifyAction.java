package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String updatetitle = request.getParameter("updatetitle");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String user_no = request.getParameter("userno");
		String rdate = request.getParameter("rdate");
		String page = request.getParameter("page");

		
		new BoardDao().update(Long.parseLong(user_no), title, rdate, updatetitle, content);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter(); 
		writer.println("<script>alert('수정되었습니다!'); location.href='"+request.getContextPath() + "/board?page="+page+"';</script>");

		
	}

}
