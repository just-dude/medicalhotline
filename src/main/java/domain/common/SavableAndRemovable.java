package domain.common;

import domain.common.exception.BusinessException;

/**
 * Created by SuslovAI on 02.10.2017.
 */
public interface SavableAndRemovable<T> {

    <S extends T> S save() throws BusinessException;

    void remove() throws BusinessException;
}
