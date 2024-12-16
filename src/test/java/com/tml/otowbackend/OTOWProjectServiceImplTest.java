package com.tml.otowbackend;

import com.tml.otowbackend.service.OTOWProjectService;
import com.tml.otowbackend.util.UserThread;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@WebAppConfiguration
@Slf4j
public class OTOWProjectServiceImplTest {

    @Resource
    OTOWProjectService otowProjectService;

    @Test
    public void testInitializeProject() {
        UserThread.set("uid", "5b0bb073-f3e6-4fe2-b58f-aeda00af5e90");
        // 测试初始化项目
        Long projectId = otowProjectService.initializeProject("Test Project", "This is a test project");
        System.out.println(projectId);
    }
}