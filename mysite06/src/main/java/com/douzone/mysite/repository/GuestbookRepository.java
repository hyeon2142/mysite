package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<GuestbookVo> findAll() throws GuestbookRepositoryException {
		
		
		return sqlSession.selectList("guestbook.findAll");
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//				
//		try {
//			conn = dataSource.getConnection();
//			
//			String sql =
//				"   select no, name, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, message" +
//				"     from guestbook" +
//				" order by reg_date desc";
//			pstmt = conn.prepareStatement(sql);
//			
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				Long no = rs.getLong(1);
//				String name = rs.getString(2);
//				String regDate = rs.getString(3);
//				String message = rs.getString(4);
//				
//				GuestbookVo vo = new GuestbookVo();
//				vo.setNo(no);
//				vo.setName(name);
//				vo.setRegDate(regDate);
//				vo.setMessage(message);
//				
//				list.add(vo);
//			}
//			
//		} catch (SQLException e) {
//			throw new GuestbookRepositoryException(e.toString());
//		} finally {
//			try {
//				if(rs != null) {
//					rs.close();
//				}
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
	
	public boolean delete(GuestbookVo vo) {
		int count = sqlSession.delete("guestbook.delete", vo);
		return count == 1;

//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			
//			String sql =
//					" delete" +
//					"   from guestbook" +
//					"  where no=?" +
//					"    and password=?";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setLong(1, vo.getNo());
//			pstmt.setString(2, vo.getPassword());
//			
//			int count = pstmt.executeUpdate();
//			result = count == 1;
//			
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
		
		//return result;		
	}
	
	public boolean insert(GuestbookVo vo) {
		
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println(vo);
		return count == 1;
//		boolean result = false;
//
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			
//			String sql =
//					" insert" +
//					"   into guestbook" +
//					" values (null, ?, ?, ?, now())";
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, vo.getName());
//			pstmt.setString(2, vo.getPassword());
//			pstmt.setString(3, vo.getMessage());
//			
//			int count = pstmt.executeUpdate();
//			result = count == 1;
//			
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
		
		//return result;
	}
	
}