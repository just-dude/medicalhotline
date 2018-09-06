/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.customComponent.comparator;

import java.util.Comparator;


public class NaturalOrderComparator<T extends Comparable<T>> implements Comparator<T> {

    public static Comparator INSTANCE = new NaturalOrderComparator();
    
    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }
    
}
