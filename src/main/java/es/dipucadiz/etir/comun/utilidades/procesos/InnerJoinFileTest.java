package es.dipucadiz.etir.comun.utilidades.procesos;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class InnerJoinFileTest {
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		test();
	}
	
	public static void test() throws UnsupportedEncodingException, FileNotFoundException {
		//String[] args = {"C:\\temp\\TASACONES\\movidos\\TASACON_izq.txt", "C:\\temp\\TASACONES\\movidos\\AYCALLES_der.txt", "C:\\temp\\TASACONES\\movidos\\resultado.txt", "2"};
		//String[] args = {"C:\\temp\\TASACONES\\TASACON_izq.txt", "C:\\temp\\TASACONES\\AYCALLES_der.txt", "C:\\temp\\TASACONES\\resultado.txt", "1"};
		//String[] args = {"C:\\temp\\TASACONES2\\TasaconBS1", "C:\\temp\\TASACONES2\\AycallesBS", "C:\\temp\\TASACONES2\\resultado.txt", "1"};
		String[] args = {"C:\\temp\\TASACON3\\TASACON 037 08 4T.txt", "C:\\temp\\TASACON3\\AYCALLES 037 08 4T.txt", "C:\\temp\\TASACON3\\resultado.txt", "1"};
		InnerJoinFile.main(args);
		
	}
	
}
