package com.sun.testboot.batch.itemprocessor;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("firstNameToUpperProcessor")
public class FirstNameToUpperProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        return new Customer(item.getId(),item.getFirstName().toUpperCase(),
                item.getLastName(),item.getBirsthday(),item.getBirthday());
    }
}
