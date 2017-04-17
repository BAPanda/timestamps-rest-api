package timestamps.dao.impl;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import timestamps.dao.EntityDAO;
import timestamps.models.Entity;

public class EntityDAOImpl implements EntityDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Entity getByID(BigInteger ID) {
		
		String query = "SELECT \"Entity\".\"ID\", \"Entity\".\"Name\",  \"Group\", \"Address\"" + 
				  "FROM \"Entity\" JOIN \"Group\" ON \"Entity\".\"Group\" = \"Group\".\"ID\" WHERE \"Entity\".\"ID\" = ?";
		
		return jdbcTemplate.query(query, new ResultSetExtractor<Entity>() {
			@Override
			public Entity extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) 
					return new EntityRowMapper().mapRow(rs, 1);
				else 
					return null;
				}
			}, ID.intValue());
	}

	@Override
	public List<Entity> getAll() {
		String query = "SELECT \"Entity\".\"ID\", \"Entity\".\"Name\",  \"Group\", \"Address\"" + 
				  "FROM \"Entity\" JOIN \"Group\" ON \"Entity\".\"Group\" = \"Group\".\"ID\"";
		
		return jdbcTemplate.query(query, new EntityRowMapper());
	}

	@Override
	public Entity getByName(String name) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("SELECT \"ID\", \"Name\" FROM \"Entity\" WHERE \"Name\" = ?", new ResultSetExtractor<Entity>() {
			@Override
			public Entity extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) 
					return new EntityRowMapper().mapRow(rs, 1);
				else 
					return null;
				}
			}, name);
	}
	
	@Override
	public void add(Entity entity) {		
		jdbcTemplate.update("INSERT  \"Entity\" (\"ID\", \"Name\") VALUES (?, ?)", entity.getID(), entity.getName());
	}
	
	class EntityRowMapper implements RowMapper<Entity>{

		@Override
		public Entity mapRow(ResultSet rs, int i) throws SQLException {
			Entity entity = new Entity();
			
			entity.setID(rs.getBigDecimal("ID").toBigInteger());
			entity.setName(rs.getString("Name"));
			
			return entity;
		}
		
	}

	

}
