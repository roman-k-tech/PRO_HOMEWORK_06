import org.junit.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class DishTest
{
    private static final String SETTINGS = "SETTINGS";

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @org.junit.Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(SETTINGS);
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(new Dish("Juice", 20, 50, true));
        entityManager.persist(new Dish("Potato", 30, 100, true));
        entityManager.persist(new Dish("Meat", 50, 150, true));
        entityManager.persist(new Dish("Fish", 10, 80, false));
        entityManager.persist(new Dish("Salat", 30, 50, false));
        entityManager.persist(new Dish("Soup", 35, 150, false));
        entityManager.persist(new Dish("Water", 1, 20, false));
        entityManager.persist(new Dish("Bread", 3, 10, false));
        transaction.commit();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void insertToBD() throws Exception
    {
        Dish bread = new Dish("TestBread", 0, 0, false);
        transaction.begin();
        entityManager.persist(bread);
        transaction.commit();
        assertTrue(entityManager.contains(bread));

        Query query = entityManager.createQuery("SELECT testDish FROM Dish testDish WHERE testDish.id=:id", Dish.class);
        query.setParameter("id", bread.getId());
        Dish testDish = (Dish) query.getSingleResult();
        assertEquals(bread, testDish);
    }

    @Test
    public void deleteFromBD() throws Exception {
        Dish testDish = new Dish("Test Dish", 0, 0, false);
        transaction.begin();
        entityManager.persist(testDish);
        transaction.commit();
        Query query = entityManager.createQuery("SELECT testDish1 FROM Dish testDish1 WHERE testDish1.id=:id", Dish.class);
        query.setParameter("id", testDish.getId());
        Dish resultDish = null;
        try {
            resultDish = (Dish) query.getSingleResult();
        }
        catch (NoResultException e) { System.out.println(e.getLocalizedMessage()); }
        assertNotNull(resultDish);

        transaction.begin();
        entityManager.remove(testDish);
        transaction.commit();
        resultDish = null;
        try {
            resultDish = (Dish) query.getSingleResult();
        }
        catch (NoResultException e) { System.out.println(e.getLocalizedMessage()); }
        assertNull(resultDish);
    }

    @Test
    public void selectBy() throws Exception
    {
        TypedQuery<Dish> query = entityManager.createQuery(
                "SELECT dish FROM Dish dish WHERE dish.price>=:priceMin AND dish.price<=:priceMax", Dish.class);
        query.setParameter("priceMin", 0);
        query.setParameter("priceMax", 35);
        List<Dish> dishes = query.getResultList();
        assertFalse(dishes.isEmpty());

        for (Dish dish : dishes)
        {
            assertTrue(dish.getPrice() >= 0 && dish.getPrice() <= 35);
            System.out.println(dish.toString());
        }
    }

    @Test
    public void selectDiscount() throws Exception
    {
        TypedQuery<Dish> typedQuery = entityManager.createQuery(
                "SELECT dish FROM Dish dish WHERE dish.discount=:exists", Dish.class);
        typedQuery.setParameter("exists", true);

        List<Dish> dishes = typedQuery.getResultList();
        assertFalse(dishes.isEmpty());

        for (Dish dish : dishes)
        {
            assertTrue(dish.isDiscount());
            System.out.println(dish.toString());
        }
    }
}