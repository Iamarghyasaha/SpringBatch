package com.ndb.springBatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class FirstItemWriter implements ItemWriter<Long> {
    @Override
    // whatever chunk size we will provide the same size of List it will take as parameter
    // size of the list == size of the every chunk
    public void write(List<? extends Long> list) throws Exception {
        System.out.println("Inside Item Writer");
        for(long l : list){
            System.out.println(l);
        }

    }
}
