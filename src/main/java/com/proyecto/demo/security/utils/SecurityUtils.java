package com.proyecto.demo.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.model.entity.User;

import lombok.Getter;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Service
public class SecurityUtils {

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No hay usuario autenticado");
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof User user) {
            return user;
        }
        throw new RuntimeException("Usuario no encontrado en el contexto de seguridad");
    }

    public Almacen getCurrentAlmacen() {
        User user = getCurrentUser();
        Almacen almacen = user.getAlmacenAsignado();
        if (almacen == null) {
            throw new RuntimeException("El usuario no tiene un almacén asignado");
        }
        return almacen;
    }

    public boolean isAdmin() {
        User user = getCurrentUser();
        return user.getRoles().stream()
                .anyMatch(role -> "ADMIN".equals(role.getName()));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
