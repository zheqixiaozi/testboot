package com.sun.testboot.batch.itemreader.restartDemo;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component("restartDemoReader")
public class RestartDemoReader implements ItemStreamReader {

    FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
    private Long curLine=0L;
    private boolean restart = false;
    private ExecutionContext executionContext;

    RestartDemoReader(){
        reader.setResource(new ClassPathResource("restart.csv"));
        reader.setLinesToSkip(1);
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id","firstName","lastName","birthday");
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(fieldSet -> {
            return Customer.builder().id(fieldSet.readInt("id"))
                    .firstName(fieldSet.readString("firstName"))
                    .lastName(fieldSet.readString("lastName"))
                    .birthday(fieldSet.readString("birthday"))
                    .build();
        });
        reader.setLineMapper(mapper);
    }
    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Customer customer = null;
        this.curLine++;
        if (restart){
            reader.setLinesToSkip(this.curLine.intValue() - 1);
            restart = false;
            System.out.println("start reading from line:"+this.curLine);
        }
        reader.open(executionContext);
        customer = reader.read();
        if (customer != null){
            if (customer.getFirstName().equals("wrongName")){
                throw new RuntimeException("something wrong, customer id: "+customer.getId());
            }
        }else {
            curLine --;
        }
        return customer;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
        if (executionContext.containsKey("curLine")){
            this.curLine = executionContext.getLong("curLine");
            this.restart = true;
        }else {
            this.curLine = 0L;
            executionContext.put("curLine",this.curLine.intValue());
        }

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("update curLine:"+this.curLine);
        executionContext.put("curLine",this.curLine);
    }

    @Override
    public void close() throws ItemStreamException {
        System.out.println("close.....");
    }
}
