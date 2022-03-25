package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="usuario/{id}")
    public Usuario getUsuario(@PathVariable long id)
    {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Daniel");
        usuario.setApellido("Nava");
        usuario.setEmail("gnava@gmail.com");
        usuario.setTelefono("4775203699");
        return  usuario;
       // return List.of("sandia","melon","kiwi","manzana");
    }
    @RequestMapping(value="api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token)
    {
         if(!validarToken(token))
         {
             return null;
         }
         return usuarioDao.getUsuarios();
 
    }
    @RequestMapping(value="usuari4o")
    public Usuario editar()
    {
        Usuario usuario = new Usuario();
        usuario.setNombre("Daniel");
        usuario.setApellido("Nava");
        usuario.setEmail("gnava@gmail.com");
        usuario.setTelefono("4775203699");
        return  usuario;
        // return List.of("sandia","melon","kiwi","manzana");
    }

    @RequestMapping(value="api/usuarios/{id}",method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token,@PathVariable Long id)
    {
        if(!validarToken(token))
        {
            return;
        }
        usuarioDao.eliminar(id);
    }
 private boolean validarToken(String token){
     String usuadioid = jwtUtil.getKey(token);
        return usuadioid != null;
 }
    @RequestMapping(value="api/usuarios", method = RequestMethod.POST)
    public void RegistrarUsuario(@RequestBody Usuario usuario)
    {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash =  argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);

    }



}
