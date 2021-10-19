package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = request.getParameter("page");
		List<BoardVo> list = new BoardDao().findAll(page);
		
		int rowCount = new BoardDao().getRowCount();
		int maxButton;
		
		if(rowCount % 5 == 0) {
			maxButton = rowCount/5;
		}else {
			maxButton = rowCount/5 + 1;
		}
		
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("authUser") == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter(); 
			writer.println("<script>alert('권한이 없습니다!'); location.href='/mysite02"+"';</script>");
		} else {
			request.setAttribute("list", list);
			request.setAttribute("maxButton",maxButton);
			MvcUtil.forward("board/list", request, response);
		}
	}

}
