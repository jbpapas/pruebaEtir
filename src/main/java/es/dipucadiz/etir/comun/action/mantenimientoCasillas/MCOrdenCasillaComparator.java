package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

public class MCOrdenCasillaComparator implements Comparator, Serializable {
	private static final long serialVersionUID = 8204719350417477757L;

	private Map  _data = null;
	
	public MCOrdenCasillaComparator (Map data){
		super();
		_data = data;
	}

     public int compare(Object o1, Object o2) {
    	 short e1 = ((MCInformacionCasillaVO)_data.get(o1)).getOrden();
    	 short e2 = ((MCInformacionCasillaVO)_data.get(o2)).getOrden();
    	 
    	 if (e1<e2) return -1;
    	 if (e1>e2) return 1;
    	 if (e1==e2) return 0;
    	 
         return 0;
     }
}