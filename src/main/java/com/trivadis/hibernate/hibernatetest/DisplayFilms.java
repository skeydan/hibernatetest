package com.trivadis.hibernate.hibernatetest;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DisplayFilms {

  private static final int CUSTOMER_MAX_ID = 599;
  private static int CATEGORY_MAX_ID = 15;
  private final static Logger logger = Logger.getLogger(DisplayFilms.class.getName());

  public static void main(String[] args) {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.trivadis.hibernate.hibernatetest");

    EntityTransaction txn = null;

    long startTime = System.nanoTime();

    final EntityManager em = entityManagerFactory.createEntityManager();

    try {

      txn = em.getTransaction();
      txn.begin();
      IntStream.range(0, 10000).forEach(i -> new DisplayFilms().doWork(em));
      txn.commit();

    } catch (RuntimeException e) {
      if (txn != null && txn.isActive()) {
        txn.rollback();
      }
      throw e;
    } finally {
      if (em != null) {
        em.close();
      }
    }

    long endTime = System.nanoTime();
    long durationMs = (endTime - startTime) / 1000000;
    logger.info("Execution time: " + durationMs);

    entityManagerFactory.close();
  }

  /*
   ### l. 35
   ### nur beim 1. Mal!
   ### before select country0_.COUNTRY_ID as COUNTRY_ID1_5_,
    
   select city0_.CITY_ID as CITY_ID1_4_0_, city0_.CITY as CITY2_4_0_, city0_.COUNTRY_ID as COUNTRY_ID4_4_0_, city0_.LAST_UPDATE as LAST_UPDATE3_4_0_,
   country1_.COUNTRY_ID as COUNTRY_ID1_5_1_, country1_.COUNTRY as COUNTRY2_5_1_, country1_.LAST_UPDATE as LAST_UPDATE3_5_1_ 
   from CITY city0_ 
   inner join COUNTRY country1_ on city0_.COUNTRY_ID=country1_.COUNTRY_ID
   where city0_.CITY_ID=:1
   */
  /*
   ### l. 56
   ### in between  select film0_.FILM_ID as FILM_ID1_7_0_
   ### nur beim 1.Mal
    
   select store0_.STORE_ID as STORE_ID1_16_0_, store0_.ADDRESS_ID as ADDRESS_ID3_16_0_, store0_.LAST_UPDATE as LAST_UPDATE2_16_0_, store0_.MANAGER_STAFF_ID as MANAGER_STAFF_ID4_16_0_,
   address1_.ADDRESS_ID as ADDRESS_ID1_2_1_, address1_.ADDRESS as ADDRESS2_2_1_, address1_.ADDRESS2 as ADDRESS3_2_1_, address1_.CITY_ID as CITY_ID8_2_1_, address1_.DISTRICT as DISTRICT4_2_1_, address1_.LAST_UPDATE as LAST_UPDATE5_2_1_, address1_.PHONE as PHONE6_2_1_, address1_.POSTAL_CODE as POSTAL_CODE7_2_1_,
   city2_.CITY_ID as CITY_ID1_4_2_, city2_.CITY as CITY2_4_2_, city2_.COUNTRY_ID as COUNTRY_ID4_4_2_, city2_.LAST_UPDATE as LAST_UPDATE3_4_2_,
   country3_.COUNTRY_ID as COUNTRY_ID1_5_3_, country3_.COUNTRY as COUNTRY2_5_3_, country3_.LAST_UPDATE as LAST_UPDATE3_5_3_,
   staff4_.STAFF_ID as STAFF_ID1_15_4_, staff4_.ACTIVE as ACTIVE2_15_4_, staff4_.ADDRESS_ID as ADDRESS_ID10_15_4_, staff4_.EMAIL as EMAIL3_15_4_, staff4_.FIRST_NAME as FIRST_NAME4_15_4_, staff4_.LAST_NAME as LAST_NAME5_15_4_, staff4_.LAST_UPDATE as LAST_UPDATE6_15_4_, staff4_.PASSWORD as PASSWORD7_15_4_, staff4_.PICTURE as PICTURE8_15_4_, staff4_.STORE_ID as STORE_ID11_15_4_, staff4_.USERNAME as USERNAME9_15_4_,
   address5_.ADDRESS_ID as ADDRESS_ID1_2_5_, address5_.ADDRESS as ADDRESS2_2_5_, address5_.ADDRESS2 as ADDRESS3_2_5_, address5_.CITY_ID as CITY_ID8_2_5_, address5_.DISTRICT as DISTRICT4_2_5_, address5_.LAST_UPDATE as LAST_UPDATE5_2_5_, address5_.PHONE as PHONE6_2_5_, address5_.POSTAL_CODE as POSTAL_CODE7_2_5_,
   store6_.STORE_ID as STORE_ID1_16_6_, store6_.ADDRESS_ID as ADDRESS_ID3_16_6_, store6_.LAST_UPDATE as LAST_UPDATE2_16_6_, store6_.MANAGER_STAFF_ID as MANAGER_STAFF_ID4_16_6_
   from STORE store0_ 
   inner join ADDRESS address1_ on store0_.ADDRESS_ID=address1_.ADDRESS_ID 
   inner join CITY city2_ on address1_.CITY_ID=city2_.CITY_ID 
   inner join COUNTRY country3_ on city2_.COUNTRY_ID=country3_.COUNTRY_ID
   inner join STAFF staff4_ on store0_.MANAGER_STAFF_ID=staff4_.STAFF_ID
   inner join ADDRESS address5_ on staff4_.ADDRESS_ID=address5_.ADDRESS_ID
   inner join STORE store6_ on staff4_.STORE_ID=store6_.STORE_ID
   where store0_.STORE_ID=:1
   */
  /*
   ### l. 167
   ### many
   ### before select category0_.CATEGORY_ID
   ### insges. 491
    
   select address0_.ADDRESS_ID as ADDRESS_ID1_2_0_, address0_.ADDRESS as ADDRESS2_2_0_, address0_.ADDRESS2 as ADDRESS3_2_0_, address0_.CITY_ID as CITY_ID8_2_0_, address0_.DISTRICT as DISTRICT4_2_0_, address0_.LAST_UPDATE as LAST_UPDATE5_2_0_, address0_.PHONE as PHONE6_2_0_, address0_.POSTAL_CODE as POSTAL_CODE7_2_0_, 
   city1_.CITY_ID as CITY_ID1_4_1_, city1_.CITY as CITY2_4_1_, city1_.COUNTRY_ID as COUNTRY_ID4_4_1_, city1_.LAST_UPDATE as LAST_UPDATE3_4_1_, 
   country2_.COUNTRY_ID as COUNTRY_ID1_5_2_, country2_.COUNTRY as COUNTRY2_5_2_, country2_.LAST_UPDATE as LAST_UPDATE3_5_2_ 
   from ADDRESS address0_ 
   inner join CITY city1_ on address0_.CITY_ID=city1_.CITY_ID 
   inner join COUNTRY country2_ on city1_.COUNTRY_ID=country2_.COUNTRY_ID 
   where address0_.ADDRESS_ID=:1

   */
  public void doWork(EntityManager em) {

    // throws javax.persistence.PersistenceException: Hibernate cannot unwrap interface java.sql.Connection
    //Connection connection = em.unwrap(java.sql.Connection.class);
    //connection.setClientInfo("OCSID.MODULE", "hibernatetest");

    /* the target customer */
    Customer customer = getCustomer(em);
    logger.info("Customer is: " + customer.getCustomerId());

    /* find similar customers. Similar here means living in same country */
    /* Step 1: target customer's country */
    //Country customerCountry = getCountry(em, customer.getAddress().getAddressId());
    //logger.info("Customer lives in country: " + customerCountry.getCountry()); 

    /* Step 2: customers who live in same country         */
    //List<Customer> similarCustomers = getSimilarCustomers(em, customerCountry);
    //logger.info("Similar customers are: " + similarCustomers.toString

    List<Customer> similarCustomers = getSimilarCustomers1Step(em, customer.getAddress().getAddressId());    
    logger.info("Similar customers are: " + similarCustomers.toString());
    
    /* target customer's preferred category */
    Category category = getPreferredCategory(em, customer);
    //logger.info("Customer preferred category: " + category);

    /* inventories for similar customers */
    List<Inventory> inventories = getInventories(em, similarCustomers);
    //logger.info("Relevant inventories are: " + inventories.toString());

    /* display films where similarity criterion and category criterion both match */
    //List<Film> films = getFilmInfo(em, inventories, category);
    
    List<Film> films = getFilmInfo1Step(em, similarCustomers, category);    
    logger.info("Displaying films: " + films.stream().map(film -> film.getTitle()).collect(Collectors.toList()));

    //logger.info("Unique films: " + films.size() + ", matching inventories: " + inventories.size() + ", similar customers: "
    //        + similarCustomers.size());
    logger.info("Unique films: " + films.size() + ", matching inventories: " + ", similar customers: "
      + similarCustomers.size());

  }

  /*
   select distinct f.*
   from film f
   join inventory i on (f.film_id=i.film_id)
   join film_category fc on (f.film_id = fc.film_id)
   join category c on (c.category_id=fc.category_id)
   where i.film_id in (:1 , :2 , :3 , :4 , :5 , :6 , :7 , :8 , :9 , :10 , :11 , :12 , :13 , :14 , :15 , :16 , :17 , :18 , :19 , :20 , :21 , :22 , :23 , :24 , :25 , :26 , :27 , :28 , :29 , :30 , :31 , :32 , :33 , :34 ) 
   and c.name= :35
   */
  private List<Film> getFilmInfo(EntityManager em, List<Inventory> inventories, Category category) {

    Query query = em.createNativeQuery(
      "select distinct f.* from film f join inventory i on (f.film_id=i.film_id) "
      + "join film_category fc on (f.film_id = fc.film_id) join category c on (c.category_id=fc.category_id)"
      + " where i.film_id in :f and c.name= :c",
      Film.class);
    query.setParameter("f", inventories.stream().map(
      i -> i.getFilm().getFilmId()).limit(1000).collect(Collectors.toList()));
    query.setParameter("c", category.getName());
    List<Film> films = query.getResultList();
    return films;

  }

  /*
   select film0_.FILM_ID as FILM_ID1_7_0_, film0_.DESCRIPTION as DESCRIPTION2_7_0_, film0_.LANGUAGE_ID as LANGUAGE_ID12_7_0_, film0_.LAST_UPDATE as LAST_UPDATE3_7_0_, film0_.LENGTH as LENGTH4_7_0_, film0_.ORIGINAL_LANGUAGE_ID as ORIGINAL_LANGUAGE13_7_0_, film0_.RATING as RATING5_7_0_, film0_.RELEASE_YEAR as RELEASE_YEAR6_7_0_, film0_.RENTAL_DURATION as RENTAL_DURATION7_7_0_, film0_.RENTAL_RATE as RENTAL_RATE8_7_0_, film0_.REPLACEMENT_COST as REPLACEMENT_COST9_7_0_, film0_.SPECIAL_FEATURES as SPECIAL_FEATURES10_7_0_, film0_.TITLE as TITLE11_7_0_,
   language1_.LANGUAGE_ID as LANGUAGE_ID1_0_1_, language1_.LAST_UPDATE as LAST_UPDATE2_0_1_, language1_.NAME as NAME3_0_1_, language2_.LANGUAGE_ID as LANGUAGE_ID1_0_2_, language2_.LAST_UPDATE as LAST_UPDATE2_0_2_, language2_.NAME as NAME3_0_2_
   from FILM film0_ 
   inner join "LANGUAGE" language1_ on film0_.LANGUAGE_ID=language1_.LANGUAGE_ID
   left outer join "LANGUAGE" language2_ on film0_.ORIGINAL_LANGUAGE_ID=language2_.LANGUAGE_ID 
   where film0_.FILM_ID=:1
   ### once per film!
   ### language_id fk not null - inner join
   ### original_language_id fk null - left outer
   */

  /*
   select inventory0_.INVENTORY_ID as INVENTORY_ID1_12_, inventory0_.FILM_ID as FILM_ID3_12_, inventory0_.LAST_UPDATE as LAST_UPDATE2_12_, inventory0_.STORE_ID as STORE_ID4_12_
   from INVENTORY inventory0_
   inner join RENTAL rentalset1_ on inventory0_.INVENTORY_ID=rentalset1_.INVENTORY_ID
   where rentalset1_.CUSTOMER_ID in (:1 )    
   */
  private List<Inventory> getInventories(EntityManager em, List<Customer> similarCustomers) {

    Query query = em.createQuery(
      "select i from Inventory i join i.rentalSet r where r.customerId in :ids");
    query.setParameter("ids", similarCustomers);
    List<Inventory> inventories = query.getResultList();
    return inventories;

  }

  /*
   select country0_.COUNTRY_ID as COUNTRY_ID1_5_, country0_.COUNTRY as COUNTRY2_5_, country0_.LAST_UPDATE as LAST_UPDATE3_5_
   from COUNTRY country0_ 
   inner join CITY cityset1_ on country0_.COUNTRY_ID=cityset1_.COUNTRY_ID
   inner join ADDRESS addressset2_ on cityset1_.CITY_ID=addressset2_.CITY_ID
   where addressset2_.ADDRESS_ID=70
   ### literal
   */
  private Country getCountry(EntityManager em, BigDecimal addressId) {

    return (Country) em.createQuery("select c from Country c join c.citySet"
      + " ci join ci.addressSet a where a.addressId = :adr_id")
      .setParameter("adr_id", addressId).getSingleResult();

  }
  
// all versions before 4
  //return (Country) em.createQuery(
  //    "select c from Country c join c.citySet ci join ci.addressSet a where a.addressId ="
  //  + addressId).getSingleResult();
  // version 4
  //return (Country) em.createQuery("select c from Country c join c.citySet ci join ci.addressSet a where a.addressId = :adr_id")
  //        .setParameter("adr_id", addressId).getSingleResult();

  /*
   select category0_.CATEGORY_ID as CATEGORY_ID1_3_0_, category0_.LAST_UPDATE as LAST_UPDATE2_3_0_, category0_.NAME as NAME3_3_0_
   from CATEGORY category0_
   where category0_.CATEGORY_ID=:1
   ### nur 15 Mal!
   */
  private Category getPreferredCategory(EntityManager em, Customer customer) {

    return em.find(Category.class,
      BigDecimal.valueOf(new Random().nextInt(CATEGORY_MAX_ID) + 1));

  }

  /*
   select customer0_.CUSTOMER_ID as CUSTOMER_ID1_6_, customer0_.ACTIVE as ACTIVE2_6_, customer0_.ADDRESS_ID as ADDRESS_ID8_6_, customer0_.CREATE_DATE as CREATE_DATE3_6_, customer0_.EMAIL as EMAIL4_6_, customer0_.FIRST_NAME as FIRST_NAME5_6_, customer0_.LAST_NAME as LAST_NAME6_6_, customer0_.LAST_UPDATE as LAST_UPDATE7_6_, customer0_.STORE_ID as STORE_ID9_6_
   from CUSTOMER customer0_ 
   inner join ADDRESS address1_ on customer0_.ADDRESS_ID=address1_.ADDRESS_ID 
   inner join CITY city2_ on address1_.CITY_ID=city2_.CITY_ID 
   inner join COUNTRY country3_ on city2_.COUNTRY_ID=country3_.COUNTRY_ID 
   where country3_.COUNTRY_ID=90     
   ### LITERAL!
   */
  private List<Customer> getSimilarCustomers(EntityManager em, Country country) {

    List<Customer> c = em.createQuery("select c from Customer c join c.address a "
      + "join a.city ci join ci.country co where co.countryId = :ctr_id")
      .setParameter("ctr_id", country.getCountryId())
      .getResultList();
    return c;

  }

  // all versions before 4
  //List<Customer> c = em.createQuery(
  //    "select c from Customer c join c.address a join a.city ci join ci.country co"
  //    + " where co.countryId =" + country.getCountryId()).getResultList();
  //return c;
  // version 4
  //List<Customer> c = em.createQuery("select c from Customer c join c.address a join a.city ci join ci.country co"
  //        + " where co.countryId = :ctr_id")
  //        .setParameter("ctr_id", country.getCountryId())
  //        .getResultList();
  /*
   select customer0_.CUSTOMER_ID as CUSTOMER_ID1_6_0_, customer0_.ACTIVE as ACTIVE2_6_0_, customer0_.ADDRESS_ID as ADDRESS_ID8_6_0_, customer0_.CREATE_DATE as CREATE_DATE3_6_0_, customer0_.EMAIL as EMAIL4_6_0_, customer0_.FIRST_NAME as FIRST_NAME5_6_0_, customer0_.LAST_NAME as LAST_NAME6_6_0_, customer0_.LAST_UPDATE as LAST_UPDATE7_6_0_, customer0_.STORE_ID as STORE_ID9_6_0_, 
   address1_.ADDRESS_ID as ADDRESS_ID1_2_1_, address1_.ADDRESS as ADDRESS2_2_1_, address1_.ADDRESS2 as ADDRESS3_2_1_, address1_.CITY_ID as CITY_ID8_2_1_, address1_.DISTRICT as DISTRICT4_2_1_, address1_.LAST_UPDATE as LAST_UPDATE5_2_1_, address1_.PHONE as PHONE6_2_1_, address1_.POSTAL_CODE as POSTAL_CODE7_2_1_, 
   city2_.CITY_ID as CITY_ID1_4_2_, city2_.CITY as CITY2_4_2_, city2_.COUNTRY_ID as COUNTRY_ID4_4_2_, city2_.LAST_UPDATE as LAST_UPDATE3_4_2_,
   country3_.COUNTRY_ID as COUNTRY_ID1_5_3_, country3_.COUNTRY as COUNTRY2_5_3_, country3_.LAST_UPDATE as LAST_UPDATE3_5_3_, 
   store4_.STORE_ID as STORE_ID1_16_4_, store4_.ADDRESS_ID as ADDRESS_ID3_16_4_, store4_.LAST_UPDATE as LAST_UPDATE2_16_4_, store4_.MANAGER_STAFF_ID as MANAGER_STAFF_ID4_16_4_, 
   address5_.ADDRESS_ID as ADDRESS_ID1_2_5_, address5_.ADDRESS as ADDRESS2_2_5_, address5_.ADDRESS2 as ADDRESS3_2_5_, address5_.CITY_ID as CITY_ID8_2_5_, address5_.DISTRICT as DISTRICT4_2_5_, address5_.LAST_UPDATE as LAST_UPDATE5_2_5_, address5_.PHONE as PHONE6_2_5_, address5_.POSTAL_CODE as POSTAL_CODE7_2_5_,
   staff6_.STAFF_ID as STAFF_ID1_15_6_, staff6_.ACTIVE as ACTIVE2_15_6_, staff6_.ADDRESS_ID as ADDRESS_ID10_15_6_, staff6_.EMAIL as EMAIL3_15_6_, staff6_.FIRST_NAME as FIRST_NAME4_15_6_, staff6_.LAST_NAME as LAST_NAME5_15_6_, staff6_.LAST_UPDATE as LAST_UPDATE6_15_6_, staff6_.PASSWORD as PASSWORD7_15_6_, staff6_.PICTURE as PICTURE8_15_6_, staff6_.STORE_ID as STORE_ID11_15_6_, staff6_.USERNAME as USERNAME9_15_6_, address7_.ADDRESS_ID as ADDRESS_ID1_2_7_,
   address7_.ADDRESS as ADDRESS2_2_7_, address7_.ADDRESS2 as ADDRESS3_2_7_, address7_.CITY_ID as CITY_ID8_2_7_, address7_.DISTRICT as DISTRICT4_2_7_, address7_.LAST_UPDATE as LAST_UPDATE5_2_7_, address7_.PHONE as PHONE6_2_7_, address7_.POSTAL_CODE as POSTAL_CODE7_2_7_,
   store8_.STORE_ID as STORE_ID1_16_8_, store8_.ADDRESS_ID as ADDRESS_ID3_16_8_, store8_.LAST_UPDATE as LAST_UPDATE2_16_8_, store8_.MANAGER_STAFF_ID as MANAGER_STAFF_ID4_16_8_
   from CUSTOMER customer0_ 
   inner join ADDRESS address1_ on customer0_.ADDRESS_ID=address1_.ADDRESS_ID ### from Customer
   inner join CITY city2_ on address1_.CITY_ID=city2_.CITY_ID                 ### from Address 
   inner join COUNTRY country3_ on city2_.COUNTRY_ID=country3_.COUNTRY_ID     ### from City
   inner join STORE store4_ on customer0_.STORE_ID=store4_.STORE_ID           ### from Customer
   inner join ADDRESS address5_ on store4_.ADDRESS_ID=address5_.ADDRESS_ID    ### from Store
   inner join STAFF staff6_ on store4_.MANAGER_STAFF_ID=staff6_.STAFF_ID      ### from Store
   inner join ADDRESS address7_ on staff6_.ADDRESS_ID=address7_.ADDRESS_ID    ### from Staff
   inner join STORE store8_ on staff6_.STORE_ID=store8_.STORE_ID              ### from Staff
   where customer0_.CUSTOMER_ID=:1
   ### nur 99 Mal
   */
  private Customer getCustomer(EntityManager em) {

    return (Customer) em.createQuery("select c from Customer c"
      + " where c.customerId = :cstid").setParameter("cstid",
        BigDecimal.valueOf(new Random().nextInt(CUSTOMER_MAX_ID) + 1))
      .getSingleResult();

  }
  
  // version 1: whole graph
  //return em.find(Customer.class,
  //    BigDecimal.valueOf(new Random().nextInt(CUSTOMER_MAX_ID) + 1));
  // version 2: just Customer, using a literal
  //return (Customer) em.createQuery("select c from Customer c where c.customerId = " +
  //        BigDecimal.valueOf(new Random().nextInt(CUSTOMER_MAX_ID) + 1))
  //        .getSingleResult();
  // version 3: bind variable
  //return (Customer) em.createQuery("select c from Customer c where c.customerId = :cstid").setParameter("cstid",
  //        BigDecimal.valueOf(new Random().nextInt(CUSTOMER_MAX_ID) + 1))
  //        .getSingleResult();

  /*
   select c1.customer_id, c1.store_id, c1.first_name, c1.last_name,c1.email, 
   c1.address_id, c1.active, c1.create_date, c1.last_update 
   from
   customer c1     join address a1 on c1.address_id=a1.address_id     join city 
   ci1 on ci1.city_id = a1.city_id     join country co1 on co1.country_id=
   ci1.country_id     join country co2 on co1.country_id = co2.country_id     
   join city ci2  on co2.country_id=ci2.country_id     join address a2 on 
   ci2.city_id=a2.city_id     WHERE a2.address_id = :1 
   */
  private List<Customer> getSimilarCustomers1Step(EntityManager em, BigDecimal addressId) {

    Query query = em.createNativeQuery("select c1.customer_id, c1.store_id, c1.first_name, c1.last_name,"
      + "c1.email, c1.address_id, c1.active, c1.create_date, c1.last_update "
      + "from customer c1"
      + "     join address a1 on c1.address_id=a1.address_id"
      + "     join city ci1 on ci1.city_id = a1.city_id"
      + "     join country co1 on co1.country_id=ci1.country_id"
      + "     join country co2 on co1.country_id = co2.country_id"
      + "     join city ci2  on co2.country_id=ci2.country_id"
      + "     join address a2 on ci2.city_id=a2.city_id"
      + "     WHERE a2.address_id = :a_id", Customer.class);

    query.setParameter("a_id", addressId);
    List<Customer> c = query.getResultList();
    return c;
  }

  /*
   select distinct f.* 
   from film f 
   join film_category fc on (f.film_id = fc.film_id) 
   join category c on (c.category_id=fc.category_id) 
   join inventory i on (f.film_id = i.film_id) 
   join rental r on r.inventory_id = i.inventory_id 
   where r.customer_id in (:1 , :2 , :3 , :4 , :5 , :6 , :7 , :8 , :9 , :10 , :11 , :12 , :13 , :14 , :15 ) 
   and c.name= :16 
   */
  private List<Film> getFilmInfo1Step(EntityManager em, List<Customer> similarCustomers, Category category) {

    Query query = em.createNativeQuery("select distinct f.* from film f "
      + "join film_category fc on (f.film_id = fc.film_id) join category c on (c.category_id=fc.category_id)"
      + " join inventory i on (f.film_id = i.film_id) join rental r on r.inventory_id = i.inventory_id"
      + " where r.customer_id in :s and c.name= :c", Film.class);
    query.setParameter("s", similarCustomers);
    query.setParameter("c", category.getName());
    List<Film> films = query.getResultList();
    return films;

  }

}
