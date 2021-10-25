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
		
		
		

		int rowCount = new BoardDao().getRowCount(); // 총 게시물 수 32
		int maxButton;
		// 만들어야 하는 버튼 수 7

		if (rowCount % 10 == 0) {
			maxButton = rowCount / 10;
		} else {
			maxButton = rowCount / 10 + 1;
		}

		String startPage = page;
		String endPage;
		
		// 시작, 끝버튼
		if (maxButton >= Integer.parseInt(startPage) + 4) {
			endPage = startPage+4;
			
			if (Integer.parseInt(startPage) - 1 == 1) {
				startPage = Integer.toString(Integer.parseInt(page) - 1);
				endPage = Integer.toString(Integer.parseInt(page) + 3);
			} else if(Integer.parseInt(startPage) >= 3) {
				
				startPage = Integer.toString(Integer.parseInt(page) - 2);
				endPage = Integer.toString(Integer.parseInt(page) + 2);
		
				
			} else {
				startPage = page;
				endPage = Integer.toString((Integer.parseInt(page) + 4));
			}
			
			
		} else {
			endPage = Integer.toString(maxButton);
			
			if(Integer.parseInt(startPage) == Integer.parseInt(endPage)) {
				startPage = Integer.toString(Integer.parseInt(endPage) -4);
				endPage = Integer.toString(Integer.parseInt(page));
				
			}else if(Integer.parseInt(startPage) == Integer.parseInt(endPage)-1) {
				startPage = Integer.toString(Integer.parseInt(startPage) -3);
				endPage = Integer.toString(Integer.parseInt(page) +1);
			}else if(Integer.parseInt(startPage) == Integer.parseInt(endPage)-2) {
				startPage = Integer.toString(Integer.parseInt(startPage) -2);
				endPage = Integer.toString(Integer.parseInt(page) +2);
			}else if(Integer.parseInt(startPage) == Integer.parseInt(endPage)-3) {
				startPage = Integer.toString(Integer.parseInt(startPage) -1);
				endPage = Integer.toString(Integer.parseInt(page) +3);
			}
			
			else {
			startPage = page;
			endPage = Integer.toString(maxButton);
			}
		}
	

		HttpSession session = request.getSession(true);

		if (session.getAttribute("authUser") == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('권한이 없습니다!'); location.href='/mysite02" + "';</script>");
		} else {
			request.setAttribute("list", list);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("maxButton", Integer.toString(maxButton));

			MvcUtil.forward("board/list", request, response);
		}
	}

}
