package com.example.ReservaBiblioteca.config;

// Importa anotações do Spring para configuração
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Classe responsável por criptografia de senhas
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*Indica ao Spring que esta classe contém configurações da aplicação. */
@Configuration
public class PasswordConfig {

    /*Registra um Bean no container do Spring Esse Bean poderá ser injetado em qualquer
     * Service ou Controller usando @Autowired ou injeção por construtor.*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        /*Cria uma instância do BCrypt. BCrypt é um algoritmo seguro para armazenar senhas porque: 
         *- gera hash irreversível
         * - adiciona salt automaticamente
         * - dificulta ataques de força bruta
         */
        return new BCryptPasswordEncoder();
    }
}