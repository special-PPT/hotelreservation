package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static CustomerService customerService = null;
    private static Map<String, Customer> customerMap = new HashMap<>();

// use singleton pattern to initialize customerservice
    private CustomerService() { }
    public static CustomerService getInstance() {
        if(customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }
    public void addCustomer(String email, String firstName, String lastName) {
        if(customerMap.containsKey(email)){
            System.out.println("This email is already registered");
        } else {
            customerMap.put(email, new Customer(firstName, lastName, email));
        }
    }

    public Customer getCustomer(String customerEmail){
        if(!customerMap.containsKey(customerEmail)) {
            System.out.println("This account does not exist");
            return null;
            // do not sure if it could return null ?
        } else return customerMap.get(customerEmail);
    }

    public Collection<Customer> getAllCustomer() {
        return customerMap.values();
    }
}
