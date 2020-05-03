package br.com.bruno_martins.padrao.rest.util.configuration;

import br.com.bruno_martins.padrao.rest.services.SessionService;
import br.com.bruno_martins.padrao.rest.services.UsuarioService;
import br.com.bruno_martins.padrao.rest.util.JWTAuthenticationFilter;
import br.com.bruno_martins.padrao.rest.util.JWTCookieFilter;
import br.com.bruno_martins.padrao.rest.util.JWTLoginFilter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author Bruno Martins
 */
@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SessionService sessionService;

    private final boolean habilitaToken = true;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (habilitaToken) {

            httpSecurity.csrf().disable().authorizeRequests()
                    .antMatchers("/home").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/security/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/security/cookie").permitAll()
                    .antMatchers("/api/arquivos-processar/upload").permitAll()
                    .antMatchers("/api/agencias/upload").permitAll()
                    .antMatchers("/api/agencia-certificado/upload").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/r/n").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/ativo").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/dataAtual").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/contratos/isPrimeiroAcesso").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/contratos/l").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .cors()
                    .and()
                    // filtra requisições de login
                    .addFilterBefore(
                            new JWTLoginFilter(
                                    "/api/security/login",
                                    authenticationManager(),
                                    usuarioService,
                                    sessionService
                            ),
                            UsernamePasswordAuthenticationFilter.class
                    )
                    .addFilterBefore(
                            new JWTCookieFilter(
                                    "/api/security/cookie",
                                    sessionService
                            ),
                            UsernamePasswordAuthenticationFilter.class
                    )
                    // filtra outras requisições para verificar a presença do JWT no header
                    .addFilterBefore(new JWTAuthenticationFilter(sessionService),
                            UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }

    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(usuarioService);

        return authProvider;

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("*"));

        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SessionService sessionService() {

        return new SessionService();

    }

    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
