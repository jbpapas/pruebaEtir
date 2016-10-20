package es.dipucadiz.etir.comun.vo;

import java.util.Comparator;
import java.util.Date;

import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb06.vo.ExpedienteVO;
import es.dipucadiz.etir.sb06.vo.ExpedienteVOComparator;

public enum GestorTareasSeguimientoHVOComparator implements Comparator<GestorTareasSeguimientoHGestorTareasVO> {
	FH_ACTUALIZACION {
		public int compare(GestorTareasSeguimientoHGestorTareasVO o1, GestorTareasSeguimientoHGestorTareasVO o2) {
			try {
				//Date fxAperturaO1 = Utilidades.DDMMYYYYToDate(o1.getFhActualizacion());
				//Date fxAperturaO2 = Utilidades.DDMMYYYYToDate(o2.getFhActualizacion());
				return Integer.valueOf(o1.getFhActualizacion().compareTo(o2.getFhActualizacion()));
			} catch (Exception e) {
				return Integer.valueOf(o1.getFhActualizacion().compareTo(o2.getFhActualizacion()));
			}			
		}
	};
	
	
	public static Comparator<GestorTareasSeguimientoHGestorTareasVO> getComparator(final GestorTareasSeguimientoHVOComparator... multipleOptions) {
		return new Comparator<GestorTareasSeguimientoHGestorTareasVO>() {
			public int compare(GestorTareasSeguimientoHGestorTareasVO o1, GestorTareasSeguimientoHGestorTareasVO o2) {
				for (GestorTareasSeguimientoHVOComparator option : multipleOptions) {
					int result = option.compare(o1, o2);
					if (result != 0) {
						return result;
					}
				}
				return 0;
			}
		};
	}
	
	
	public static Comparator<GestorTareasSeguimientoHGestorTareasVO> asc(final Comparator<GestorTareasSeguimientoHGestorTareasVO> otro) {
		return new Comparator<GestorTareasSeguimientoHGestorTareasVO>() {
			public int compare(GestorTareasSeguimientoHGestorTareasVO o1, GestorTareasSeguimientoHGestorTareasVO o2) {
				return otro.compare(o1, o2);
			}
		};
	}	
}

