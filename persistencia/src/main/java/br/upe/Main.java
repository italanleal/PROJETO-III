package br.upe;

import br.upe.dao.JBDCEventDAO;
import br.upe.entities.Event;
import br.upe.util.DefaultEntityManagerFactory;
import br.upe.util.KeeperInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JBDCEventDAO eventDAO = new JBDCEventDAO(DefaultEntityManagerFactory::createEntityManager);

        List<Event> arr = eventDAO.findAll();
        for(Event e : arr) {
            System.out.println(e.getId());
            System.out.println(e.getTitle());
            System.out.println(e.getDescription());
            System.out.println(e.getDirector());
        }
        DefaultEntityManagerFactory.close();
    }
}