package com.shawn.diamond.learn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import com.taobao.diamond.client.DiamondConfigure;
import com.taobao.diamond.client.impl.DiamondClientFactory;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luxufeng
 * @date 2020/10/28
 **/
public class ConfigService {
    private static final Logger log = LoggerFactory.getLogger(ConfigService.class);
    private static final int DEFAULT_READ_TIMEOUT = 2000;
    private static final String KEY_FORMAT = "group:%s__dataId:%s";
    private static ConcurrentHashMap<String, String> configValues = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        String group = "TEST_TEST";
        String dataId = "test";
        String configValue = getConfigByGroupAndDataId(group, dataId);
        log.info("group:{},dataId:{},value:{}", group, dataId, configValue);
        log.info("done");
        while (true) {
            String newValue = getConfigByGroupAndDataId(group, dataId);
            if (null != configValue && !configValue.equals(newValue)) {
                log.info("change");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String makeKey(String group, String dataId) {
        return String.format(KEY_FORMAT, group, dataId);
    }

    private static String getConfigByGroupAndDataId(String group, String dataId) {
//        Preconditions.checkArgument(StringUtils.isNotBlank(dataId), "配置DataId不能为空");

        final String key = makeKey(group, dataId);

        if (configValues.containsKey(key)) {
            return configValues.get(key);
        }

        String availableConfigureInformation = getValue(group, dataId, new ManagerListener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String s) {
                log.info("监听到变化，配置:{}的值:{}，将它保存到内存中", key, s);
                if (s == null) {
                    s = "";
                }

                configValues.put(key, s);
            }
        }, DEFAULT_READ_TIMEOUT);
        configValues.put(key, availableConfigureInformation);

        log.debug("获取到配置:{}的值:{}", key, availableConfigureInformation);
        return availableConfigureInformation;
    }


    public static String getValue(String group, String dataId, ManagerListener managerListener, long timeout) {
        DiamondConfigure dc = DiamondClientFactory.getSingletonDiamondSubscriber().getDiamondConfigure();
        if (dc != null) {
            if (dc.getMaxHostConnections() < 20) {
                dc.setMaxHostConnections(20);
            }
        } else {
            dc = new DiamondConfigure();
            dc.setMaxHostConnections(20);
            DiamondClientFactory.getSingletonDiamondSubscriber().setDiamondConfigure(dc);
        }

        DiamondManager diamondManager = new DefaultDiamondManager(group, dataId, managerListener);

        String availableConfigureInformation = null;
        try {
            availableConfigureInformation = diamondManager.getAvailableConfigureInformation(timeout);
        } catch (Exception e) {
        }

        if (availableConfigureInformation == null) {
            availableConfigureInformation = "";
        }

        return availableConfigureInformation;
    }
}
