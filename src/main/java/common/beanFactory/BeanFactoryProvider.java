/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 *
 * @author SuslovAI
 */
public class BeanFactoryProvider implements BeanFactoryAware{
    
    private static BeanFactory bf = null;
    
    public static BeanFactory getBeanFactory() {
        return bf;
    }
    
    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        this.bf = bf;
    }
}
