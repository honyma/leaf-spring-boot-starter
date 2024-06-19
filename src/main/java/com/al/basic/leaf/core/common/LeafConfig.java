package com.al.basic.leaf.core.common;


/**
 * leaf配置文件
 * @author zhangyue
 */
public class LeafConfig {
    private boolean segmentEnable;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private boolean snowflakeEnable;
    private int snowflakePort;
    private String snowflakeZkAddress;
    private boolean lazyInit;

    public boolean isSegmentEnable() {
        return segmentEnable;
    }

    public void setSegmentEnable(boolean segmentEnable) {
        this.segmentEnable = segmentEnable;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public boolean isSnowflakeEnable() {
        return snowflakeEnable;
    }

    public void setSnowflakeEnable(boolean snowflakeEnable) {
        this.snowflakeEnable = snowflakeEnable;
    }

    public int getSnowflakePort() {
        return snowflakePort;
    }

    public void setSnowflakePort(int snowflakePort) {
        this.snowflakePort = snowflakePort;
    }

    public String getSnowflakeZkAddress() {
        return snowflakeZkAddress;
    }

    public void setSnowflakeZkAddress(String snowflakeZkAddress) {
        this.snowflakeZkAddress = snowflakeZkAddress;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }
}
