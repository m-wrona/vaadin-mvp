package test.java.com.mwronski.wannabe.web;

import com.vaadin.Application;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

/**
 * Test cases related with user's session data
 *
 * @author Michal Wronski
 * @date 16-02-2014
 * @see UserSessionData
 */
public class UserSessionDataTest {

//    @Mock
//    private Application mockApplication;
//
//    @Test
//    public void testTransactionListener() {
//        UserOrders userData = new UserOrders(mockApplication);
//        assertEquals(userData, UserOrders.instance());
//        userData.transactionStart(mockApplication, null);
//        assertEquals(userData, UserOrders.instance());
//        userData.transactionEnd(mockApplication, null);
//        assertNull(UserOrders.instance());
//    }
//
//    @Test
//    public void testTransactionListenerForDifferentInstance() {
//        Application mockApplication2 = mock(Application.class);
//        UserOrders userData = new UserOrders(mockApplication);
//        assertEquals(userData, UserOrders.instance());
//        userData.transactionStart(mockApplication2, null);
//        assertEquals(userData, UserOrders.instance());
//        userData.transactionEnd(mockApplication2, null);
//        assertEquals(userData, UserOrders.instance());
//    }

}
