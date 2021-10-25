package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String userno = request.getParameter("userno");
		String rdate = request.getParameter("rdate");
		String page = request.getParameter("page");
		
		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo) session.getAttribute("authUser");
		
		if(userno.equals(Long.toString(vo.getNo()))){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter(); 
			new BoardDao().Delete(title, rdate, Integer.parseInt(userno));
			writer.println("<script>alert('삭제 완료!'); location.href='"+request.getContextPath() + "/board?page="+page+"';</script>");
			
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter(); 
			writer.println("<script>alert('권한이 없습니다!'); location.href='"+request.getContextPath() + "/board?page=1"+"';</script>");
		}
		
		

	}

}
