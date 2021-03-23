package org.king2.log.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * 描述：
 *      配置对应的德鲁伊数据源信息
 *      虽然我们配置了druid连接池的其它属性，但是不会生效。
 *      因为默认是使用的java.sql.Datasource的类来获取属性的，有些属性datasource没有。如果我们想让配置生效，需要手动创建Druid的配置文件。
 * @author 刘梓江
 */
@Configuration
public class DruidConfiguration {

    /**配置数据源**/
    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        DruidDataSource druidDataSource = new DruidDataSource();//创建数据源
        return druidDataSource;
    }

    /**配置监控服务器**/
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //servletRegistrationBean.addInitParameter("allow", "127.0.0.1");                   // 添加IP白名单
        //servletRegistrationBean.addInitParameter("deny", "127.0.0.1");                    // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
        servletRegistrationBean.addInitParameter("loginUsername", "yuzijiang");// 添加控制台管理用户
        servletRegistrationBean.addInitParameter("loginPassword", "yuzijiang");
        servletRegistrationBean.addInitParameter("resetEnable", "false");       // 是否能够重置数据  禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }


    /**配置服务过滤器**/
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");                                                                        // 添加过滤规则
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");  // 忽略过滤格式
        return filterRegistrationBean;
    }

}
