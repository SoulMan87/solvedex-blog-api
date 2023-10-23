package com.soulrebel.blog;

import com.soulrebel.blog.configuration.BootstrapData;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Solvedex Blog API", version = "1.0", description = "Solvedex-blog-api", termsOfService = "https://swagger.io/terms/", extensions = {
}), externalDocs = @ExternalDocumentation(description = "Solvedex Blog API", url = "https://github.com/SoulMan87/solvedex-blog-api"))
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BootstrapBlogApi implements CommandLineRunner {

    private final BootstrapData bootstrapData;

    public static void main(String[] args) {
        SpringApplication.run (BootstrapBlogApi.class, args);
    }

    @Override
    public void run(String... args) {
        bootstrapData.bootstrap ();
    }
}
