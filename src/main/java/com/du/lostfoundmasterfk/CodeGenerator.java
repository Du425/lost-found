package com.du.lostfoundmasterfk;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.sql.SQLException;
import java.util.Collections;

public class CodeGenerator {

    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:33060/lost_found",
            "root",
            "DST773344");

    public static void main(String[] args) throws SQLException {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder ->
                        builder.author("Du425")
                                .fileOverride()
                                .enableSwagger()
                                .dateType(DateType.TIME_PACK)
                                .commentDate("yyyy-MM-dd")
                                .outputDir("D://java-code/lost-found-master-fk/src/main/java"))
                // 包配置
                .packageConfig(builder ->
                        builder.parent("com.du.lostfoundmasterfk")
                                .service("service")
                                .serviceImpl("service.impl")
                                .mapper("dao")
                                .controller("controller")
                                .other("other")
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://java-code/lost-found-master-fk/src/main/resources/mapper")))
                // 策略配置
                .strategyConfig(builder -> builder.addInclude("t_admin_audit","t_found_comment","t_found_loss","t_found_thing","t_loss_comment","t_loss_thing","t_message","t_permission","t_thing_type","t_user","t_user_permission","t_user_star"))

                .execute();

    }


}
