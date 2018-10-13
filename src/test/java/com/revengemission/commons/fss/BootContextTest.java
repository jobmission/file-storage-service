package com.revengemission.commons.fss;

import com.revengemission.commons.fss.persistence.entity.FileRecordEntityExample;
import com.revengemission.commons.fss.persistence.mapper.FileRecordEntityMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootContextTest {

    @Autowired
    FileRecordEntityMapper fileRecordEntityMapper;


    @Test
    @Ignore
    public void testCount() throws Exception {
        System.out.println("total=" + fileRecordEntityMapper.countByExample(new FileRecordEntityExample()));

    }

}
