package com.sun.testboot.batch.itemwriter.toMultipleFile;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.classify.Classifier;

public class MyClassifier implements Classifier<Customer,ItemWriter<Customer>> {
    private ItemWriter<Customer> xmlItemWriter;
    private ItemWriter<Customer> jsonItemWriter;
    public MyClassifier(StaxEventItemWriter<Customer> xmlItemWriter, FlatFileItemWriter<Customer> jsonItemWriter) {
        this.xmlItemWriter = xmlItemWriter;
        this.jsonItemWriter = jsonItemWriter;
    }


    /**
     * 根据输入实体的值写入到不同的文件中
     * @param customer
     * @return
     */
    @Override
    public ItemWriter<Customer> classify(Customer customer) {
        return customer.getId() % 2 == 0 ? xmlItemWriter : jsonItemWriter;
    }
}
