package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo uvo = (UserVo) session.getAttribute("authUser");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String group_no = request.getParameter("group_no");
		String order_no = request.getParameter("order_no");
		String depth = request.getParameter("depth");
		String page = request.getParameter("page");
		Long user_no = uvo.getNo();
		

		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setGroup_no(Integer.parseInt(group_no));
		vo.setOrder_no(Integer.parseInt(order_no)+1);
		vo.setUser_no(user_no);
		vo.setDepth(Integer.parseInt(depth)+1);
	    new BoardDao().reply(vo);
	    MvcUtil.redirect(request.getContextPath() + "/board?page="+page+"&depth="+depth, request, response);
		

		
	}


}
