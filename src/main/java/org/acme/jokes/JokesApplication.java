package org.acme.jokes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Random Joke API",
        version = "1.0",
        description = "API for fetching random jokes",
        license = @License(name = "GNU General Public License v3.0", url = "https://www.gnu.org/licenses/gpl-3.0.html")

))
@SpringBootApplication
public class JokesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JokesApplication.class, args);
    }

}