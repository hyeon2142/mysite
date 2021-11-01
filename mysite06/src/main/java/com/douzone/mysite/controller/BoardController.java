package com.douzone.mysite.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/page/{pages}")
	public String index(@PathVariable("pages") String now, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(true);

		if (session.getAttribute("authUser") == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('권한이 없습니다!'); history.back();</script>");
			return null;
		}
		if (now == null) {
			now = "1";
		}

		String page = now;
		List<BoardVo> list = boardService.findAll(page);
		int rowCount = boardService.getRowCount();
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
			endPage = startPage + 4;

			if (Integer.parseInt(startPage) - 1 == 1) {
				startPage = Integer.toString(Integer.parseInt(page) - 1);
				endPage = Integer.toString(Integer.parseInt(page) + 3);
			} else if (Integer.parseInt(startPage) >= 3) {

				startPage = Integer.toString(Integer.parseInt(page) - 2);
				endPage = Integer.toString(Integer.parseInt(page) + 2);

			} else {
				startPage = page;
				endPage = Integer.toString((Integer.parseInt(page) + 4));
			}

		} else {
			endPage = Integer.toString(maxButton);

			if (Integer.parseInt(startPage) == Integer.parseInt(endPage)) {
				startPage = Integer.toString(Integer.parseInt(endPage) - 4);
				endPage = Integer.toString(Integer.parseInt(page));

			} else if (Integer.parseInt(startPage) == Integer.parseInt(endPage) - 1) {
				startPage = Integer.toString(Integer.parseInt(startPage) - 3);
				endPage = Integer.toString(Integer.parseInt(page) + 1);
			} else if (Integer.parseInt(startPage) == Integer.parseInt(endPage) - 2) {
				startPage = Integer.toString(Integer.parseInt(startPage) - 2);
				endPage = Integer.toString(Integer.parseInt(page) + 2);
			} else if (Integer.parseInt(startPage) == Integer.parseInt(endPage) - 3) {
				startPage = Integer.toString(Integer.parseInt(startPage) - 1);
				endPage = Integer.toString(Integer.parseInt(page) + 3);
			}

			else {
				startPage = page;
				endPage = Integer.toString(maxButton);
			}
		}

		model.addAttribute("start", startPage);
		model.addAttribute("end", endPage);
		model.addAttribute("maxButton", maxButton);
		model.addAttribute("page", page);

		model.addAttribute("list", list);
		return "board/index";

	}
	
	@RequestMapping(value = "/replyform/{group_no}/{order_no}/{depth}/{page}")
		public String replyForm(Model model,@PathVariable("group_no") String group_no,@PathVariable("order_no") String order_no,@PathVariable("depth") String depth,@PathVariable("page") String page) {
		
		
		model.addAttribute(group_no);
		model.addAttribute(order_no);
		model.addAttribute(depth);
		model.addAttribute(page);
		
		return "board/reply";
	}
	
	@RequestMapping(value = "/reply")
	public String reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo) session.getAttribute("authUser");
		Long no = vo.getNo();
		
		
		String user_no = Long.toString(no);
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String order_no = request.getParameter("order_no");
		String group_no = request.getParameter("group_no");
		String depth = request.getParameter("depth");
		String page = request.getParameter("page");
		
		int dep = (Integer.parseInt(depth))+1;
		int orderno = (Integer.parseInt(order_no))+1;
		
		depth = Integer.toString(dep);
		order_no = Integer.toString(orderno);
		int count = boardService.reply(title, contents, user_no, order_no, group_no, depth);
		if(count == 1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('댓글작성완료!'); location.href='" + request.getContextPath() + "/board/page/"+page+"'</script>");
		}else {	       
			System.out.println("실패");
		}
		
		return null;
	}

	@RequestMapping(value = "/writeform")
	public String writeForm() {
		return "board/write";
	}

	@RequestMapping(value = "/write")
	public String write(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo) session.getAttribute("authUser");
		Long no = vo.getNo();

		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String user_no = Long.toString(no);

		int count = boardService.write(title, contents, user_no);

		if (count == 1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('작성완료!'); location.href='" + request.getContextPath() + "/board/page/1'</script>");
			return null;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('에러 발생!'); history.back();</script>");
			return null;
		}

	}

	@RequestMapping(value = "/update/{user_no}/{reg_date}/{title}/{page}/{contents}")
	public String updateForm(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable("user_no") String user_no, @PathVariable("reg_date") String reg_date,
			@PathVariable("title") String title, @PathVariable("page") String page,
			@PathVariable("contents") String contents) throws IOException {

		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo) session.getAttribute("authUser");
		int user = Integer.parseInt(user_no);
		if (user == vo.getNo()) {
			model.addAttribute(user_no);
			model.addAttribute(reg_date);
			model.addAttribute(title);
			model.addAttribute(page);
			model.addAttribute(contents);

			return "board/modify";

		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('권한이 없습니다!'); history.back();</script>");
			return null;
		}

	}

	@RequestMapping(value = "/view/{title}/{reg_date}/{user_no}/{page}/{group_no}/{order_no}/{depth}")
	public String views(Model model, @PathVariable("title") String title, @PathVariable("page") String page,
			@PathVariable("reg_date") String reg_date, @PathVariable("user_no") String user_no,
			@PathVariable("group_no") String group_no, @PathVariable("order_no") String order_no,
			@PathVariable("depth") String depth) {

		BoardVo vo = boardService.findContents(title, reg_date, user_no);

		model.addAttribute("title", vo.getTitle());
		model.addAttribute("contents", vo.getContents());
		model.addAttribute("reg_date", reg_date);
		model.addAttribute("page", page);
		model.addAttribute("group_no", group_no);
		model.addAttribute("order_no", order_no);
		model.addAttribute("depth", depth);

		return "board/view";
	}

	@RequestMapping(value = "/modify/{user_no}/{reg_date}/{title}/{page}")
	public String modify(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("user_no") String user_no, @PathVariable("reg_date") String reg_date,
			@PathVariable("title") String title, @PathVariable("page") String page) throws IOException {

		String updateTitle = request.getParameter("updatetitle");
		String contents = request.getParameter("contents");

		int count = boardService.update(updateTitle, contents, title, reg_date, user_no);
		if (count == 1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('수정 완료!'); location.href='" + request.getContextPath() + "/board/page/" + page
					+ "';</script>");
			return null;
		} else {
			System.out.println("에러 발생");
		}

		return null;
	}

	@RequestMapping(value = "/delete/{title}/{reg_date}/{user_no}/{page}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("title") String title,
			@PathVariable("reg_date") String reg_date, @PathVariable("user_no") String user_no,
			@PathVariable("page") String page) throws IOException {

		if (title.equals("삭제된 글 입니다.")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('이미 삭제된 글 입니다!'); history.back();</script>");
			return null;
		}

		HttpSession session = request.getSession(true);
		UserVo vo = (UserVo) session.getAttribute("authUser");
		int user = Integer.parseInt(user_no);

		if (user == vo.getNo()) {

			int count = boardService.delete("삭제된 글 입니다.", title, reg_date, user_no);

			if (count == 1) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println("<script>alert('삭제 완료!'); location.href='" + request.getContextPath() + "/board/page/"
						+ page + "';</script>");
				return null;
			}

		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('권한이 없습니다!'); history.back();</script>");
			return null;
		}
		return null;

	}

}
