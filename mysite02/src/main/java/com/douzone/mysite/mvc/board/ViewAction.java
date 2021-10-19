package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String userno = request.getParameter("userno");
		String rdate = request.getParameter("rdate");
		
		BoardVo vo = new BoardDao().findContents(title,rdate,Integer.parseInt(userno));
		
		request.setAttribute("title", vo.getTitle());
		request.setAttribute("contents", vo.getContents());
		request.setAttribute("userno", userno);
		request.setAttribute("rdate", rdate);
		
		MvcUtil.forward("board/view", request, response);

	}

}
