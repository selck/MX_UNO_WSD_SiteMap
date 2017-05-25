package mx.com.amx.unotv.sitemap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import mx.com.amx.unotv.sitemap.dto.NotaDTO;

@Component
@Qualifier("siteMapDAO")
public class SiteMapDAO implements ISiteMapDAO {
	
	private JdbcTemplate jdbcTemplate;
	private Logger logger= Logger.getLogger(SiteMapDAO.class);
	
	public List<NotaDTO> getElementosNewsSiteMap(int numElementos) {
		
		StringBuffer sql=new StringBuffer();
		try {
			sql.append(" SELECT  ");
			sql.append(" TO_CHAR(H.FD_FECHA_MODIFICACION, 'YYYY-MM-DD') AS fcFechaModificacion,  ");
			sql.append(" TO_CHAR(H.FD_FECHA_MODIFICACION, 'YYYY-MM-DD') AS fcFechaPubli,  ");
			sql.append(" H.FC_ID_CONTENIDO as fcIdContenido,  ");
			sql.append(" H.FC_TITULO as fcTitulo,  ");
			sql.append(" H.FC_NOMBRE as fcNombre,  ");
			sql.append(" H.FC_KEYWORDS as fcKeyWords, ");
			sql.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_ID_CATEGORIA,'')||'/detalle/'|| COALESCE(H.FC_NOMBRE,'')) as fcLinkDetalle  ");
			sql.append(" FROM WPDB2INS.UNO_MX_H_NOTA H,  ");
			sql.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION TS,  ");
			sql.append(" WPDB2INS.UNO_MX_C_SECCION S,  ");
			sql.append(" WPDB2INS.UNO_MX_C_CATEGORIA  C  ");
			sql.append(" WHERE  ");
			sql.append(" C.FC_ID_CATEGORIA=H.FC_ID_CATEGORIA  ");
			sql.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION  ");
			sql.append(" ORDER BY H.FD_FECHA_PUBLICACION DESC ");
			sql.append(" FETCH FIRST "+numElementos+" ROWS ONLY ");
			
			return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<NotaDTO>(NotaDTO.class));
			
		} catch (Exception e) {
			logger.error(" Error getElementosInsertar [DAO] ",e );
			logger.error("Error SQL: "+sql);
			return Collections.emptyList();
		}
		
	}
	public List<NotaDTO> getElementosInsertar(int numElementos) {
		
		StringBuffer sql=new StringBuffer();
		try {
			sql.append(" SELECT  ");
			sql.append(" TO_CHAR(H.FD_FECHA_MODIFICACION, 'YYYY-MM-DD') AS fcFechaModificacion,  ");
			sql.append(" TO_CHAR(H.FD_FECHA_MODIFICACION, 'YYYY-MM-DD') AS fcFechaPubli,  ");
			sql.append(" H.FC_ID_CONTENIDO as fcIdContenido,  ");
			sql.append(" H.FC_TITULO as fcTitulo,  ");
			sql.append(" H.FC_NOMBRE as fcNombre,  ");
			sql.append(" H.FC_KEYWORDS as fcKeyWords, ");
			sql.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_ID_CATEGORIA,'')||'/detalle/'|| COALESCE(H.FC_NOMBRE,'')) as fcLinkDetalle  ");
			sql.append(" FROM WPDB2INS.UNO_MX_H_NOTA H,  ");
			sql.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION TS,  ");
			sql.append(" WPDB2INS.UNO_MX_C_SECCION S,  ");
			sql.append(" WPDB2INS.UNO_MX_C_CATEGORIA  C  ");
			sql.append(" WHERE  ");
			sql.append(" C.FC_ID_CATEGORIA=H.FC_ID_CATEGORIA  ");
			sql.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION  ");
			sql.append(" AND H.FI_BAN_SITEMAP = 0 ");
			sql.append(" ORDER BY H.FD_FECHA_PUBLICACION DESC ");
			if(numElementos >0)
				sql.append(" FETCH FIRST "+numElementos+" ROWS ONLY ");
			
			return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<NotaDTO>(NotaDTO.class));
			
		} catch (Exception e) {
			logger.error(" Error getElementosInsertar [DAO] ",e );
			logger.error("Error SQL: "+sql);
			return Collections.emptyList();
		}
		
	}

	public boolean actualizarEstatusElemento(String id) {
		boolean bandera=true;
		try {			
			String strQuery = "UPDATE WPDB2INS.UNO_MX_H_NOTA SET FI_BAN_SITEMAP=1 WHERE FC_ID_CONTENIDO = ?";
			jdbcTemplate.update(strQuery, new Object[]{id});
			return bandera;
		} catch (Exception e) {
			logger.error(" Error actualizarEstatusElemento [DAO] ",e );
			bandera=false;
			return bandera;
		}	
	}

	public Integer getSecuencia(){
		Integer secuencia = 0;
		try {
			final StringBuffer sb = new StringBuffer();				
			sb.append( " SELECT WPDB2INS.UNO_MX_SEC_SITEMAP.NEXTVAL AS SECUENCIA FROM SYSIBM.SYSDUMMY1 ");	
			
			secuencia = (Integer) jdbcTemplate.query(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)
						throws SQLException {
					return connection.prepareStatement(sb.toString());
				}
			},
			new ResultSetExtractor<Integer>() {
				public Integer extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					int sec = 0;
					if( rs.next() ) 
				    	sec = rs.getInt("SECUENCIA");				    					   
				    return sec;
				}
			});								
		} catch (Exception e) {
			secuencia = 0;
			logger.error(" Error getSecuencia [DAO] ",e );			
		}
		return secuencia;
	}

	public Integer getSecuenciaActual(){
		Integer secuencia = 0;
		try {
			final StringBuffer sb = new StringBuffer();				
			sb.append( " SELECT WPDB2INS.UNO_MX_SEC_SITEMAP.CURRVAL AS SECUENCIA FROM SYSIBM.SYSDUMMY1");	
			logger.info("Obteniendo la secuencia actual "+sb.toString());
			secuencia = (Integer) jdbcTemplate.query(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)
						throws SQLException {
					return connection.prepareStatement(sb.toString());
				}
			},
			new ResultSetExtractor<Integer>() {
				public Integer extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					Integer sec = 0;
					if( rs.next() ) 
				    	sec = rs.getInt("SECUENCIA");				    					   
				    return sec;
				}
			});								
		} catch (Exception e) {
			secuencia = 0;
			logger.error(" Error getSecuenciaActual [DAO] ",e );				
		}
		return secuencia;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
