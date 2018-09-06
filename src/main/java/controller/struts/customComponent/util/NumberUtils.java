/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.customComponent.util;

/**
 *
 * @author SuslovAI
 */
public class NumberUtils {
    
    public static <T extends Comparable<T>> boolean isFristValueGreaterThanSecond(T firstValue,T secondValue,boolean inclusive){
        int inclusiveCompareOffset = inclusive ? 1 : 0;
        return (firstValue.compareTo(secondValue)>(0 - inclusiveCompareOffset));
    }
    
    public static <T extends Comparable<T>> boolean isFristValueLessThanSecond(T firstValue,T secondValue,boolean inclusive){
        return !isFristValueGreaterThanSecond(firstValue,secondValue,!inclusive);
    }
    
    public static <T extends Comparable<T>> boolean isFirstValueBetween(T value,T startValue,boolean startValueInclusive,T endValue,boolean endValueInclusive){
        return (isFristValueGreaterThanSecond(value,startValue,startValueInclusive) 
            && isFristValueLessThanSecond(value,endValue,endValueInclusive));
    }
    
}
