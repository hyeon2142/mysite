package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;

	public List<BoardVo> findAll(String startPage){
		return boardRepository.findAll(startPage);
		
	}
	
	public int getRowCount() {
		return boardRepository.getRowCount();
	}
	
	public BoardVo findContents(String title,String reg_date,String user_no) {
		return boardRepository.findContents(title, reg_date, user_no);
	}
	
	public int delete(String newtitle, String title, String reg_date, String user_no) {
		return boardRepository.delete(newtitle, title, reg_date, user_no);
	}
	public int update(String newtitle,String contents,String title,String reg_date,String user_no) {
		return boardRepository.update(newtitle, contents, title, reg_date, user_no);
	}
	
	public int write(String title, String contents, String user_no) {
		return boardRepository.write(title, contents, user_no);
	}
	
	public int reply(String title, String contents, String user_no, String order_no, String group_no, String depth) {
		return boardRepository.reply(title, contents, user_no, order_no, group_no, depth);
	}

}
