package edu.wpi.teamb.Entities;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public enum SessionGetter {

  // XML based configuration
  CONNECTION;
  private static SessionFactory sessionFactory;

  private static StandardServiceRegistry ssr;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) sessionFactory = buildSessionFactory();
    return sessionFactory;
  }

  private static SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      ssr =
          new StandardServiceRegistryBuilder()
              .configure("edu/wpi/teamb/hibernate/hibernate.cfg.xml")
              .build();
      System.out.println("Hibernate Configuration loaded");

      sessionFactory = new MetadataSources(ssr).buildMetadata().buildSessionFactory();

      return sessionFactory;
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
}
