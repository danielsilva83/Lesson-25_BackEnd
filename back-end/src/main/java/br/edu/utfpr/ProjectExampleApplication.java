package br.edu.utfpr;

import br.edu.utfpr.dao.ClienteDAO;
import br.edu.utfpr.dao.PaisDAO;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectExampleApplication implements CommandLineRunner {

    private final ClienteDAO clienteDAO;
    private final PaisDAO paisDAO;
    
    @Autowired
    public ProjectExampleApplication(PaisDAO paisDAO, ClienteDAO clienteDAO) {
        this.paisDAO = paisDAO;
        this.clienteDAO = clienteDAO;
    }
    
    
    public static void main(String[] args) {
        SpringApplication.run(ProjectExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

                
    }

}
