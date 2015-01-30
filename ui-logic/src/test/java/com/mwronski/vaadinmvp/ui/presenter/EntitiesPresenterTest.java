package com.mwronski.vaadinmvp.ui.presenter;

import com.mwronski.vaadinmvp.services.Service;
import com.mwronski.vaadinmvp.ui.view.ListView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Common test cases for generic entities presenter
 *
 * @param <K> type of primary unique key of entity
 * @param <T> type of entities provided by this service
 * @param <V> type of view controlled by presenter
 * @author Michal Wronski
 * @date 23-03-2014
 * @see EntitiesPresenter
 */
@RunWith(PowerMockRunner.class)
public abstract class EntitiesPresenterTest<K, T, V extends ListView> {

    private V mockView;
    private Service<K, T> mockReadService;
    private EntitiesPresenter<T, V> presenter;

    /**
     * Create mocked view that will be controlled by presenter
     *
     * @return mocked view
     */
    protected abstract V createMockView();

    /**
     * Create mocked service that will provide data for test
     *
     * @return mocked service
     */
    protected abstract Service<K, T> createMockService();

    /**
     * Get mocked view controlled by presenter
     *
     * @return non-null instance
     */
    protected final V getMockView() {
        return mockView;
    }

    /**
     * Get mocked service providing data for tests
     *
     * @return non-null instance
     */
    protected final Service<K, T> getMockReadService() {
        return mockReadService;
    }

    /**
     * Get presenter under tests
     *
     * @return non-null instance
     */
    protected final EntitiesPresenter<T, V> getPresenter() {
        return presenter;
    }

    /**
     * Create presenter that will be tested
     *
     * @param mockReadService service that will provide mock data
     * @param mockView view that will be controlled by presenter
     * @return non-null instance of newly created presenter under tests
     */
    protected abstract EntitiesPresenter<T, V> createPresenter(Service<K, T> mockReadService, V mockView);

    @Before
    public void setUp() {
        mockView = createMockView();
        mockReadService = createMockService();
        presenter = createPresenter(mockReadService, mockView);

    }

    /**
     * Check whether after creation of presenter, view is initialized properly
     */
    @Test
    public void testViewInitialization() {
        verify(mockView).setPresenter(presenter);
        verify(mockView).init();
        verifyNoMoreInteractions(mockView);
    }

    /**
     * Check whether navigation to next page works correctly
     */
    @Test
    public void testDisplayNextPages() {
        //given
        presenter.setPageSize(2);
        when(mockReadService.size()).thenReturn(3);
        when(mockReadService.get(anyInt(), eq(2))).thenReturn(new ArrayList<T>());
        //when display first page
        presenter.display();
        //then
        verify(mockView).setPreviousPageEnabled(false);
        verify(mockView).setNextPageEnabled(true);
        //when display second page
        presenter.nextPage();
        //then
        verify(mockView).setPreviousPageEnabled(true);
        verify(mockView).setNextPageEnabled(false);
    }

    /**
     * Check whether navigation to previous page works correctly
     */
    @Test
    public void testDisplayPrevPages() {
        //given
        presenter.setPageSize(2);
        when(mockReadService.size()).thenReturn(3);
        when(mockReadService.get(anyInt(), eq(2))).thenReturn(new ArrayList<T>());
        //when display second page
        presenter.nextPage();
        //then
        verify(mockView).setPreviousPageEnabled(true);
        verify(mockView).setNextPageEnabled(false);
        //when display second page
        presenter.previousPage();
        //then
        verify(mockView).setPreviousPageEnabled(false);
        verify(mockView).setNextPageEnabled(true);
    }

    /**
     * Check whether presenter is displaying data correctly on view
     */
    @Test
    public abstract void testDisplayData();

}

