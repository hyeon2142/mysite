package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo findByEmailAndPassword(String email,String password) throws UserRepositoryException {
			
			
			Map<String, String> map = new HashMap<>();
			map.put("e", email);
			map.put("p", password);
			
			return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}
	
	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		
		return count == 1;

//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			
//			String sql =
//					" insert " + 
//					"   into user " + 
//					" values(null, ?, ?, ?, ?, now())";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, vo.getName());
//			pstmt.setString(2, vo.getEmail());
//			pstmt.setString(3, vo.getPassword());
//			pstmt.setString(4, vo.getGender());
//			
//			int count = pstmt.executeUpdate();
//			result = count == 1;			
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if(pstmt != null) {
//					pstmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}		
		
		
	}
	

	public UserVo findByNo(Long no) throws UserRepositoryException {
		
		return sqlSession.selectOne("user.findByNo",no);
	}

	public boolean update(UserVo vo) {
		int count = sqlSession.update("user.update",vo);
		return count == 1;
	}

	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail",email);
	}
}
