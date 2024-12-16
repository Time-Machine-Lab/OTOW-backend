package com.tml.otowbackend;

import com.tml.otowbackend.engine.ai.core.AIOperation;
import com.tml.otowbackend.engine.ai.core.AIOperationFactory;
import com.tml.otowbackend.engine.ai.core.ParseResult;
import com.tml.otowbackend.engine.ai.model.QwModel;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.ai.result.FeaturePackage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tml.otowbackend.constants.AIConstant.GENERATE_DESC;
import static com.tml.otowbackend.constants.AIConstant.GENERATE_ENTITY;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtowBackendApplication.class)
@WebAppConfiguration
@Slf4j
public class AIGenerateTest {

    @Resource
    private QwModel qwModel;

    @Test
    public void generate() {
        AIOperation<List<EntityClassDefinition>> operation = (AIOperation<List<EntityClassDefinition>>) AIOperationFactory.getOperation(GENERATE_ENTITY);

        Map<String, Object> projectOutline = new HashMap<>();

        projectOutline.put("title", "社区物业管理系统");

        projectOutline.put("description", "1.应用场景：社区物业管理系统用于提升社区物业管理和服务效率。 2.系统客户：物业管理员、业主、维修人员、保安人员、清洁人员。 " +
                "3.主要业务： （1）物业管理员：注册和登录、管理业主信息、发布社区公告、处理业主投诉和建议、安排和监督维修工作、生成物业费账单、查看和管理物业费缴纳情况、" +
                "统计和分析物业管理数据、组织社区活动、管理社区公共设施、安排日常维护工作； （2）业主：注册和登录、查看和更新个人信息、查看社区公告、在线报修、提交投诉和建议、" +
                "查看物业费账单、在线缴纳物业费、参与社区活动、查看报修处理进度、申请和预约公共设施使用； （3）维修人员：注册和登录、查看和接收维修任务、记录维修进度和结果、" +
                "提交维修报告、反馈维修过程中遇到的问题、查看维修统计数据、管理维修工具和材料； （4）保安人员：注册和登录、查看值班安排、记录值班情况、处理突发事件、巡逻记录、" +
                "查看监控视频、反馈安全隐患； （5）清洁人员：注册和登录、查看工作安排、记录清洁工作情况、反馈清洁过程中遇到的问题、申请清洁用品； （6）创新功能：基于报修数据和" +
                "历史记录，自动分配维修任务给最合适的维修人员，提高维修效率和服务质量。 4. 系统要求：（1）采用B/S模式；（2）不同用户登录系统后能完成相应职责；（3）统计分析" +
                "数据以图表形式呈现，例如直方图、饼状图等；（4）系统界面友好、操作便捷、具有较好的健壮性；（5）需要在1000条以上记录条件下进行测试；（6）引入智能算法进行不良反" +
                "应数据的聚类分析和趋势预测，提高数据分析的精确度和预测能力。");

        projectOutline.put("complexity", "很高");

        // 构造功能包列表
        List<FeaturePackage> featurePackages = List.of(
                new FeaturePackage("1001", "头像上传"),
                new FeaturePackage("1002", "文件上传"),
                new FeaturePackage("1003", "文件下载"),
                new FeaturePackage("1004", "在线支付"),
                new FeaturePackage("1005", "消息推送"),
                new FeaturePackage("1006", "权限管理"),
                new FeaturePackage("1007", "日志记录"),
                new FeaturePackage("1008", "数据导出"),
                new FeaturePackage("1009", "数据备份"),
                new FeaturePackage("1010", "用户注册"),
                new FeaturePackage("1011", "用户登录"),
                new FeaturePackage("1012", "密码重置"),
                new FeaturePackage("1013", "评论管理"),
                new FeaturePackage("1014", "统计分析"),
                new FeaturePackage("1015", "数据可视化"),
                new FeaturePackage("1016", "在线编辑器"),
                new FeaturePackage("1017", "二维码生成"),
                new FeaturePackage("1018", "日程管理"),
                new FeaturePackage("1019", "实时通知"),
                new FeaturePackage("1020", "简单推荐系统")
        );
        projectOutline.put("featurePackages", featurePackages);

        // 生成AI提示词
        String prompt = operation.generatePrompt(projectOutline);

        // 调用AI模型
        String generate = qwModel.generate(prompt);

        // 解析内容
        ParseResult<List<EntityClassDefinition>> listParseResult = operation.parseResponse(generate);

        List<EntityClassDefinition> data = listParseResult.getData();
        for (EntityClassDefinition datum : data) {
            System.out.println(datum);
        }
    }

    @Test
    public void generateDesc() {
        AIOperation<String> operation = (AIOperation<String>) AIOperationFactory.getOperation(GENERATE_DESC);
        Map<String, Object> projectOutline = new HashMap<>();

        projectOutline.put("title", "社区物业管理系统");
        projectOutline.put("complexity", "很高");

        // 生成AI提示词
        String prompt = operation.generatePrompt(projectOutline);

        // 调用AI模型
        String generate = qwModel.generate(prompt);

        ParseResult<String> listParseResult = operation.parseResponse(generate);

        System.out.println(listParseResult.getData());
    }
}
