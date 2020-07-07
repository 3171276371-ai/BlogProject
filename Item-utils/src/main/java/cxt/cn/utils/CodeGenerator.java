package cxt.cn.utils;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h3>mpgenerator</h3>
 * <p></p>
 *
 * @author : zhengyue
 * @date : 2020-03-13 17:13
 **/
public class CodeGenerator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
//        String [] tableNames = new String[]{"cms_user"};
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");

        String [] modules = new String[]{"Item-controller", "Item-service", "Item-dao", "Item-pojo"};//项目模块名，需自定义
        for (String module : modules) {
            moduleGenerator(module,tableNames);
        }
    }

    private static void moduleGenerator(String module,String [] tableNames){

        GlobalConfig globalConfig = getGlobalConfig(module);// 全局配置

        DataSourceConfig dataSourceConfig = getDataSourceConfig();// 数据源配置

        PackageConfig packageConfig = getPackageConfig(module);// 包配置

        StrategyConfig strategyConfig = getStrategyConfig(tableNames);// 策略配置

        TemplateConfig templateConfig = getTemplateConfig(module);// 配置模板

        InjectionConfig cfgConfig = getCfgConfig(); //自定义设置

        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig)
                .setTemplate(templateConfig)
                .setCfg(cfgConfig)
                .execute();

    }

    private static InjectionConfig getCfgConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String projectPath = System.getProperty("user.dir");

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return new File("Item-dao").getAbsolutePath()+ "/src/main/resources/mapper/" + tableInfo.getEntityName()
                        + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static TemplateConfig getTemplateConfig(String module) {
        TemplateConfig templateConfig = new TemplateConfig();
        //studio_pojo", "studio_dao", "studio_service", "studio_controller
        if ("Item-pojo".equals(module)){
            templateConfig.setEntity(new TemplateConfig().getEntity(false))
                    .setMapper(null)//mapper模板
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);//service模块不生成controller代码
        } else if ("Item-dao".equals(module)){//web模块只生成controller代码
            templateConfig.setEntity(null)
                    .setMapper(new TemplateConfig().getMapper())
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);
        } else if ("Item-service".equals(module)){//web模块只生成controller代码
            templateConfig.setEntity(null)
                    .setMapper(null)
                    .setXml(null)
                    .setService(new TemplateConfig().getService())
                    .setServiceImpl(new TemplateConfig().getServiceImpl())
                    .setController(null);
        } else if ("Item-controller".equals(module)){//web模块只生成controller代码
            templateConfig.setEntity(null)
                    .setMapper(null)
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(new TemplateConfig().getController());
        } else {
            throw new IllegalArgumentException("参数匹配错误，请检查");
        }
        return templateConfig;
    }

    private static StrategyConfig getStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)//驼峰命名
                .setEntityLombokModel(true) //lombok
                .setRestControllerStyle(true) //restful
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass("cxt.cn.entity.BaseEntity")
                .setSuperEntityColumns(new String[]{"id", "create_id", "update_id", "create_time", "update_time", "is_del"})
                .setInclude(tableNames);
        return strategyConfig;
    }

    private static PackageConfig getPackageConfig(String module) {
        PackageConfig packageConfig = new PackageConfig();
        String packageName = "cxt.cn";//不同模块 代码生成具体路径自定义指定
        packageConfig.setParent(packageName)
                .setEntity("entity")
                .setMapper("mapper")
//                .setXml("mapper")
                .setService("service")
                .setController("controller");
        return packageConfig;
    }

    private static DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://121.199.73.204:3306/blog?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&allowPublicKeyRetrieval=true";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName(Driver.class.getName())
                .setUsername("root")
                .setPassword("root")
                .setUrl(dbUrl);
        return dataSourceConfig;
    }

    private static GlobalConfig getGlobalConfig(String module) {
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOpen(false)//new File(module).getAbsolutePath()得到模块根目录路径，因事Maven项目，代码指定路径自定义调整
                .setOutputDir(new File(module).getAbsolutePath()+"/src/main/java") //生成文件的输出目录
                .setFileOverride(false)//是否覆盖已有文件
                .setActiveRecord(false)
                .setIdType(IdType.ID_WORKER)
                .setAuthor("chenxiaotian");
        return globalConfig;
    }
}
