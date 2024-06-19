package com.al.basic.leaf.service;

import com.al.basic.leaf.core.IDGen;
import com.al.basic.leaf.core.common.Result;
import com.al.basic.leaf.core.common.Status;
import com.al.basic.leaf.core.segment.SegmentIDGenImpl;
import com.al.basic.leaf.core.segment.dao.IDAllocDao;
import com.al.basic.leaf.core.segment.dao.impl.IDAllocDaoImpl;
import com.al.basic.leaf.core.common.LeafConfig;
import com.al.basic.leaf.core.common.ZeroIDGen;
import com.al.basic.leaf.exception.InitException;
import com.al.basic.leaf.exception.LeafServerException;
import com.al.basic.leaf.exception.NoKeyException;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class LeafService {
    private Logger logger = LoggerFactory.getLogger(LeafService.class);
    IDGen idGen;
    DruidDataSource dataSource;

    public LeafService(LeafConfig leafConfig, boolean lazyInit) throws SQLException, InitException {
        //config instance is singleton for each namespace and is never null
        if (leafConfig.isSegmentEnable()) {
            // Config dataSource
            dataSource = new DruidDataSource();
            dataSource.setUrl(leafConfig.getJdbcUrl());
            dataSource.setUsername(leafConfig.getJdbcUsername());
            dataSource.setPassword(leafConfig.getJdbcPassword());
            dataSource.init();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl(dao, lazyInit);
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }

    public LeafService(DataSource dataSource, boolean lazyInit) throws InitException {
        // Config Dao
        IDAllocDao dao = new IDAllocDaoImpl(dataSource);
        // Config ID Gen
        // Config ID Gen
        idGen = new SegmentIDGenImpl(dao, lazyInit);
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public Long genId(String key) {
        Result result = idGen.get(key);
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }

        if (result.getStatus().equals(Status.EXCEPTION)) {
            logger.warn("genId has exception. id {} exception {}", result.getId(), result.getStatus());
            throw new LeafServerException(result.toString());
        }
        return result.getId();
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
