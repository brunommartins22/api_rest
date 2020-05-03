package br.com.bruno_martins.padrao.rest.util.configuration;

import br.com.bruno_martins.padrao.rest.util.DataBaseCfg;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author Bruno Martins
 */
@Configuration
public class MultiDatasourceConfiguration {

    private final DataBaseCfg dataBaseCfg = new DataBaseCfg();

    //Inclua a propriedade 'packages.to.scan' no arquivo application.properties
    //para alterar o valor deste atributo
    @Value("${packages.to.scan:br.com.bruno_martins}")
    private String[] packagesToScan;

    //Tem que ser criado como bean, para
    //ser injetado com as propriedades do arquivo application.propreties
    @Bean
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    //Configura o postgre como datasource primário
    //e utiliza as configurações do arquivo
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDatasource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaProperties jpaProperties) {

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

        hibernateJpaVendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        hibernateJpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
        // emBean.setJpaPropertyMap(.getHibernateProperties(primaryDatasource));
        LocalContainerEntityManagerFactoryBean emBean = new LocalContainerEntityManagerFactoryBean();
        emBean.setDataSource(dataSource);
        emBean.setPackagesToScan(packagesToScan);
        emBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        emBean.setJpaPropertyMap(jpaProperties.getHibernateProperties(dataSource));

        return emBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(factory);

        return transactionManager;
    }

}
