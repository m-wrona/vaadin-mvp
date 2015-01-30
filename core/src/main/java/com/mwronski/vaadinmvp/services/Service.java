package com.mwronski.vaadinmvp.services;

import java.util.List;

/**
 * Contract of the service that will provide basic access to the data.
 *
 * @param <K> type of primary unique key of entity
 * @param <T> type of entities provided by this service
 * @author Michal Wronski
 * @date 04-03-2014
 */
public interface Service<K, T> {

    /**
     * Find entity by given key
     *
     * @param key unique primary key of searched entity
     * @return found entity, null if no entity was found
     * @throws ServiceException for access issues
     */
    T find(K key) throws ServiceException;

    /**
     * Save entities. Service will decide itself whether entities should be created or just updated.
     *
     * @param entities to be saved
     * @throws ServiceException for access issues
     */
    void save(T... entities) throws ServiceException;

    /**
     * Get chosen entities
     *
     * @param startIndex index of first entity to get
     * @param size number of entities to get
     * @return non-nullable collection with chosen entities
     * @throws ServiceException for access issues
     */
    List<T> get(int startIndex, int size) throws ServiceException;

    /**
     * Check how many entities are available in the service
     *
     * @return number of entities
     * @throws ServiceException for access issues
     */
    int size() throws ServiceException;
}
