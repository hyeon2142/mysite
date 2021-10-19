package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo vo = (UserVo) session.getAttribute("authUser");

		
		if(vo.getNo() == Integer.parseInt(request.getParameter("userno"))) {
			
			request.setAttribute("userno", request.getParameter("userno"));
			request.setAttribute("rdate", request.getParameter("rdate"));
			request.setAttribute("title", request.getParameter("title"));
			
			MvcUtil.forward("board/modify", request, response);
			
		}else {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter(); 
			writer.println("<script>alert('권한이 없습니다!'); location.href='"+request.getContextPath() + "/board"+"';</script>");

		
		}
		
		

	}

}
