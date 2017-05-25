package mx.com.amx.unotv.sitemap.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import mx.com.amx.unotv.sitemap.dao.SiteMapDAO;
import mx.com.amx.unotv.sitemap.dto.NotaDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("siteMapController")
public class SiteMapController {
	
	private static Logger logger=Logger.getLogger(SiteMapController.class);
	
	private SiteMapDAO siteMapDAO;
	
	@RequestMapping( value = "getElementosNewsSiteMap" , method=RequestMethod.POST , headers="Accept=application/json" )
	@ResponseBody
	public List < NotaDTO > getElementosNewsSiteMap (@RequestBody int numElementos, HttpServletResponse  response ) {
		logger.info("===== getElementosNewsSiteMap =====");
		
		ArrayList<NotaDTO>listElementosInsertar=new ArrayList<NotaDTO>();
		
		try {
			logger.info("numElementos: "+numElementos);
			listElementosInsertar=(ArrayList<NotaDTO>) siteMapDAO.getElementosNewsSiteMap(numElementos);
			
		} catch ( Exception e ){
			logger.error("Error getElementosNewsSiteMap[Controller] ",e);
			return Collections.emptyList();
		}	 	
		
		return listElementosInsertar;
	}
	
	
	@RequestMapping( value = "getElementosInsertar" , method=RequestMethod.POST , headers="Accept=application/json" )
	@ResponseBody
	public List < NotaDTO > getElementosInsertar (@RequestBody int numElementos, HttpServletResponse  response ) {
		logger.info("===== getElementosInsertar =====");
		
		ArrayList<NotaDTO>listElementosInsertar=new ArrayList<NotaDTO>();
		
		try {
			logger.info("numElementos: "+numElementos);
			listElementosInsertar=(ArrayList<NotaDTO>) siteMapDAO.getElementosInsertar(numElementos);
			
		} catch ( Exception e ){
			logger.error("Error getElementosInsertar[Controller] ",e);
			return Collections.emptyList();
		}	 	
		
		return listElementosInsertar;
	}
	
	@RequestMapping( value = "actualizarEstatusElemento" , method=RequestMethod.POST , headers="Accept=application/json" )
	@ResponseBody
	public Boolean actualizarEstatusElemento (@RequestBody String idContenido, HttpServletResponse  response ) {
		logger.info("===== actualizarEstatusElemento =====");
		boolean respuesta=false;
		try {
			logger.info("idContenido: "+idContenido);
			respuesta= siteMapDAO.actualizarEstatusElemento(idContenido);
			
		} catch ( Exception e ){
			logger.error("Error actualizarEstatusElemento[Controller] ",e);
		}	 	
		
		return respuesta;
	}
	
	@RequestMapping( value = "getSecuencia" , method=RequestMethod.GET , headers="Accept=application/json" )
	@ResponseBody
	public Integer getSecuencia (HttpServletResponse  response ) {
		logger.info("===== getSecuencia =====");
		int secuencia=0;
		try {
			
			secuencia = siteMapDAO.getSecuencia();
			
		} catch ( Exception e ){
			logger.error("Error getSecuencia[Controller] ",e);
		}	 	
		
		return secuencia;
	}
	
	@RequestMapping( value = "getSecuenciaActual" , method=RequestMethod.GET , headers="Accept=application/json" )
	@ResponseBody
	public Integer getSecuenciaActual (HttpServletResponse  response ) {
		logger.info("===== getSecuenciaActual =====");
		int secuencia=0;
		try {
			
			secuencia = siteMapDAO.getSecuenciaActual();
			
		} catch ( Exception e ){
			logger.error("Error getSecuenciaActual[Controller] ",e);
		}	 	
		
		return secuencia;
	}
	

	/**
	 * @return the siteMapDAO
	 */
	public SiteMapDAO getSiteMapDAO() {
		return siteMapDAO;
	}

	/**
	 * @param siteMapDAO the siteMapDAO to set
	 */
	@Autowired
	public void setSiteMapDAO(SiteMapDAO siteMapDAO) {
		this.siteMapDAO = siteMapDAO;
	}
	
	
	
}
