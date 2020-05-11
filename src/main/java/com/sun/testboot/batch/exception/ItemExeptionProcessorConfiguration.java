package com.sun.testboot.batch.exception;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("itemExeptionProcessor")
public class ItemExeptionProcessorConfiguration implements ItemProcessor<String,String> {
    private int count = 0;
    @Override
    public String process(String s) throws Exception {
        System.out.println("process item "+s);
        if ("18".equals(s)) {
            count ++;
            if (count >= 3){
                System.out.println("retry 3 times,can be promiss");
                return "-"+s;
            }else {
                System.out.println("process deal "+count +" times failed");
                throw new MyException();
            }
        }else {
            return "-"+s;
        }
    }
}
