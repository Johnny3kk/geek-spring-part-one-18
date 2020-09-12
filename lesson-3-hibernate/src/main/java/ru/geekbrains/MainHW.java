package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

public class MainHW {

    public static void main(String[] args) {

        EntityManagerFactory emFactory = new Configuration().configure("hibernateHW.cfg.xml").buildSessionFactory();

         //INSERT
//        EntityManager em = emFactory.createEntityManager();
////        Customer cus1 = new Customer(null, "Bill");
////        Customer cus2 = new Customer(null, "John");
////        Customer cus3 = new Customer(null, "Bob");
//        Product prod1 = new Product(null, "memory", new BigDecimal(100));
//        Product prod2 = new Product(null, "processor", new BigDecimal(500));
//        Product prod3 = new Product(null, "motherboard", new BigDecimal(350));
//        Product prod4 = new Product(null, "videocard", new BigDecimal(1200));
//
//        em.getTransaction().begin();
////        em.persist(cus1);
////        em.persist(cus2);
////        em.persist(cus3);
//        em.persist(prod1);
//        em.persist(prod2);
//        em.persist(prod3);
//        em.persist(prod4);
//
//        em.getTransaction().commit();
//
//        em.close();

            //MORE INSERT
//        EntityManager em = emFactory.createEntityManager();
//
//        Customer customer1 = em.find(Customer.class, 1);
//        Customer customer2 = em.find(Customer.class, 2);
//        Customer customer3 = em.find(Customer.class, 3);
//        Product product1 = em.find(Product.class, 1);
//        Product product2 = em.find(Product.class, 2);
//        Product product3 = em.find(Product.class, 3);
//        Product product4 = em.find(Product.class, 4);
//        OrderItem orderItem1 = new OrderItem(null, customer1, product1, product1.getPrice(), 2);
//        OrderItem orderItem2 = new OrderItem(null, customer2, product1, product1.getPrice(), 4);
//        OrderItem orderItem3 = new OrderItem(null, customer3, product1, product1.getPrice(), 4);
//        OrderItem orderItem4 = new OrderItem(null, customer1, product2, product2.getPrice(), 1);
//        OrderItem orderItem5 = new OrderItem(null, customer1, product3, product3.getPrice(), 1);
//        OrderItem orderItem6 = new OrderItem(null, customer1, product4, product4.getPrice(), 1);
//        OrderItem orderItem7 = new OrderItem(null, customer1, product4, product4.getPrice(), 1);
//        OrderItem orderItem8 = new OrderItem(null, customer1, product4, product4.getPrice(), 1);
//
//        em.getTransaction().begin();
//
//        em.persist(orderItem1);
//        em.persist(orderItem2);
//        em.persist(orderItem3);
//        em.persist(orderItem4);
//        em.persist(orderItem5);
//        em.persist(orderItem6);
//        em.persist(orderItem7);
//        em.persist(orderItem8);
//
//        em.getTransaction().commit();

        //SELECT Home Work 3
        EntityManager em = emFactory.createEntityManager();


        List<Product> products = em.createQuery("select oi.product from OrderItem oi where oi.customer.name = :name", Product.class)
                .setParameter("name", "John")
                .getResultList();
        System.out.println(products);

        List<Customer> customers = em.createQuery("select oi.customer from OrderItem oi where oi.product.title = :title", Customer.class)
                .setParameter("title", "memory")
                .getResultList();
        System.out.println(customers);

        //DELETE Home Work 3
        em.getTransaction().begin();

        Product prod = em.createQuery("select p from Product p where title = :title", Product.class)
                .setParameter("title", "processor")
                .getSingleResult();
        em.remove(prod);

        Customer cust = em.createQuery("select c from Customer c where name = :name", Customer.class)
                .setParameter("name", "Bob")
                .getSingleResult();
        em.remove(cust);

        em.getTransaction().commit();

        em.close();
    }
}
