# leaf-spring-boot-starter
通过美团Leaf-segment框架封装了个spring starter启动器，可clone下来到你本地，然后通过maven打包后引入该依赖到你工程项目中，就可以直接生成分布式ID使用了。

## 1、引入最新版maven依赖
本地升级你要打的包版本，打包上传后，在你的工程pom文件里面引入相关依赖包

```java
<dependency>
    <groupId>com.al.basic.id</groupId>
    <artifactId>leaf-spring-boot-starter</artifactId>
    <version>xxx-RELEASE</version>
</dependency>
```
## 2、初始化配置
该框架生成的分布式ID默认是基于mysql的号段发号模式，所以需要依赖于数据库。使用spring.datasource的数据库,所以如果你使用的spring的数据库管理，需要在配置中心或本地工程配置文件加上如下配置：

```properties
leaf.enable=true
spring.datasource.url=xxx
spring.datasource.username=xxx
spring.datasource.password=xxx
```
## 3、初始化数据库
由于采用mysql的号段发号模式生成分布式ID， 需要依赖数据库表，所以需要提前在上面配置的spring.datasource数据库中创建序列号表，通过序列号表进行号段管理和维护。
```sql
DROP TABLE IF EXISTS `leaf_alloc`;

CREATE TABLE `leaf_alloc` (
  `biz_tag` varchar(128)  NOT NULL DEFAULT '',
  `max_id` bigint(20) NOT NULL DEFAULT '1',
  `step` int(11) NOT NULL,
  `description` varchar(256)  DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB;
```

上面依赖表创建好后，并准备初始化脚本: 如对某个业务生成分布式ID，需要先添加id生成记录sql，sql带该业务类型对应ID的初始值和步长。

说明：
biz_tag：一般是工程的${application.name }.${table_name}
# 例如支付订单表t_pay_order的id生成记录的tag为'order-service.t_order'

```sql
INSERT INTO `leaf_alloc`(`biz_tag`,`max_id`,`step`,`description`,`update_time`) VALUES('order-service.t_order',1,1000,'订单主表',NOW());
```
# 这句话的意思是添加一条biz_tag叫`order-service.t_order`的自增生成记录，当前从1开始递增，每次客户端取1000个数



