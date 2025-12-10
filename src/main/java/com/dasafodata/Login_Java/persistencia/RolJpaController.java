package com.dasafodata.Login_Java.persistencia;

import com.dasafodata.Login_Java.logica.Rol;
import com.dasafodata.Login_Java.logica.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RolJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public RolJpaController() {
        emf = Persistence.createEntityManagerFactory("LoginPU");;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListaUsuarios() == null) {
            rol.setListaUsuarios(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            // Nota: No persistimos los usuarios aquí porque Usuario es el dueño de la relación (MappedBy)
            // Solo guardamos el Rol. Los usuarios se deben crear/editar asignándoles este rol.
            em.persist(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Rol persistentRol = em.find(Rol.class, rol.getId());
            
            // Mantenemos la lista de usuarios actualizada solo en memoria, 
            // la BD se actualiza desde el lado de Usuario
            List<Usuario> listaUsuariosOld = persistentRol.getListaUsuarios();
            List<Usuario> listaUsuariosNew = rol.getListaUsuarios();
            List<Usuario> attachedListaUsuariosNew = new ArrayList<Usuario>();
            
            for (Usuario listaUsuariosNewUsuarioToAttach : listaUsuariosNew) {
                listaUsuariosNewUsuarioToAttach = em.getReference(listaUsuariosNewUsuarioToAttach.getClass(), listaUsuariosNewUsuarioToAttach.getId());
                attachedListaUsuariosNew.add(listaUsuariosNewUsuarioToAttach);
            }
            listaUsuariosNew = attachedListaUsuariosNew;
            rol.setListaUsuarios(listaUsuariosNew);
            
            rol = em.merge(rol);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rol.getId();
                if (findRol(id) == null) {
                    throw new EntityNotFoundException("El rol con id " + id + " no existe.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getId();
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("El rol con id " + id + " no existe.");
            }
            
            // IMPORTANTE: Manejo de hijos antes de borrar el padre
            List<Usuario> listaUsuarios = rol.getListaUsuarios();
            for (Usuario usuario : listaUsuarios) {
                // Opción A: Desvincular usuario (Poner Rol en null)
                usuario.setUnRol(null);
                em.merge(usuario);
                
                // Opción B: Eliminar usuario (Cascada manual) -> em.remove(usuario);
            }
            
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() { return findRolEntities(true, -1, -1); }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rol findRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}