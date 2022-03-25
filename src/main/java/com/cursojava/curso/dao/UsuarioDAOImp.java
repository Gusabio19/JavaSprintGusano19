package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //hace referencia a qe podra hacer al repositorio de base de datos
@Transactional //como hacer las consultas a la base de datos
public class UsuarioDAOImp implements  UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query= "FROM Usuario";
        List<Usuario>  resultados  = entityManager.createQuery(query).getResultList();
        return resultados;
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario verificarEmailPassword(Usuario usuario)
    {
        String query= "FROM Usuario WHERE email = :email";
        List<Usuario> lista  =  entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if(lista.isEmpty()){
            return null;
        }

        String pass = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //System.out.println("sesion-->"+argon2.verify(pass,usuario.getPassword()));
        if( argon2.verify(pass,usuario.getPassword()))
        {
            return lista.get(0);
        }
        return null;
    }
}
