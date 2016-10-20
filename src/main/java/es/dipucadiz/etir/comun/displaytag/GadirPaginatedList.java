package es.dipucadiz.etir.comun.displaytag;

import java.io.Serializable;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * Clase responsable de la paginación manual con displayTag.
 */
public class GadirPaginatedList<E> implements PaginatedList, Serializable {
	private static final long serialVersionUID = -4031749949220956159L;

	public static final String ASCENDING = "asc";
	public static final String DESCENDING = "desc";
	
	private int fullListSize;
	private List<E> list;
	private int objectsPerPage;
	private int pageNumber;
	private String searchId;
	private String sortCriterion;
	private SortOrderEnum sortDirection;

	public GadirPaginatedList(List<E> list, int fullListSize, int objectsPerPage, int page, String searchId, String sortCriterion, String sortDirection) {
		this.list = list;
		this.fullListSize = fullListSize;
		this.objectsPerPage = objectsPerPage;
		try {
			this.pageNumber = Integer.valueOf(page);
		} catch (Exception e) {
			this.pageNumber = 1;
		}
		this.searchId = searchId;
		this.sortCriterion = sortCriterion;
		if (DESCENDING.equals(sortDirection)) {
			this.sortDirection = SortOrderEnum.DESCENDING;
		} else {
			this.sortDirection = SortOrderEnum.ASCENDING;
		}
	}
	
	
	public int getFullListSize() {
		return fullListSize;
	}

	
	public List<E> getList() {
		return list;
	}

	
	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	
	public int getPageNumber() {
		return pageNumber;
	}

	
	public String getSearchId() {
		return searchId;
	}

	
	public String getSortCriterion() {
		return sortCriterion;
	}

	
	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}
}
