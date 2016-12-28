package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import core.jdbc.ConnectionManager;
import next.controller.CreateUserController;
import next.model.User;

public abstract class SelectJdbcTemplate {
	private static final Logger log = LoggerFactory.getLogger(SelectJdbcTemplate.class);
	
	public User findByUserId(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		
		try {
			con = ConnectionManager.getConnection();
			String sql = createFindUserQuery();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, userId);

	        rs = pstmt.executeQuery();
	        User user = null;
	        if (rs.next()) {
	             user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
	                     rs.getString("email"));
	        }

	        return user;
		}finally {
			if(con != null) {
				con.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
		}
	}

	abstract String createFindUserQuery();
	
	public <T> List<T> findAll(RowMapper<T> rm) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		ArrayList<T> elements = new ArrayList<T>();
		
		try {
			con = ConnectionManager.getConnection();
			String sql = createSelectAllQuery();
			log.debug(sql);
	    	pstmt = con.prepareStatement(sql);
	    	
	    	rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) {
	    		T t = rm.mapRow(rs, 0);
	    		log.debug(rs.toString() + t.toString());
	    		elements.add(t);
	    	}        	
		} finally {    	
	    	if(con!= null)
	    		con.close();
	    	if(pstmt != null)
	    		pstmt.close();
		}
		
    	return elements; 
	}

	abstract String createSelectAllQuery();

}
