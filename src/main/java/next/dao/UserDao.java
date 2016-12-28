package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        InsertJdbcTemplate jdbcTemplate = new InsertJdbcTemplate() {
        	@Override
        	void setInsertQueryParameter(PreparedStatement pstmt) throws SQLException {
        		pstmt.setString(1, user.getUserId());
        		pstmt.setString(2, user.getPassword());
        		pstmt.setString(3, user.getName());
        		pstmt.setString(4, user.getEmail());
        	}
        	
        	@Override
        	String createInsertQuery() {
        		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        		return sql;
        	}
        };
        
        jdbcTemplate.insert();
    }
/*
    public void update(User user) throws SQLException {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
    	jdbcTemplate.update(user);
    }
*/

    public List<User> findAll() throws SQLException {
    	SelectJdbcTemplate jdbcTemplate = new SelectJdbcTemplate() {
    		@Override
    		String createFindUserQuery() { return null;}
    		
    		@Override
    		String createSelectAllQuery() {
    			return "SELECT userId, password, name, email FROM USERS";
    		}
    	};
    	
    	RowMapper<User> rm = new RowMapper<User>() {
    		@Override
    		public User mapRow(ResultSet rs, int num) throws SQLException {
    			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
	                     rs.getString("email"));
    		}
    	};
    	
    	return jdbcTemplate.findAll(rm);
    }

    public User findByUserId(String userId) throws SQLException {
    	SelectJdbcTemplate jdbcTemplate = new SelectJdbcTemplate() {
    		@Override
    		String createFindUserQuery() {
    			return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    		}
    		
    		@Override
    		String createSelectAllQuery() { return null; }
		
    	};

    	return jdbcTemplate.findByUserId(userId);
    }
    
    
}
