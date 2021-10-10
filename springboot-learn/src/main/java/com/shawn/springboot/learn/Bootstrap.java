package com.shawn.springboot.learn;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.shawn.springboot.learn.consumer.CanalListener;
import com.shawn.springboot.learn.consumer.GalaxyCanalListener;
import com.shawn.springboot.learn.consumer.MyCacheManager;
import com.shawn.springboot.learn.util.M2Utils;
import com.shawn.springboot.my.starter.HelloFormatTemplate;
import com.yonghui.common.message.bridge.model.Message2;
import com.yonghui.common.redis.YhRedisConfiguration;
import com.yonghui.mid.sdk.rocketmq.RocketMQOriginalContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
@Import({YhRedisConfiguration.class})
@SpringBootApplication
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private HelloFormatTemplate helloFormatTemplate;
    private static String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";
    @Autowired
    private MyCacheManager myCacheManager;

    public static void main(String[] args) {

        try {
            String topic = "activity-db-sync-topic";
//            topic = "canal-default";
//            topic = "yx-lottery-prize-message";
            String consumergroup = "local-test";
            String addr = "rocketmq-node1-t1.yh.test:9876;rocketmq-node2-t1.yh.test:9876;rocketmq-node1-t3.yh.test:9876";


            topic = "obd-outstock-report";
            addr = "rocketmq-ns-dev.mid.io:9876";
            topic = "obd-outstock-report";
            addr = "rocketmq-ns-sit.mid.io:9876";
            addr = "10.0.0.2:9876";
//-----{message={"dbName":"wms_mst_center_1","destination":"wms-mst-warehouse-sku","eventType":"UPDATE","relaData":{},"rowdataLst":[{"afterColumns":[{"mysqlType":"varchar(200)","name":"company_name","updated":true,"value":"会员店1"},{"mysqlType":"datetime(6)","name":"last_updated_at","updated":true,"value":"2021-09-23 16:55:44.736868"}],"beforeColumns":[{"mysqlType":"bigint(20)","name":"id","updated":false,"value":"1234232"}]}],"tableName":"mst_warehouse_sku"}}
//            -----{message={"dbName":"wms_mst_center_1","destination":"wms-mst-center-mst-warehouse-sku","eventType":"UPDATE","relaData":{},"rowdataLst":[{"afterColumns":[{"mysqlType":"bigint(20)","name":"id","updated":false,"value":"1234232"},{"mysqlType":"bigint(20)","name":"company_id","updated":false,"value":"155255889186529280"},{"mysqlType":"varchar(45)","name":"company_code","updated":false,"value":"100"},{"mysqlType":"varchar(200)","name":"company_name","updated":true,"value":"会员店1"},{"mysqlType":"bigint(20)","name":"warehouse_id","updated":false,"value":"328623759646851072"},{"mysqlType":"varchar(45)","name":"warehouse_code","updated":false,"value":"WH021"},{"mysqlType":"varchar(200)","name":"warehouse_name","updated":false,"value":"上海昆山物流中心"},{"mysqlType":"varchar(100)","name":"workshop_code","updated":false,"value":"0"},{"mysqlType":"varchar(100)","name":"workshop_name","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"workshop_type","updated":false,"value":"0"},{"mysqlType":"varchar(100)","name":"sku_code","updated":false,"value":"12211"},{"mysqlType":"varchar(100)","name":"sku_name","updated":false,"value":"花生"},{"mysqlType":"tinyint(4)","name":"operate_mode","updated":false,"value":"1"},{"mysqlType":"tinyint(4)","name":"inventory_source","updated":false,"value":""},{"mysqlType":"varchar(150)","name":"receipt_location_code","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"receipt_location_id","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"is_managed","updated":false,"value":"1"},{"mysqlType":"varchar(200)","name":"vendor_code","updated":false,"value":"200449"},{"mysqlType":"varchar(200)","name":"vendor_name","updated":false,"value":""},{"mysqlType":"varchar(100)","name":"department_category_code","updated":false,"value":"11"},{"mysqlType":"varchar(200)","name":"department_category_name","updated":false,"value":"生鲜部"},{"mysqlType":"varchar(100)","name":"big_category_code","updated":false,"value":"1101"},{"mysqlType":"varchar(200)","name":"big_category_name","updated":false,"value":"干货"},{"mysqlType":"varchar(100)","name":"middle_category_code","updated":false,"value":"110118"},{"mysqlType":"varchar(200)","name":"middle_category_name","updated":false,"value":"杂粮"},{"mysqlType":"varchar(100)","name":"small_category_code","updated":false,"value":"11011804"},{"mysqlType":"varchar(200)","name":"small_category_name","updated":false,"value":"豆类"},{"mysqlType":"bigint(20)","name":"allocation_id","updated":false,"value":"328635249519026176"},{"mysqlType":"bigint(20)","name":"putaway_id","updated":false,"value":"328635249703575552"},{"mysqlType":"decimal(18,4)","name":"replenish_point","updated":false,"value":""},{"mysqlType":"decimal(18,4)","name":"replenish_qty","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"putaway_loction_id","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"zone_id","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"sku_pickship_grade_id","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"force_flag","updated":false,"value":"0"},{"mysqlType":"decimal(18,4)","name":"unstandard_rate","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"alloc_location_id","updated":false,"value":""},{"mysqlType":"varchar(150)","name":"alloc_location_code","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"pre_alloc_location_id","updated":false,"value":"328634700375588864"},{"mysqlType":"varchar(150)","name":"pre_alloc_location_code","updated":false,"value":"1"},{"mysqlType":"decimal(18,4)","name":"inventory_qty","updated":false,"value":""},{"mysqlType":"datetime(6)","name":"adjust_time","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"inventory_gt_zero","updated":false,"value":""},{"mysqlType":"decimal(18,4)","name":"step_qty","updated":false,"value":""},{"mysqlType":"varchar(100)","name":"purchasing_group","updated":false,"value":"H01"},{"mysqlType":"varchar(200)","name":"purchasing_group_name","updated":false,"value":"干货课"},{"mysqlType":"tinyint(4)","name":"trade_dress","updated":false,"value":"1"},{"mysqlType":"tinyint(4)","name":"shelf_group","updated":false,"value":""},{"mysqlType":"varchar(100)","name":"package_configuration","updated":false,"value":"12E2C2L"},{"mysqlType":"int(10)","name":"package_number","updated":false,"value":"64"},{"mysqlType":"decimal(18,2)","name":"package_number_decimal","updated":false,"value":""},{"mysqlType":"int(10)","name":"number_per_piece","updated":false,"value":"4"},{"mysqlType":"decimal(18,2)","name":"number_per_piece_decimal","updated":false,"value":""},{"mysqlType":"decimal(18,4)","name":"insure_price","updated":false,"value":"0.0"},{"mysqlType":"tinyint(4)","name":"is_box","updated":false,"value":"0"},{"mysqlType":"varchar(45)","name":"ABCband","updated":false,"value":""},{"mysqlType":"varchar(45)","name":"qgp_alarm_in","updated":false,"value":"270"},{"mysqlType":"varchar(45)","name":"qgp_alarm_out","updated":false,"value":"270"},{"mysqlType":"int(10)","name":"obd_collection_effective","updated":false,"value":""},{"mysqlType":"int(10)","name":"recycle_qty","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"is_collect_spec","updated":false,"value":"0"},{"mysqlType":"int(10)","name":"over_interception_rate","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"product_status","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"product_valid_flag","updated":false,"value":""},{"mysqlType":"varchar(2000)","name":"comments","updated":false,"value":"test"},{"mysqlType":"datetime(6)","name":"created_at","updated":false,"value":"2019-09-27 11:24:17.177000"},{"mysqlType":"varchar(64)","name":"created_by","updated":false,"value":"0"},{"mysqlType":"datetime(6)","name":"updated_at","updated":false,"value":"2020-12-07 10:19:48.013000"},{"mysqlType":"varchar(64)","name":"updated_by","updated":false,"value":"10001"},{"mysqlType":"tinyint(4)","name":"is_del","updated":false,"value":"0"},{"mysqlType":"datetime(6)","name":"last_updated_at","updated":true,"value":"2021-09-23 17:23:30.275651"}],"beforeColumns":[{"mysqlType":"bigint(20)","name":"id","updated":false,"value":"1234232"},{"mysqlType":"bigint(20)","name":"company_id","updated":false,"value":"155255889186529280"},{"mysqlType":"varchar(45)","name":"company_code","updated":false,"value":"100"},{"mysqlType":"varchar(200)","name":"company_name","updated":false,"value":"会员店"},{"mysqlType":"bigint(20)","name":"warehouse_id","updated":false,"value":"328623759646851072"},{"mysqlType":"varchar(45)","name":"warehouse_code","updated":false,"value":"WH021"},{"mysqlType":"varchar(200)","name":"warehouse_name","updated":false,"value":"上海昆山物流中心"},{"mysqlType":"varchar(100)","name":"workshop_code","updated":false,"value":"0"},{"mysqlType":"varchar(100)","name":"workshop_name","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"workshop_type","updated":false,"value":"0"},{"mysqlType":"varchar(100)","name":"sku_code","updated":false,"value":"12211"},{"mysqlType":"varchar(100)","name":"sku_name","updated":false,"value":"花生"},{"mysqlType":"tinyint(4)","name":"operate_mode","updated":false,"value":"1"},{"mysqlType":"tinyint(4)","name":"inventory_source","updated":false,"value":""},{"mysqlType":"varchar(150)","name":"receipt_location_code","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"receipt_location_id","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"is_managed","updated":false,"value":"1"},{"mysqlType":"varchar(200)","name":"vendor_code","updated":false,"value":"200449"},{"mysqlType":"varchar(200)","name":"vendor_name","updated":false,"value":""},{"mysqlType":"varchar(100)","name":"department_category_code","updated":false,"value":"11"},{"mysqlType":"varchar(200)","name":"department_category_name","updated":false,"value":"生鲜部"},{"mysqlType":"varchar(100)","name":"big_category_code","updated":false,"value":"1101"},{"mysqlType":"varchar(200)","name":"big_category_name","updated":false,"value":"干货"},{"mysqlType":"varchar(100)","name":"middle_category_code","updated":false,"value":"110118"},{"mysqlType":"varchar(200)","name":"middle_category_name","updated":false,"value":"杂粮"},{"mysqlType":"varchar(100)","name":"small_category_code","updated":false,"value":"11011804"},{"mysqlType":"varchar(200)","name":"small_category_name","updated":false,"value":"豆类"},{"mysqlType":"bigint(20)","name":"allocation_id","updated":false,"value":"328635249519026176"},{"mysqlType":"bigint(20)","name":"putaway_id","updated":false,"value":"328635249703575552"},{"mysqlType":"decimal(18,4)","name":"replenish_point","updated":false,"value":""},{"mysqlType":"decimal(18,4)","name":"replenish_qty","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"putaway_loction_id","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"zone_id","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"sku_pickship_grade_id","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"force_flag","updated":false,"value":"0"},{"mysqlType":"decimal(18,4)","name":"unstandard_rate","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"alloc_location_id","updated":false,"value":""},{"mysqlType":"varchar(150)","name":"alloc_location_code","updated":false,"value":""},{"mysqlType":"bigint(20)","name":"pre_alloc_location_id","updated":false,"value":"328634700375588864"},{"mysqlType":"varchar(150)","name":"pre_alloc_location_code","updated":false,"value":"1"},{"mysqlType":"decimal(18,4)","name":"inventory_qty","updated":false,"value":""},{"mysqlType":"datetime(6)","name":"adjust_time","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"inventory_gt_zero","updated":false,"value":""},{"mysqlType":"decimal(18,4)","name":"step_qty","updated":false,"value":""},{"mysqlType":"varchar(100)","name":"purchasing_group","updated":false,"value":"H01"},{"mysqlType":"varchar(200)","name":"purchasing_group_name","updated":false,"value":"干货课"},{"mysqlType":"tinyint(4)","name":"trade_dress","updated":false,"value":"1"},{"mysqlType":"tinyint(4)","name":"shelf_group","updated":false,"value":""},{"mysqlType":"varchar(100)","name":"package_configuration","updated":false,"value":"12E2C2L"},{"mysqlType":"int(10)","name":"package_number","updated":false,"value":"64"},{"mysqlType":"decimal(18,2)","name":"package_number_decimal","updated":false,"value":""},{"mysqlType":"int(10)","name":"number_per_piece","updated":false,"value":"4"},{"mysqlType":"decimal(18,2)","name":"number_per_piece_decimal","updated":false,"value":""},{"mysqlType":"decimal(18,4)","name":"insure_price","updated":false,"value":"0.0"},{"mysqlType":"tinyint(4)","name":"is_box","updated":false,"value":"0"},{"mysqlType":"varchar(45)","name":"ABCband","updated":false,"value":""},{"mysqlType":"varchar(45)","name":"qgp_alarm_in","updated":false,"value":"270"},{"mysqlType":"varchar(45)","name":"qgp_alarm_out","updated":false,"value":"270"},{"mysqlType":"int(10)","name":"obd_collection_effective","updated":false,"value":""},{"mysqlType":"int(10)","name":"recycle_qty","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"is_collect_spec","updated":false,"value":"0"},{"mysqlType":"int(10)","name":"over_interception_rate","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"product_status","updated":false,"value":""},{"mysqlType":"tinyint(4)","name":"product_valid_flag","updated":false,"value":""},{"mysqlType":"varchar(2000)","name":"comments","updated":false,"value":"test"},{"mysqlType":"datetime(6)","name":"created_at","updated":false,"value":"2019-09-27 11:24:17.177000"},{"mysqlType":"varchar(64)","name":"created_by","updated":false,"value":"0"},{"mysqlType":"datetime(6)","name":"updated_at","updated":false,"value":"2020-12-07 10:19:48.013000"},{"mysqlType":"varchar(64)","name":"updated_by","updated":false,"value":"10001"},{"mysqlType":"tinyint(4)","name":"is_del","updated":false,"value":"0"},{"mysqlType":"datetime(6)","name":"last_updated_at","updated":false,"value":"2021-05-19 13:45:52.062183"}]}],"tableName":"mst_warehouse_sku"}}

            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumergroup);
            defaultMQPushConsumer.setNamesrvAddr(addr);
            String filtertag = "*";
            defaultMQPushConsumer.subscribe("canal-default", filtertag);
//            defaultMQPushConsumer.subscribe("wms-obd-order-sync", filtertag);
//            defaultMQPushConsumer.subscribe("obd-outstock-report", filtertag);
//            defaultMQPushConsumer.subscribe("wms-task-sync", filtertag);
            defaultMQPushConsumer.subscribe("product-search-word-change", filtertag);

            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    try{

                        MessageExt ext = list.get(0);
                        String content = new String(ext.getBody());
//                        Message2 m2 = M2Utils.unserialize(ext.getBody());
                        System.out.println(ext.getTopic() + "-----" + content);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
//                    System.out.println(content);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            defaultMQPushConsumer.start();
            System.out.println("success");
            while (true) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        final RocketMQOriginalContext context = RocketMQOriginalContext.getInstance();
//        // 注册Consumer
//        context.registerConsumer(CanalListener.class);
//        context.registerConsumer(GalaxyCanalListener.class);
//        // 启动
//        context.start();

//        SpringApplication.run(Bootstrap.class, args);
//        System.out.println("Hello World!");
//        try {
//            ClassLoader loggerFactoryClassLoader = LoggerFactory.class.getClassLoader();
//            Enumeration<URL> paths;
//            System.out.println(loggerFactoryClassLoader);
//            if (loggerFactoryClassLoader == null) {
//                paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
//            } else {
//                paths = loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH);
//            }
//            while(paths.hasMoreElements()){
//                System.out.println(paths.nextElement());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run(String... args) throws Exception {
        myCacheManager.updateNotModifyExpireTime("abcd-test-abcd", "nw");

        String formatProperties = helloFormatTemplate.doFormat();
        System.out.println(formatProperties);
//        myCacheManager.update("abcd-test-abcd","a",2, TimeUnit.HOURS);
//        String s = myCacheManager.get("abcd-test-abcd");
//        System.out.println("a");

    }
}
