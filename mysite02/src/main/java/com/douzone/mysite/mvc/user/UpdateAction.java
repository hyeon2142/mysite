package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Access Control(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		
		long no = authUser.getNo();
		String name = authUser.getName();
		String gender = request.getParameter("gender");
		String password = request.getParameter("password");   // 사용자가 비밀번호 변경 시 수정된 비밀번호 사용 
		
		
		UserVo vo = new UserVo();
		vo.setNo(no);
		vo.setName(name);
		vo.setGender(gender);
		
		new UserDao().update(no,password,gender);
		
		MvcUtil.redirect(request.getContextPath(), request, response);
		
	}

}
