package com.marcelomartins.desafiopitang;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.spi.http.HttpExchange;

import com.marcelomartins.desafiopitang.model.Telefone;
import com.marcelomartins.desafiopitang.model.Usuario;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

public class Application {
	 public static void main(String[] args) throws IOException {
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pitang");
	        EntityManager em = emf.createEntityManager();
	        
	        Telefone t0, t1, t2;
	        Usuario u0, u1;
	        
	        t0 = new Telefone(null, 68, "98565431", "celular");
	        t1 = new Telefone(null, 68, "992314972", "celular");
	        t2 = new Telefone(null, 68, "32119987", "fixo");
	        
	        u0 = new Usuario(null, "Jim Morrison", "jimjim@thedoors.com", "123");
	        u0.setTelefones(Arrays.asList(t0, t1));
	        u1 = new Usuario(null, "Freddie Mercury", "fredinho@queen.com", "456");
	        u1.setTelefones(Arrays.asList(t2));
	        
	        em.getTransaction().begin();
	        em.persist(t0);
	        em.persist(t1);
	        em.persist(t2);
	        em.persist(u0);
	        em.persist(u1);
	        em.getTransaction().commit();
	        
	        
	        
	        
	        int serverPort = 8000;
	        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
	        server.createContext("/faces");
	        
	        server.setExecutor(null);
	        server.start();
	        
	    }
	 
	 
	 
}



