package com.sw.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sw.dto.BDto;

public class BDaoImpl implements BDao {
	public ArrayList<BDto> showBoardList() {
		ArrayList<BDto> dtosList = new ArrayList<BDto>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String query = "SELECT * FROM board order by bId";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int dbId = rs.getInt("bId");
				String dbName = rs.getString("bName");
				String dbTitle = rs.getString("bTitle");
				String dbContent = rs.getString("bContent");
				Timestamp dbDate = rs.getTimestamp("bDate");
				int dbHit = rs.getInt("bHit");
				System.out.println(dbId + " " + dbName + " " + dbTitle + " " + dbContent + " " + dbDate + " " + dbHit);
				System.out.println(dbDate);
				BDto bto = new BDto(dbId, dbName, dbTitle, dbContent, dbDate, dbHit);
				dtosList.add(bto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, pstmt, con);
		}
		
		return dtosList;
	}
	
	public int writeContent(BDto bdto) {
		int ret = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String bName = bdto.getbName();
		String bTitle = bdto.getbTitle();
		String bContent = bdto.getbContent();
		
		try {
			con = getConnection();
			String query = "insert into board values (null, ?, ?, ?, CURDATE(), 0, null, null, null)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			ret = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, pstmt, con);
		}
		
		return ret;
	}
	
	private void upHit(int bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String query = "update board set bHit = bHit + 1 where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, pstmt, con);
		}
	}

	public BDto viewContent(int bId) {
		upHit(bId);
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String query = "select * from board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int dbId = rs.getInt("bId");
				String dbName = rs.getString("bName");
				String dbTitle = rs.getString("bTitle");
				String dbContent = rs.getString("bContent");
				Timestamp dbDate = rs.getTimestamp("bDate");
				int dbHit = rs.getInt("bHit");
				dto = new BDto(dbId, dbName, dbTitle, dbContent, dbDate, dbHit);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, pstmt, con);
		}
		return dto;
	}
	
	public int modifyContent(BDto bdto) {
		int ret = 0;
		int bId = bdto.getbId();
		String bName = bdto.getbName();
		String bTitle = bdto.getbTitle();
		String bContent = bdto.getbContent();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String query = "update board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, bId);
			ret = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, pstmt, con);
		}
		
		return ret;
	}
	
	public int deleteContent(int bId) {
		int ret = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String query = "delete from board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			ret = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(rs, pstmt, con);
		}
		
		return ret;
	}
	
	public Connection getConnection() {
		
		Connection conn=null;		
		String DBName = "jsp_servlet_db";
		String dbURL = "jdbc:mysql://localhost:3306/" + DBName;
		String sslStr="?useSSL=false";

		try {
			
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.println("JDBC driver load success");

			conn = DriverManager.getConnection(dbURL+sslStr
					, "root","1234"); 			
			System.out.println("DB connection success");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver load fail !!");
		} catch (SQLException e) {
			System.out.println("DB connection fail !!");
		}
		
		return conn;
	}
	public void closeConnection(ResultSet set, PreparedStatement pstmt, Connection connection) {
		if(set!=null)
		{
			try {
			set.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		if(pstmt!=null)
		{
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
