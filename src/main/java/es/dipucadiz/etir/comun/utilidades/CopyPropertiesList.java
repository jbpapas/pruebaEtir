package es.dipucadiz.etir.comun.utilidades;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;



/**
 * Clase de utilidad para convertir Listas de DTO en VO
 * 
 * Se especifica los tipos S (Source) y T (Target)
 */
final public class CopyPropertiesList<S,T> {
	
	public List<T> convertListDTOtoVO(List<S> listSource, Class<T> clazz) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
		List<T> listTarget = new ArrayList<T>();
		
		for(S source : listSource){
			T target = clazz.newInstance();
			
			PropertyUtils.copyProperties(target, source);
			
			listTarget.add(target);
		}
		
		return listTarget;
	}
}