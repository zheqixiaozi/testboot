package com.sun.testboot.batch.itemreader.fromFile;


import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ItemReaderFromFileWriter")
public class ItemReaderFromFileWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer:list){
            System.out.println(customer);
        }
    }
}
