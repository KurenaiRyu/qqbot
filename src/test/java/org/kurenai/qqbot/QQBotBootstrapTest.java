package org.kurenai.qqbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kurenai.qqbot.annotation.GroupEvent;
import org.kurenai.qqbot.core.BotContext;
import org.kurenai.qqbot.pojo.Event;
import org.kurenai.qqbot.pojo.result.GroupInfo;
import org.kurenai.qqbot.util.JacksonFactory;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@Slf4j
class QQBotBootstrapTest {

    @Test
    void logTest() {
        log.info("info");
        log.error("error");
        log.warn("warn");
        log.debug("debug");
        log.trace("trace");
        System.out.println("test");
    }

    @Test
    void jacksonTest() throws JsonProcessingException {
        String msg = "{\"data\":[{\"group_create_time\":0,\"group_id\":4732133,\"group_level\":0,\"group_memo\":\"团结、奋进、朝气蓬勃，创中国服装鞋业行业软件第一品牌，需要你的努力！\\r\\n系统登录地址链 http://192.168.8.4:8888/\",\"group_name\":\"思创理德\",\"max_member_count\":500,\"member_count\":47},{\"group_create_time\":0,\"group_id\":43735309,\"group_level\":0,\"group_memo\":\"群规：\\n禁止,外挂,黄图,刷屏钻图,欺骗\\n广告,收费,病毒,骂人,借号\\n刷屏、等等、管理员带头！\\n\",\"group_name\":\"⒐●后╃→专属 \",\"max_member_count\":500,\"member_count\":52},{\"group_create_time\":0,\"group_id\":83663746,\"group_level\":0,\"group_memo\":\"禁止,外挂,黄图,刷屏钻图,欺骗\\n广告,收费,病毒,骂人,借号\\n马甲格式:↙藝園-XX\\n招收管理员联系:dg(381885231\",\"group_name\":\"↙藝園.九年级.自由群\",\"max_member_count\":500,\"member_count\":62},{\"group_create_time\":0,\"group_id\":89972152,\"group_level\":0,\"group_memo\":\"请自己写好备注\",\"group_name\":\"93\",\"max_member_count\":500,\"member_count\":25},{\"group_create_time\":0,\"group_id\":139571157,\"group_level\":0,\"group_memo\":\"请童鞋们自觉改上自己的名字  好让大家认识你  谢谢合作\",\"group_name\":\"℡. 闪耀 ‖高① ⑨班\",\"max_member_count\":500,\"member_count\":28},{\"group_create_time\":0,\"group_id\":156655764,\"group_level\":0,\"group_memo\":\"该干嘛的就该干嘛，好好干，努力干，走出自己的星光大道。。\",\"group_name\":\"连平中学2014届高三11班\",\"max_member_count\":500,\"member_count\":61},{\"group_create_time\":0,\"group_id\":170896323,\"group_level\":0,\"group_memo\":\"修改备注，姓名+年级专业班级\\r\\n如，\\n张飞～16软件3\",\"group_name\":\"Technical Workshop\",\"max_member_count\":200,\"member_count\":38},{\"group_create_time\":0,\"group_id\":177906874,\"group_level\":0,\"group_memo\":\"发布的是国际服版本，国服暂时先别用，我因为个人原因暂时没法修，国服用户请先耐心等待几天，非常抱歉\",\"group_name\":\"船长内测群\",\"max_member_count\":500,\"member_count\":300},{\"group_create_time\":0,\"group_id\":185916873,\"group_level\":0,\"group_memo\":\"关于上述等的范围，管理员保留解释权，实在不知道该不该的请私聊咨询管理员[坏笑]\",\"group_name\":\"ENode\",\"max_member_count\":2000,\"member_count\":944},{\"group_create_time\":0,\"group_id\":203248469,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"高一八\",\"max_member_count\":500,\"member_count\":46},{\"group_create_time\":0,\"group_id\":213690137,\"group_level\":0,\"group_memo\":\"https://docs.qq.com/sheet/DRkxQYnRGZmZGVGdp\",\"group_name\":\"Anime吹Water挖Chia聊天Group\",\"max_member_count\":500,\"member_count\":184},{\"group_create_time\":0,\"group_id\":224740764,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"软件学习交流讨论群\",\"max_member_count\":200,\"member_count\":20},{\"group_create_time\":0,\"group_id\":237369065,\"group_level\":0,\"group_memo\":\"\\u0001http://192.168.8.251:7898\\u0002\\n密码：admin\",\"group_name\":\"软件研发部\",\"max_member_count\":200,\"member_count\":22},{\"group_create_time\":0,\"group_id\":284466675,\"group_level\":0,\"group_memo\":\"看连载的都是敌人\",\"group_name\":\"纸片儿不要钱补档群\",\"max_member_count\":200,\"member_count\":37},{\"group_create_time\":0,\"group_id\":295470827,\"group_level\":0,\"group_memo\":\"通知\\n 请大家带上身份证复印件，户口簿复印件，毕业证复印件，在8月20--30号（周内）到学校政教处领取档案！\",\"group_name\":\"高一（10）班\",\"max_member_count\":500,\"member_count\":59},{\"group_create_time\":0,\"group_id\":345038240,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"俊宏老师读书会\",\"max_member_count\":500,\"member_count\":334},{\"group_create_time\":0,\"group_id\":462761387,\"group_level\":0,\"group_memo\":\"代码在群共享\\nhackmd\\nhttps://hackmd.io/EwVgDAnAhmCMwFpgFNkgQFgOwDZECMATAMwgVgymFEMoA5Cog===\",\"group_name\":\"sv逆向分析\",\"max_member_count\":200,\"member_count\":26},{\"group_create_time\":0,\"group_id\":472558462,\"group_level\":0,\"group_memo\":\"学生寒假放假及开学注册、上课时间：\\r放假时间：2017年1月14日-2月17日\\r\\n注册时间：2月18��-19日，2月20日上课\",\"group_name\":\"2015级软件新生群\",\"max_member_count\":500,\"member_count\":207},{\"group_create_time\":0,\"group_id\":475373428,\"group_level\":0,\"group_memo\":\"马甲格式：年级-兴趣-名字\\n如：12级-PHP-小夜\\n这是一个提供网站开发，微信开发的一个交流群。\",\"group_name\":\"广州城建IT技术交流群\",\"max_member_count\":200,\"member_count\":39},{\"group_create_time\":0,\"group_id\":476377831,\"group_level\":0,\"group_memo\":\"本周四（11月12日）晚上7点40分进行放映会，地点于一教505，电影《萤火之森》\",\"group_name\":\"15届UNIONE动漫社会员群\",\"max_member_count\":200,\"member_count\":137},{\"group_create_time\":0,\"group_id\":484689728,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"城建学生开发团队\",\"max_member_count\":500,\"member_count\":26},{\"group_create_time\":0,\"group_id\":489510887,\"group_level\":0,\"group_memo\":\"学生寒假放假及开学注册、上课时间：\\r放假时间：2017年1月14日-2月17日\\r\\n注册时间：2月18日-19日，2月20日上课\",\"group_name\":\"15软件技术5班\",\"max_member_count\":500,\"member_count\":57},{\"group_create_time\":0,\"group_id\":490146519,\"group_level\":0,\"group_memo\":\"通知：今天下午2：30在多媒体2419，5、6节课的时间召开班会！请大家准时到。\",\"group_name\":\"15软件1班\",\"max_member_count\":200,\"member_count\":69},{\"group_create_time\":0,\"group_id\":495723504,\"group_level\":0,\"group_memo\":\"院徽与院训设计比赛有关资料我已经传上群里了，大家看看吧！\",\"group_name\":\"软件一班通知群\",\"max_member_count\":200,\"member_count\":47},{\"group_create_time\":0,\"group_id\":512767557,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"聚会通知群\",\"max_member_count\":200,\"member_count\":11},{\"group_create_time\":0,\"group_id\":552746290,\"group_level\":0,\"group_memo\":\"在群文件里下载下来可以自学。。\",\"group_name\":\"Web协会大家庭\",\"max_member_count\":500,\"member_count\":55},{\"group_create_time\":0,\"group_id\":586608623,\"group_level\":0,\"group_memo\":\"馒头、柠檬辅种如果出现失败或者红种的，可以做如下处理：\\r\\n第一步，升级到最新版本；\\r\\n第二步，编辑站点设\",\"group_name\":\"IYUU矿老板群⑤\",\"max_member_count\":1000,\"member_count\":357},{\"group_create_time\":0,\"group_id\":590962780,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"office技能大赛复赛\",\"max_member_count\":500,\"member_count\":40},{\"group_create_time\":0,\"group_id\":594082554,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"午夜摸鱼黑车站\",\"max_member_count\":200,\"member_count\":4},{\"group_create_time\":0,\"group_id\":597967099,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"学生开发团队\",\"max_member_count\":200,\"member_count\":10},{\"group_create_time\":0,\"group_id\":622032041,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"BotTest\",\"max_member_count\":200,\"member_count\":2},{\"group_create_time\":0,\"group_id\":623124967,\"group_level\":0,\"group_memo\":\"今年有人获得了软考证书的吗  有的话在明天上午10点前（12月14日） 在我这里报备。\",\"group_name\":\"【南区】-实施团队\",\"max_member_count\":200,\"member_count\":35},{\"group_create_time\":0,\"group_id\":627756926,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"城建报表学习讨论群\",\"max_member_count\":500,\"member_count\":4},{\"group_create_time\":0,\"group_id\":628600300,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"希望の花赛马娘\",\"max_member_count\":200,\"member_count\":12},{\"group_create_time\":0,\"group_id\":633600842,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"java项目开发讨论组\",\"max_member_count\":200,\"member_count\":9},{\"group_create_time\":0,\"group_id\":650386287,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"罗德岛动物保护协会\",\"max_member_count\":200,\"member_count\":200},{\"group_create_time\":0,\"group_id\":671016748,\"group_level\":0,\"group_memo\":\"恭贺新春！\",\"group_name\":\"船长的SV交流群-4群\",\"max_member_count\":500,\"member_count\":447},{\"group_create_time\":0,\"group_id\":677025336,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"15毕业设计\",\"max_member_count\":200,\"member_count\":31},{\"group_create_time\":0,\"group_id\":677073920,\"group_level\":0,\"group_memo\":\"蜜汁考古---6年前的飞利浦BDM4065UC 4K显示器\\n\\u0001https://post.smzdm.com/p/a3d28l9r/\\u0002\",\"group_name\":\"Private Tracker\",\"max_member_count\":2000,\"member_count\":181},{\"group_create_time\":0,\"group_id\":721829413,\"group_level\":0,\"group_memo\":\"请各位bot主人将自己的bot的昵称带上主人的名字，否则将被清理\\n格式：BOT-{bot昵称}-{主人昵称}\",\"group_name\":\" 人工智障交流群\",\"max_member_count\":1000,\"member_count\":608},{\"group_create_time\":0,\"group_id\":737974222,\"group_level\":0,\"group_memo\":\"通知:因疫情圣诞老人今年将不能进入我国,但我已经和圣诞老人达成战略合作伙伴, 各位女生可以把袜子寄给我,届\",\"group_name\":\"小小甜心\",\"max_member_count\":200,\"member_count\":36},{\"group_create_time\":0,\"group_id\":785717862,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"生放送通知\",\"max_member_count\":2000,\"member_count\":705},{\"group_create_time\":0,\"group_id\":858678492,\"group_level\":0,\"group_memo\":\"Q＆A脑溢血问题大合集\\r\\nQ1：我在主界面修改成功了，为什么进战斗没用啊？\\r\\nA1：你他娘的能不能进战斗再修改\\r\\nQ2\",\"group_name\":\"碧蓝档案脚本分享2群\",\"max_member_count\":500,\"member_count\":207},{\"group_create_time\":0,\"group_id\":859882209,\"group_level\":0,\"group_memo\":\"馒头、柠檬辅种如果出现失败或者红种的，可以做如下处理：\\n第一步，升级到最新版本；\\n第二步，编辑站点设置\",\"group_name\":\"IYUU自动辅种交流①\",\"max_member_count\":2000,\"member_count\":1882},{\"group_create_time\":0,\"group_id\":879793109,\"group_level\":0,\"group_memo\":\"这期视频的5600X / 5800X 会以散片的形式，分为体制一般和体制较好两个规格进行出售，有想法的可以私聊geiwumi / 毕\",\"group_name\":\"建设社会主义装机平台\",\"max_member_count\":2000,\"member_count\":920},{\"group_create_time\":0,\"group_id\":1035742830,\"group_level\":0,\"group_memo\":\"下期跑路和没空出刀的跟我说，或者自己退出刀群吧，管理们都燃尽了，四月坚决不代刀，需要睡饱饱\",\"group_name\":\"三十只真布\",\"max_member_count\":200,\"member_count\":62},{\"group_create_time\":0,\"group_id\":1108156669,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"三十只mcw会战作业群\",\"max_member_count\":200,\"member_count\":32}],\"echo\":\"group\",\"retcode\":0,\"status\":\"ok\"}";
        ObjectMapper mapper = JacksonFactory.getInstance();
        GroupInfo event = mapper.readValue(msg, GroupInfo.class);
    }

    @Test
    void testAnno() {
        HashMap<String, Object> map = new HashMap<>();

        Reflections reflections = new Reflections("org.kurenai.qqbot",
                                                  new MethodAnnotationsScanner(),
                                                  new TypeAnnotationsScanner(),
                                                  new SubTypesScanner());

        for (Method method : reflections.getMethodsAnnotatedWith(GroupEvent.class)) {
            System.out.println(method.getName());
            String clazzName = method.getDeclaringClass().getName();
            Object instance = map.get(clazzName);
            try {
                if (instance == null) {
                    instance = method.getDeclaringClass().getConstructor().newInstance();
                    map.put(clazzName, instance);
                }
                method.invoke(instance, new BotContext(), new Event());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                log.error("调用方法失败", e);
            }
        }
    }
}