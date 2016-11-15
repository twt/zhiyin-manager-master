import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.module.anchor.entity.AnchorRoleInfo;
import com.zhiyin.dbs.module.anchor.service.IAnchorRoleInfoService;
import com.zhiyin.manager.module.anchor.controller.AnchorRoleInfoController;
import com.zhiyin.manager.module.anchor.module.AnchorRoleInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hg on 2016/4/29.
 */
@Slf4j
public class Test {
    public static void main(String[] args){

        AbstractXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo.xml");

        IAnchorRoleInfoService anchorRoleInfoService = context.getBean("anchorRoleInfoService", IAnchorRoleInfoService.class);

        AnchorRoleInfo ret = anchorRoleInfoService.selectById(725709148414545920L);


        System.out.println(JSON.toJSONString(ret));

        AnchorRoleInfoVo s2c = AnchorRoleInfoController.conv(ret);
        log.info(JSON.toJSONString(s2c));
        }
}
