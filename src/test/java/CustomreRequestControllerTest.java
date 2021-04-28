import com.interview.blueyonder.controller.CustomerRequestController;
import com.interview.blueyonder.resources.Customer;
import com.interview.blueyonder.services.CustomerServices;
import org.hamcrest.core.AnyOf;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.io.IOException;

public class CustomreRequestControllerTest {

    @Test
    public void testPushRecord(){
        CustomerRequestController customerRequestController = new CustomerRequestController();
        CustomerServices customerServicesMock = Mockito.mock(CustomerServices.class);
        Customer customerarg = new Customer();
        customerarg.setEmailAddress("test.id@gmail.com");
        customerarg.setFirstName("testname");
        customerarg.setLastName("testLname");
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        customerRequestController.pushCustomerRecord(customerarg);
        try {
            Mockito.verify(customerServicesMock,Mockito.times(1)).pushCustomerRecord(argumentCaptor.capture());
            Assert.assertEquals(argumentCaptor.getValue().getEmailAddress(), customerarg.getEmailAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
