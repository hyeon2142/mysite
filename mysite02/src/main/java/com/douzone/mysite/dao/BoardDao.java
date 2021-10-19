package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	
	public int getRowCount() {
		
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = getConnection();
			
			String sql = "SELECT count(title) from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			

			
			while(rs.next()) {
				count = rs.getInt(1);
				
			}
			
			return count;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
		
	}
	
	public List<BoardVo> findAll(String page) {
		
		
		List<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql = "SELECT board.no,title,contents,hit,DATE_FORMAT(reg_date,'%Y-%m-%d %h:%i:%s'),group_no,order_no,depth,user_no,name from board,user where board.user_no = user.no order by group_no desc, order_no asc Limit ?,?";
			pstmt = conn.prepareStatement(sql);
			int now = Integer.parseInt(page);
			pstmt.setInt(1, (now-1)*5);
			pstmt.setInt(2,5);
			rs = pstmt.executeQuery();
			

			
			while(rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date =rs.getString(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				int user_no = rs.getInt(9);
				String writer = rs.getString(10);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setWriter(writer);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	// update
		public void update(Long no,String title,String rdate,String updateTitle,String contents) {
				boolean result = false;
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {

					conn = getConnection();
					
					String sql;
					// 3. SQL 준비
					sql = "update board set title = ?, contents = ? where user_no = ? and title = ? and DATE_FORMAT(reg_date,'%Y-%m-%d %h:%i:%s') = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, updateTitle);
					pstmt.setString(2, contents);
					pstmt.setLong(3, no);
					pstmt.setString(4, title);
					pstmt.setString(5, rdate);
					
					// 5. SQL 실행

					int count = pstmt.executeUpdate(); // 잘되면 1 안되면 0

					result = count == 1;


				} catch (SQLException e) {
					System.out.println("error : " + e);
				} finally {
					// clean up
					try {
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		
	public int findTopGroupNo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int groupNo = 0;

		try {
			conn = getConnection();

			String sql = " select max(group_no) from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				groupNo = rs.getInt(1);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return groupNo;
	}
	
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql = "insert into board values(null,?,?,0,now(),?,1,1,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, findTopGroupNo()+1);
			pstmt.setInt(4, findByUserNo(vo.getEmail()));
			int count = pstmt.executeUpdate();
			result = count == 1;
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}
	
	public int findByUserNo(String email) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int userNo = 0;

		try {
			conn = getConnection();

			String sql = " select no from user where email = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				userNo = rs.getInt(1);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userNo;
	}
	
	public BoardVo findContents(String title,String reg_date,int user_no) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo vo = null;

		try {
			conn = getConnection();

			String sql = " select title,contents from board where title=? and DATE_FORMAT(reg_date,'%Y-%m-%d %h:%i:%s') = ? and user_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, reg_date);
			pstmt.setInt(3, user_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new BoardVo();
				String getTitle = rs.getString(1);
				String getContents = rs.getString(2);
				
				vo.setTitle(getTitle);
				vo.setContents(getContents);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "root", "root");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}	

}
