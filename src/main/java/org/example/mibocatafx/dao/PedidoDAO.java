package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
import javafx.scene.Cursor;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.service.CursoService;
import org.example.mibocatafx.util.HibernateConnection;
import org.example.mibocatafx.util.TipoBocadillo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class PedidoDAO {
    private final CursoService cursoService = new CursoService();

    public void save(Pedido pedido) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            if (pedido.getId() == 0) {
                session.persist(pedido);
                System.out.println("persist usado");
            } else {
                session.merge(pedido);
                System.out.println("merge usado");
            }

            tx.commit();
        } catch (HibernateException e) {
            System.out.println("Error al insertar el pedido.");
        }
    }

    public boolean tienePedido(Alumno alumno) {
        LocalDate diaHoy = LocalDate.now();
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            List<Pedido> pedidos = session.createQuery("from Pedido p where p.alumno = :alumno and p.fecha = :fecha")
                    .setParameter("alumno", alumno)
                    .setParameter("fecha", diaHoy)
                    .getResultList();

            return !pedidos.isEmpty();
        } catch (NoResultException e) {
            return false;
        }
    }

    public Pedido getPedidoHoy(Alumno alumno) {
        LocalDate diaHoy = LocalDate.now();
//        LocalDate diaHoy = LocalDate.of(2025, 2, 13);

        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Pedido p where p.alumno = :alumno and p.fecha = :fecha");
            query.setParameter("alumno", alumno);
            query.setParameter("fecha", diaHoy);

            if (query.getSingleResult() != null) {
                return (Pedido) query.getSingleResult();
            }
        } catch (NoResultException e) {
            System.out.println("No se encontró ningún pedido de este alumno con la fecha de hoy");
        }

        return null;
    }

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Pedido p WHERE true");

            if (filtros != null) {
                for (String key : filtros.keySet()) {
                    switch (key) {
                        case "nombre" -> hql.append(" AND p.alumno.nombre like :").append(key);
                        case "tipo" -> hql.append(" AND p.bocadillo.tipo = :").append(key);
                        case "curso" -> hql.append(" AND p.alumno.curso = :").append(key);
                        case "fecha" -> hql.append(" AND p.fecha = :").append(key);
                    }
                }
            }
            hql.append(" ORDER BY CASE WHEN p.fechaRetirada IS NULL THEN 0 ELSE 1 END, p.fecha DESC");

            Query<Pedido> query = session.createQuery(hql.toString(), Pedido.class);

            if (filtros != null) {
                for (HashMap.Entry<String, String> filtro : filtros.entrySet()) {
                    switch (filtro.getKey()) {
                        case "nombre" -> query.setParameter(filtro.getKey(), "%" + filtro.getValue() + "%");
                        case "tipo" ->
                                query.setParameter(filtro.getKey(), TipoBocadillo.valueOf(TipoBocadillo.class, filtro.getValue()));
                        case "curso" ->
                                query.setParameter(filtro.getKey(), cursoService.getCursoByName(filtro.getValue()));
                        case "fecha" -> query.setParameter(filtro.getKey(), LocalDate.parse(filtro.getValue()));
                    }
                }
            }


            query.setFirstResult((page - 1) * offset);
            query.setMaxResults(offset);

            return query.getResultList();
        }
    }

    public long countPedidos(HashMap<String, String> filtros) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM Pedido p WHERE true");

            if (filtros != null) {
                for (String key : filtros.keySet()) {
                    switch (key) {
                        case "nombre" -> hql.append(" AND p.alumno.nombre = :").append(key);
                        case "tipo" -> hql.append(" AND p.bocadillo.tipo = :").append(key);
                        case "curso" -> hql.append(" AND p.alumno.curso = :").append(key);
                        case "fecha" -> hql.append(" AND p.fecha = :").append(key);
                    }
                }
            }

            Query<Long> query = session.createQuery(hql.toString(), Long.class);

            if (filtros != null) {
                for (HashMap.Entry<String, String> filtro : filtros.entrySet()) {
                    switch (filtro.getKey()) {
                        case "nombre" -> query.setParameter(filtro.getKey(), filtro.getValue());
                        case "tipo" -> query.setParameter(filtro.getKey(), TipoBocadillo.valueOf(TipoBocadillo.class, filtro.getValue()));
                        case "curso" -> query.setParameter(filtro.getKey(), cursoService.getCursoByName(filtro.getValue()));
                        case "fecha" -> query.setParameter(filtro.getKey(), LocalDate.parse(filtro.getValue()));
                    }
                }
            }


            System.out.println("query" + hql);
            System.out.println(query.getSingleResult().toString());


            return query.getSingleResult();
        }
    }

    public void updateFechaRetirada(Pedido pedido) {
        Transaction tx;
        LocalDateTime fechaRetirada = LocalDateTime.now();

        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.createQuery("update Pedido p set p.fechaRetirada = :fechaRetirada where p.id = :pedido");
            query.setParameter("fechaRetirada", fechaRetirada);
            query.setParameter("pedido", pedido.getId());

            query.executeUpdate();

            tx.commit();
        }
    }
}
