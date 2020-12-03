package com.shawn.demo.hbase;

import com.google.common.collect.ImmutableMap;
import com.yonghui.access.hbase.heper.HbaseAutoConfiguration;
import com.yonghui.access.hbase.heper.HbaseService;
import com.yonghui.access.hbase.heper.model.HConnectionContext;
import com.yonghui.access.hbase.heper.utils.Try;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
@Import(HbaseAutoConfiguration.class)
@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private HConnectionContext context;

    private final static String VALUE = "VALUE";
    private final static String TIMESTAMP = "TIMESTAMP";
    private final static String QUALIFIER = "QUALIFIER";
    private final static String FAMILY = "FAMILY";

    public static final Function<Result, Map<String, Object>> defaultConvertFun = result -> {//默认转换逻辑
        if (result.isEmpty()) return null;
        return ImmutableMap.of(Bytes.toString(
                result.getRow()),//行键
                result.listCells().stream().collect(Collectors.toMap(
                        x -> Bytes.toString(CellUtil.cloneQualifier(x)), //列名
                        x -> Bytes.toString(CellUtil.cloneValue(x)))));  //列值
    };


    private static final Logger log = LoggerFactory.getLogger("Bootstrap");

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        log.info("Bootstrap started successfully");
    }

    @Override
    public void run(String... args) throws Exception {
//        Map<String, Object> data = hbaseService.get("TEST_MEMBER", "1");
        List<Map<String,Object>> s = hbaseService.getValue("TEST_MEMBER", "1","f1","name",10);
        System.out.println("--------------");
        System.out.println(s);
//        pList(s);
        System.out.println("--------------");
    }

    public byte[] getValue(String tableName, String row, String family, String qualifier) {
        return Optional.ofNullable(tableName).map(Try.of(tn -> {
            try (Table table = getTable(tn)) {
                Result result = table.get(new Get(Bytes.toBytes(row)));
                Cell cell = result.getColumnLatestCell(Bytes.toBytes(family), Bytes.toBytes(qualifier));
//                cell.getTimestamp();
                return CellUtil.cloneValue(cell);
            }
        })).orElse(null);
    }

    public List<Map<String,Object>> getValue(String tableName, String row, String family, String qualifier, int version) {
        return Optional.ofNullable(tableName).map(Try.of(tn -> {
            try (Table table = getTable(tn)) {
                Result result = table.get(new Get(Bytes.toBytes(row)).setMaxVersions(version < 1 ? 1 : version));
                return Optional.ofNullable(result.getColumnCells(Bytes.toBytes(family),Bytes.toBytes(qualifier)))
                        .orElse(new ArrayList<>())
                        .stream().map(c ->
                                {
                                    Map<String, Object> m = new HashMap<>();
                                    m.put(VALUE, Bytes.toString(CellUtil.cloneValue(c)));
                                    m.put(TIMESTAMP, c.getTimestamp());
                                    m.put(QUALIFIER, Bytes.toString(CellUtil.cloneQualifier(c)));
                                    m.put(FAMILY, Bytes.toString(CellUtil.cloneFamily(c)));
                                    return m;
                                }
//                            ImmutableMap.of(VALUE,CellUtil.cloneValue(c),TIMESTAMP,c.getTimestamp())
                        ).collect(Collectors.toList());
            }
        })).orElse(null);
    }


    private Table getTable(String tableName) {
        return Optional.ofNullable(tableName).map(Try.of(x -> context.getConnection().getTable(TableName.valueOf(x)))).orElse(null);
    }

    private void pList(List<byte[]> list) {
        list.stream().forEach(b -> {
            System.out.println(new String(b));
        });
    }


}
