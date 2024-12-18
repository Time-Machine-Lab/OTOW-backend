package com.tml.otowbackend;

import com.tml.otowbackend.engine.tree.service.IVirtualFileService;
import com.tml.otowbackend.engine.tree.template.SpringBootTreeTemplate;
import com.tml.otowbackend.engine.tree.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@WebAppConfiguration
@Slf4j
public class TreeTest {

    @Resource
    private IVirtualFileService virtualFileService;

    @Test
    public void testSpringBootTemplate() {
        String treeId = virtualFileService.initializeVirtualTree(PathUtils.getSpringbootPath(), null);
        SpringBootTreeTemplate template = new SpringBootTreeTemplate(virtualFileService, treeId);
        template.initializeTemplate();

        template.controller("TestController.java", list1);
        template.serviceImpl("TestServiceImpl.java", list2);
        template.master("pom.xml", list3);
        template.example("TestApplication.java", list1);
        virtualFileService.exportVirtualTree(treeId, "D:\\test");
    }

    List<String> list1 = List.of(
            "@RequestMapping(\"/test\")",
            "@RestController",
            "public class TestController {",
            "    private UserMapper userMapper;",
            "}"
    );

    List<String> list2 = List.of(
            "public class IUserService implements UserService {",
            "    @Resource",
            "    private UserMapper userMapper;",
            "    private UserMapper userMapper;",
            "}"
    );

    List<String> list3 = List.of(
            "@RequestMapping(\"/test\")",
            "@RestController",
            "public class TestController {",
            "    private UserMapper userMapper;",
            "}"
    );
}
