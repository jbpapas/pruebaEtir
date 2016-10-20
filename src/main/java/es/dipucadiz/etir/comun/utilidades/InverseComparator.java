package es.dipucadiz.etir.comun.utilidades;

import java.util.Comparator;

/**
 *
 * Invierte el Comparador original
 *  -1 ->  1
 *   1 -> -1
 *   0 ->  0
 */
public class InverseComparator implements Comparator {
    Comparator original;
    public InverseComparator( Comparator original) {
        this.original = original;
    }
    public int compare( Object arg0, Object arg1 )
    {
        return -1 * original.compare( arg0, arg1);
    }
}

