package com.al.basic.leaf.starter;

import com.al.basic.leaf.Constants;
import com.al.basic.leaf.service.SnowflakeService;
import com.al.basic.leaf.core.common.LeafConfig;
import com.al.basic.leaf.exception.InitException;
import com.al.basic.leaf.service.LeafService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LeafAutoConfiguration {

    @ConditionalOnProperty(prefix = "leaf", name = "enable", havingValue = "true", matchIfMissing = true)
    @Bean
    public LeafService idGenService(DataSource dataSource) throws InitException {
        return new LeafService(dataSource, lazyInit);
    }

    @ConditionalOnProperty(prefix = "leaf", name = "enable", havingValue = "true", matchIfMissing = true)
    @Bean
    public SnowflakeService snowflakeIdGenService() throws InitException {
        return new SnowflakeService(buildLeafConfig());
    }

    @Value("${" + Constants.LEAF_SEGMENT_ENABLE + ":true}")
    private boolean segmentEnable;

    @Value("${" + Constants.SPRING_JDBC_URL + ":}")
    private String springJdbcUrl;

    @Value("${" + Constants.SPRING_JDBC_USERNAME + ":}")
    private String springJdbcUsername;

    @Value("${" + Constants.SPRING_JDBC_PASSWORD + ":}")
    private String springJdbcPassword;

    @Value("${" + Constants.LEAF_SNOWFLAKE_ENABLE + ":false}")
    private boolean snowflakeEnable;

    @Value("${" + Constants.LEAF_SNOWFLAKE_ZK_ADDRESS + ":}")
    private String snowflakeZkAddress;

    @Value("${" + Constants.LEAF_SNOWFLAKE_PORT + ":0}")
    private int snowflakeZkPort;

    @Value("${" + Constants.LEAF_LAZY_INIT + ":false}")
    private boolean lazyInit;

    /**
     * 优先使用spring.datasource的jdbc链接，如果没有则使用id-gen的公用配置leaf_id_gen
     *
     * @return
     */
    public LeafConfig buildLeafConfig() {
        LeafConfig leafConfig = new LeafConfig();
        //查看是否开启了leaf功能, 如果开启则从spring的上下文种获取配置
        leafConfig.setSegmentEnable(segmentEnable);
        leafConfig.setJdbcUrl(springJdbcUrl);
        leafConfig.setJdbcUsername(springJdbcUsername);
        leafConfig.setJdbcPassword(springJdbcPassword);
        leafConfig.setSnowflakeEnable(snowflakeEnable);
        leafConfig.setSnowflakeZkAddress(snowflakeZkAddress);
        leafConfig.setSnowflakePort(snowflakeZkPort);
        leafConfig.setLazyInit(lazyInit);

        return leafConfig;
    }
}
