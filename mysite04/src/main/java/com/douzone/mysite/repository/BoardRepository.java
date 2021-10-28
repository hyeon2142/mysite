package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAll(String startPage){
		int page = ((Integer.parseInt(startPage))-1)*10;
		return sqlSession.selectList("board.findAll",page);
	}
	
	
	
	public int getRowCount() {
		return sqlSession.selectOne("board.getRowCount");
	}
	
	public BoardVo findContents(String title,String reg_date,String user_no) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("reg_date", reg_date);
		map.put("user_no", user_no);
		
		sqlSession.update("board.addhit",map);
		
		return sqlSession.selectOne("board.findContents",map);
	}
	
	public int delete(String newtitle,String title,String reg_date,String user_no) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("newtitle", newtitle);
		map.put("title", title);
		map.put("reg_date", reg_date);
		map.put("user_no", user_no);
		
		return sqlSession.delete("board.delete",map);
	}
	
	public int update(String newtitle,String contents,String title,String reg_date,String user_no) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("newtitle", newtitle);
		map.put("contents", contents);
		map.put("title", title);
		map.put("reg_date", reg_date);
		map.put("user_no", user_no);
		
		return sqlSession.update("board.update",map);
		
	}
	
	public int write(String title, String contents, String user_no) {
		
		int topGroupNum = sqlSession.selectOne("board.findTopGroupNo");
		int result = topGroupNum+1;
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("contents", contents);
		map.put("group_no", Integer.toString(result));
		map.put("user_no", user_no);
		
		return sqlSession.insert("board.write",map);
		
	}
	
	public int reply(String title, String contents, String user_no, String order_no, String group_no, String depth) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("group_no", group_no);
		map.put("order_no", order_no);
		sqlSession.update("board.reply",map);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("title", title);
		map2.put("contents", contents);
		map2.put("user_no", user_no);
		map2.put("group_no", group_no);
		map2.put("order_no", order_no);
		map2.put("depth", depth);
		
		return sqlSession.insert("board.writereply",map2);
	}
	
}
