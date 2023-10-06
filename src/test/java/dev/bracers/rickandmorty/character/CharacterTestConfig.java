package dev.bracers.rickandmorty.character;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dev.bracers.rickandmorty.episode.Episode;
import dev.bracers.rickandmorty.episode.EpisodeRepository;
import jakarta.persistence.EntityManagerFactory;



@Configuration
@EnableJpaRepositories(basePackageClasses = {EpisodeRepository.class, CharacterRepository.class})
@ComponentScan(basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"})
public class CharacterTestConfig {
	
	@MockBean
	RestTemplateBuilder builder;
	
	@Bean
	@Primary
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean("getAllEpisodes")
	@Primary
	public CommandLineRunner getAllEpisodes(RestTemplate restTemplate, EpisodeRepository repository) throws Exception {
		return args -> {
			List<Episode> episodes = Arrays.asList(
					new Episode(1L, "Episode 1", "September 4, 2013"),
					new Episode(2L, "Episode 2", "September 14, 2013"),
					new Episode(3L, "Episode 3", "September 24, 2013"),
					new Episode(4L, "Episode 4", "October 4, 2013"),
					new Episode(5L, "Episode 5", "October 14, 2013"),
					new Episode(6L, "Episode 6", "October24, 2013"));
			
			repository.saveAll(episodes);
		};
	}
	
	@Bean("getAllCharacters")
	@DependsOn("getAllEpisodes")
	@Primary
	public CommandLineRunner getAllCharacters(RestTemplate restTemplate, CharacterRepository repository) throws Exception {
		return args -> {
			List<Character> characters = Arrays.asList(
					new Character(
							1L,
							"Rick Sanchez",
							Arrays.asList(
									new Episode(1L),
									new Episode(2L),
									new Episode(3L),
									new Episode(4L),
									new Episode(5L),
									new Episode(6L)
									)
					),
					new Character(
							2L,
							"Morty Smith",
							Arrays.asList(
									new Episode(1L),
									new Episode(2L),
									new Episode(3L),
									new Episode(4L),
									new Episode(5L),
									new Episode(6L)
							)
					),
					new Character(
							3L,
							"Abadango Cluster Princess",
							Arrays.asList(
									new Episode(3L)
							)
					),
					new Character(
							4L,
							"Rick Sanchez",
							Arrays.asList(
									new Episode(4L)
							)
					)
			);
			
			repository.saveAll(characters);
		};
	}

	@Bean(destroyMethod = "close")
    DataSource dataSource(Environment env) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("spring.datasource.password"));
 
        return new HikariDataSource(dataSourceConfig);
    }
	
	@Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, 
                                                                Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode");
 
        Properties jpaProperties = new Properties();
     
        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
 
        
        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", 
                env.getRequiredProperty("hibernate.hbm2ddl.auto")
        );
 
        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put("hibernate.ejb.naming_strategy", 
                env.getRequiredProperty("hibernate.ejb.naming_strategy")
        );
 
        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql", 
                env.getRequiredProperty("spring.jpa.show-sql")
        );
 
        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
        jpaProperties.put("hibernate.format_sql", 
                env.getRequiredProperty("hibernate.format_sql")
        );
        
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
 
        return entityManagerFactoryBean;
    }
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(emf);

	    return transactionManager;
	}
}
