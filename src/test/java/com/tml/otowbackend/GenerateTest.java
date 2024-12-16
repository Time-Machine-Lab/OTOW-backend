package com.tml.otowbackend;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@WebAppConfiguration
@Slf4j
public class GenerateTest {

/*    @Test
    public void generateEntity(){
        String packageName = "io.github.geniusay.velocity.generate";
        String className = "User";
        String tableName = "user";
        List< String > fields = new ArrayList<>();
        fields.add("id");
        fields.add("uid");
        fields.add("avatar");
        fields.add("createTime");
        fields.add("updateTime");
        String generated = InitTemplate.generateEntityTemplate(packageName,className,tableName,fields);
        System.out.println(generated);
    }*/
}
