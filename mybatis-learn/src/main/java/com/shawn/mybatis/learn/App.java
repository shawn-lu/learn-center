package com.shawn.mybatis.learn;

import com.shawn.mybatis.learn.domain.Member;
import com.shawn.mybatis.learn.mapper.MemberMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
//        SqlSessionFactory sqlSessionFactory = loadFromXml();
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
//        Long id = 100000064790139725L;
//        Member member = memberMapper.selectMember(id);
//        memberMapper.selectMember(id);
//        while (true) {
//            Thread.sleep(5000);
//            member = memberMapper.selectMember(id);
//            System.out.println(member);
//        }


//        SqlSession sqlSession2 = sqlSessionFactory.openSession();
//        MemberMapper memberMapper2 = sqlSession2.getMapper(MemberMapper.class);
//        Member member2 = memberMapper2.selectMember(id);
//        System.out.println(member2);

        new App().lruCacheTest();
    }



    private static SqlSessionFactory loadFromXml() {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }


    private void lruCacheTest() {
        int size = 4;
        Map<Object, Object> keyMap = new LinkedHashMap<Object, Object>(size, .75F, true) {
            private static final long serialVersionUID = 4267176411845948333L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    System.out.println(eldest.getKey());
                }
                return tooBig;
            }
        };
        for (int i = 1; i <= 4; i++) {
            keyMap.put("a" + i, "a" + i);
        }
        System.out.println(keyMap);
        keyMap.get("a1");
        keyMap.get("a1");
        keyMap.get("a2");
        System.out.println(keyMap);
        keyMap.put("a" + 5, "a" + 5);
        System.out.println(keyMap);
    }


}
