package learn.springframework.mvchibernate.dao;

import learn.springframework.mvchibernate.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // inject sessionFactory bean
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    // don't add @Transactional here -> it is handled by service layer
    public List<Customer> getCustomers() {

        Session session = sessionFactory.getCurrentSession();

        Query<Customer> query = session.createQuery("from Customer order by lastName desc", Customer.class);
        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public void save(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {

        Session session = sessionFactory.getCurrentSession();
        Customer customer = session.get(Customer.class, id);

        return customer;
    }

    @Override
    public void delete(int id) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId", id);
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String value) {

        Session session = sessionFactory.getCurrentSession();

        Query<Customer> query = session.createQuery("from Customer where lower(firstName) like :value or lower(lastName) like :value or lower(email) like :value", Customer.class);
        query.setParameter("value", value);

        List<Customer> customerSearchList = query.getResultList();

        return customerSearchList;
    }
}
