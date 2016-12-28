package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class InsertJdbcTemplate {
	
	public void insert() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try {
			con = ConnectionManager.getConnection();
			String sql = createInsertQuery();
			pstmt  = con.prepareStatement(sql);
	        setInsertQueryParameter(pstmt);

	        pstmt.executeUpdate();
		} finally {
			if(con != null) {
				con.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
		}
	}

	abstract void setInsertQueryParameter(PreparedStatement pstmt) throws SQLException;
	abstract String createInsertQuery();
	
	public void update(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try {
			con = ConnectionManager.getConnection();
			String sql = createUpdateQuery();
	        pstmt = con.prepareStatement(sql);
	      
	        setUpdateQueryParamenter(user, pstmt);

	        pstmt.executeUpdate();
		} finally {
			if(con != null) {
				con.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
		}
		
	}
	

	private void setUpdateQueryParamenter(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getUserId());
	}

	private String createUpdateQuery() {
		String sql = "UPDATE USERS SET password=?, name=?, email=? where userid=?";
		return sql;
	}
}
