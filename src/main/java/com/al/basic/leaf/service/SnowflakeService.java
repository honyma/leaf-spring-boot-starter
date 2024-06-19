package com.al.basic.leaf.service;

import com.al.basic.leaf.core.IDGen;
import com.al.basic.leaf.core.common.LeafConfig;
import com.al.basic.leaf.core.common.Result;
import com.al.basic.leaf.core.common.ZeroIDGen;
import com.al.basic.leaf.core.snowflake.SnowflakeIDGenImpl;
import com.al.basic.leaf.exception.InitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnowflakeService {
    private Logger logger = LoggerFactory.getLogger(SnowflakeService.class);
    IDGen idGen;
    public SnowflakeService(LeafConfig leafConfig) throws InitException {
        if (leafConfig.isSnowflakeEnable()) {
            String zkAddress = leafConfig.getSnowflakeZkAddress();
            int port = leafConfig.getSnowflakePort();
            idGen = new SnowflakeIDGenImpl(zkAddress, port);
            if(idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }
    public Result getId(String key) {
        return idGen.get(key);
    }
}
