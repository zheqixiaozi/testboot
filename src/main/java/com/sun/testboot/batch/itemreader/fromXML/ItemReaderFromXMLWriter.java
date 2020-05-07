package com.sun.testboot.batch.itemreader.fromXML;


import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ItemReaderFromXMLWriter")
public class ItemReaderFromXMLWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer:list){
            System.out.println(customer);
        }
    }
}
