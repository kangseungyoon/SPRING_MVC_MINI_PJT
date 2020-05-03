package kr.co.bepoz.config;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.eclipse.jdt.internal.compiler.parser.RecoveredElement;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.bepoz.beans.UserBean;
import kr.co.bepoz.interceptor.CheckLoginInterceptor;
import kr.co.bepoz.interceptor.CheckWriterInterceptor;
import kr.co.bepoz.interceptor.TopMenuInterceptor;
import kr.co.bepoz.mapper.BoardMapper;
import kr.co.bepoz.mapper.TopMenuMapper;
import kr.co.bepoz.mapper.UserMapper;
import kr.co.bepoz.service.BoardService;
import kr.co.bepoz.service.TopMenuService;

//Spring MVC ������Ʈ�� ���õ� ������ �ϴ� Ŭ����
@Configuration
//Controller ������̼��� ���õǾ��ִ� Ŭ������ COntroller�� ����Ѵ�.
@EnableWebMvc
//��ĵ�� ��Ű���� �����Ѵ�.
@ComponentScan("kr.co.bepoz.controller")
@ComponentScan("kr.co.bepoz.service")
@ComponentScan("kr.co.bepoz.dao")


@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
	
	@Value("${db.classname}")
	private String db_classname;
	
	@Value("${db.url}")
	private String db_url;
	
	@Value("${db.username}")
	private String db_username;
	
	@Value("${db.password}")
	private String db_password;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@Autowired
	private BoardService boardService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	//Controller�� �޼��尡 ��ȯ�ϴ� jsp�� �̸� �� �ڿ� ��ο� Ȯ���ڸ� �ٿ��ֵ��� �����Ѵ�.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/",".jsp");
	}
	
	//���� ������ ��θ� �����Ѵ�.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
	//�����ͺ��̽� ���� ������ �����ϴ� Bean
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source=new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);
		
		return source;
	}
	
	//�������� ���� ������ �����ϴ� ��ü
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception{
		SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}
	
	//������ ������ ���� ��ü(Mapper ����)
	@Bean
	public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception{ 
		MapperFactoryBean<BoardMapper> factoryBean=new MapperFactoryBean<BoardMapper>(BoardMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{ 
		MapperFactoryBean<TopMenuMapper> factoryBean=new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addInterceptors(registry);
		
		TopMenuInterceptor topMenuInterceptor=new TopMenuInterceptor(topMenuService,loginUserBean);
		CheckLoginInterceptor checkLoginInterceptor=new CheckLoginInterceptor(loginUserBean);
		CheckWriterInterceptor checkWriterInterceptor=new CheckWriterInterceptor(loginUserBean, boardService);
		
		InterceptorRegistration reg1=registry.addInterceptor(topMenuInterceptor);
		InterceptorRegistration reg2=registry.addInterceptor(checkLoginInterceptor);
		InterceptorRegistration reg3=registry.addInterceptor(checkWriterInterceptor);
		
		reg1.addPathPatterns("/**");
		reg2.addPathPatterns("/user/modify","/user/logout","/board/*");
		reg2.excludePathPatterns("/board/main");
		reg3.addPathPatterns("/board/modify","/board/delete");
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer PropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource res=new ReloadableResourceBundleMessageSource();
		res.setBasenames("/WEB-INF/properties/error_message");
		return res;
	}
	
	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception{ 
		MapperFactoryBean<UserMapper> factoryBean=new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	
}