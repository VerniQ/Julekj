package me.verni.application.database;

import me.verni.application.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class HealthEntryDao {

    public void saveEntry(HealthEntry entry) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public HealthEntry getEntryById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(HealthEntry.class, id);
        }
    }

    public void updateEntry(HealthEntry entry) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteEntry(int id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            HealthEntry entry = session.get(HealthEntry.class, id);
            if(entry != null){
                session.remove(entry);
            }
            transaction.commit();
        }
        catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<HealthEntry> getAllEntries() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM HealthEntry", HealthEntry.class).list();
        }
    }
}