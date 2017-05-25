package mx.com.amx.unotv.sitemap.dao;

import java.util.List;

import mx.com.amx.unotv.sitemap.dto.NotaDTO;

public interface ISiteMapDAO {

	public List<NotaDTO> getElementosInsertar(int numElementos);
	public boolean actualizarEstatusElemento(String id);
	public Integer getSecuencia();
	public Integer getSecuenciaActual();
	
}
