package com.proyecto.demo.security.service;

import java.util.List;
import java.util.stream.Collectors;

import com.proyecto.demo.dto.AlmacenDto;
import com.proyecto.demo.dto.AuthResponse;
import com.proyecto.demo.dto.LoginRequest;
import com.proyecto.demo.dto.RegisterRequest;
import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.model.entity.User;
import com.proyecto.demo.repository.AlmacenRepository;
import com.proyecto.demo.repository.UserRepository;
import com.proyecto.demo.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AlmacenRepository almacenRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        
        // Obtener roles del usuario
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        
        // Obtener información del almacén
        Long almacenId = null;
        String almacenNombre = null;
        if (user.getAlmacenAsignado() != null) {
            almacenId = user.getAlmacenAsignado().getId();
            almacenNombre = user.getAlmacenAsignado().getNombre();
        }
        
        var jwtToken = jwtUtils.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .nombreCompleto(user.getNombre() + " " + user.getApellido())
                .roles(roles)
                .almacen(AlmacenDto.builder()
                        .id(almacenId)
                        .nombre(almacenNombre)
                        .build())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya existe");
        }
        
        Almacen almacen = null;
        System.out.println("Almacen ID en el request: " + request.getAlmacenId());
        if (request.getAlmacenId() != null) {
            almacen = almacenRepository.findById(request.getAlmacenId())
                    .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + request.getAlmacenId()));
        }
        
        // Separar nombre y apellido del nombreCompleto
        String nombre = "";
        String apellido = "";
        if (request.getNombreCompleto() != null && !request.getNombreCompleto().isEmpty()) {
            String[] partes = request.getNombreCompleto().split(" ", 2);
            nombre = partes[0];
            apellido = partes.length > 1 ? partes[1] : "";
        }
        
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .nombre(nombre)
                .apellido(apellido)
                .almacenAsignado(almacen)
                .build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        
        var jwtToken = jwtUtils.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .nombreCompleto(user.getNombre() + " " + user.getApellido())
                .build();
    }
}
