package com.sun.testboot.batch.itemprocessor;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("idFilterProcessor")
public class IdFilterProcessor implements ItemProcessor<Customer,Customer> {

    /**
     * 只返回id是偶数的项
     * @param item
     */
    @Override
    public Customer process(Customer item) throws Exception {
        if (item.getId() % 2 == 0) {
            return item;
        }else {
            return null;
        }
    }
}
