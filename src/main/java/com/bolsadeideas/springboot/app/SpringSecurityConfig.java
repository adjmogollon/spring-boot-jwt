package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.app.auth.filter.JWTAuthenticationFilter;
import com.bolsadeideas.springboot.app.auth.filter.JWTAuthorizationFilter;
import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.app.auth.service.JWTService;
import com.bolsadeideas.springboot.app.models.service.JpaUserDatailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JpaUserDatailsService userDetailsService;
	
	@Autowired
	private JWTService jwtService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**", "/js/**", "/images/**", "/listar**","/locale","/test/**","/webjars/**").permitAll()
				// .antMatchers("/ver/**").hasAnyRole("USER")
				// .antMatchers("/uploads/**").hasAnyRole("USER")
				// .antMatchers("/form/**").hasAnyRole("ADMIN")
				// .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				// .antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//					.successHandler(successHandler)
//					.loginPage("/login")
//				.permitAll()
//				.and()
//				.logout().permitAll()
//				.and()
//				.exceptionHandling()
//				.accessDeniedPage("/error_403")
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtService))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtService))
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		builder.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		/*
		 *
		 * builder.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder)
		 * .usersByUsernameQuery("select user username, password, enable from users where username=?"
		 * )
		 * .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?"
		 * );
		 */

		/*
		 * PasswordEncoder encoder =this.passwordEncoder; //PasswordEncoder encoder =
		 * PasswordEncoderFactories.createDelegatingPasswordEncoder();
		 * 
		 * UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		 * 
		 * builder.inMemoryAuthentication()
		 * .withUser(users.username("admin").password("admin").roles("ADMIN","USER"))
		 * .withUser(users.username("anibal").password("anibal").roles("USER"));
		 */

	}

}
