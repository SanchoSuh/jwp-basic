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
import next.model.User;

public abstract class JdbcTemplate {
	private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);
	
	abstract void setQueryParameter(PreparedStatement pstmt) throws SQLException;
	
	public void update(String sql) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try {
			con = ConnectionManager.getConnection();
			pstmt  = con.prepareStatement(sql);
			setQueryParameter(pstmt);

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

	public <T> T findById(String sql, RowMapper<T> rm) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		
		try {
			con = ConnectionManager.getConnection();
	        pstmt = con.prepareStatement(sql);
	        setQueryParameter(pstmt);
	        
	        rs = pstmt.executeQuery();
	        
	        T t = null;
	        
	        if (rs.next()) {
	             t = rm.mapRow(rs, 0);
	        }

	        return t;
		}finally {
			if(con != null) 
				con.close();
			if(pstmt != null) 
				pstmt.close();
			if(rs != null) 
				rs.close();
		}
	}
	
	public <T> List<T> findAll(String sql, RowMapper<T> rm) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		ArrayList<T> elements = new ArrayList<T>();
		
		try {
			con = ConnectionManager.getConnection();
			log.debug(sql);
	    	pstmt = con.prepareStatement(sql);
	    	setQueryParameter(pstmt);
	    	
	    	log.debug(pstmt.toString());
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
	    	if(rs != null) 
				rs.close();
		}
		
    	return elements; 
	}
}
