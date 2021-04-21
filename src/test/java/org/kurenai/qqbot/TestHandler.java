package org.kurenai.qqbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.kurenai.qqbot.util.JacksonFactory;

/**
 * @author Kurenai
 * @since 2021-04-16 19:26
 */

public class TestHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper mapper = JacksonFactory.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String responseStr = "{\"data\":[{\"group_create_time\":0,\"group_id\":4732133,\"group_level\":0,\"group_memo\":\"团结、奋进、朝气蓬勃，创中国服装鞋业行业软件第一品牌，需要你的努力！\\r\\n系统登录地址链 http://192.168.8.4:8888/\",\"group_name\":\"思创理德\",\"max_member_count\":500,\"member_count\":47},{\"group_create_time\":0,\"group_id\":43735309,\"group_level\":0,\"group_memo\":\"群规：\\n禁止,外挂,黄图,刷屏钻图,欺骗\\n广告,收费,病毒,骂人,借号\\n刷屏、等等、管理员带头！\\n\",\"group_name\":\"⒐●后╃→专属 \",\"max_member_count\":500,\"member_count\":52},{\"group_create_time\":0,\"group_id\":83663746,\"group_level\":0,\"group_memo\":\"禁止,外挂,黄图,刷屏钻图,欺骗\\n广告,收费,病毒,骂人,借号\\n马甲格式:↙藝園-XX\\n招收管理员联系:dg(381885231\",\"group_name\":\"↙藝園.九年级.自由群\",\"max_member_count\":500,\"member_count\":62},{\"group_create_time\":0,\"group_id\":89972152,\"group_level\":0,\"group_memo\":\"请自己写好备注\",\"group_name\":\"93\",\"max_member_count\":500,\"member_count\":25},{\"group_create_time\":0,\"group_id\":139571157,\"group_level\":0,\"group_memo\":\"请童鞋们自觉改上自己的名字  好让大家认识你  谢谢合作\",\"group_name\":\"℡. 闪耀 ‖高① ⑨班\",\"max_member_count\":500,\"member_count\":28},{\"group_create_time\":0,\"group_id\":156655764,\"group_level\":0,\"group_memo\":\"该干嘛的就该干嘛，好好干，努力干，走出自己的星光大道。。\",\"group_name\":\"连平中学2014届高三11班\",\"max_member_count\":500,\"member_count\":61},{\"group_create_time\":0,\"group_id\":170896323,\"group_level\":0,\"group_memo\":\"修改备注，姓名+年级专业班级\\r\\n如，\\n张飞～16软件3\",\"group_name\":\"Technical Workshop\",\"max_member_count\":200,\"member_count\":38},{\"group_create_time\":0,\"group_id\":177906874,\"group_level\":0,\"group_memo\":\"发布的是国际服版本，国服暂时先别用，我因为个人原因暂时没法修，国服用户请先耐心等待几天，非常抱歉\",\"group_name\":\"船长内测群\",\"max_member_count\":500,\"member_count\":300},{\"group_create_time\":0,\"group_id\":185916873,\"group_level\":0,\"group_memo\":\"关于上述等的范围，管理员保留解释权，实在不知道该不该的请私聊咨询管理员[坏笑]\",\"group_name\":\"ENode\",\"max_member_count\":2000,\"member_count\":944},{\"group_create_time\":0,\"group_id\":203248469,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"高一八\",\"max_member_count\":500,\"member_count\":46},{\"group_create_time\":0,\"group_id\":213690137,\"group_level\":0,\"group_memo\":\"https://docs.qq.com/sheet/DRkxQYnRGZmZGVGdp\",\"group_name\":\"Anime吹Water挖Chia聊天Group\",\"max_member_count\":500,\"member_count\":183},{\"group_create_time\":0,\"group_id\":224740764,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"软件学习交流讨论群\",\"max_member_count\":200,\"member_count\":20},{\"group_create_time\":0,\"group_id\":237369065,\"group_level\":0,\"group_memo\":\"\\u0001http://192.168.8.251:7898\\u0002\\n密码：admin\",\"group_name\":\"软件研发部\",\"max_member_count\":200,\"member_count\":22},{\"group_create_time\":0,\"group_id\":284466675,\"group_level\":0,\"group_memo\":\"看连载的都是敌人\",\"group_name\":\"纸片儿不要钱补档群\",\"max_member_count\":200,\"member_count\":37},{\"group_create_time\":0,\"group_id\":295470827,\"group_level\":0,\"group_memo\":\"通知\\n 请大家带上身份证复印件，户口簿复印件，毕业证复印件，在8月20--30号（周内）到学校政教处领取档案！\",\"group_name\":\"高一（10）班\",\"max_member_count\":500,\"member_count\":59},{\"group_create_time\":0,\"group_id\":345038240,\"group_level\":0,\"group_memo\":\"\",\"group_name\":\"俊宏老师读书会\",\"max_member_count\":500,\"member_count\":334},{\"group_create_time\":0,\"group_id\":462761387,\"group_level\":0,\"group_memo\":\"代码在群共享\\nhackmd\\nhttps://hackmd.io/EwVgDAnAhmCMwFpgFNkgQFgOwDZECMATAMwgVgymFEMoA5Cog===\",\"group_name\":\"sv逆向分析\",\"max_member_count\":200,\"member_count\":26},{\"group_create_time\":0,\"group_id\":472558462,\"group_level\":0,\"group_memo\":\"学生寒假放假及开学注册、上课时间：\\r放假时间：2017年1月14日-2月17日\\r\\n注册时间：2月18�";
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseStr.concat(responseStr)));
    }
}
