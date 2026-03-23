package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.RoleDto;
import com.proyecto.demo.mapper.RoleMapper;
import com.proyecto.demo.model.entity.Role;
import com.proyecto.demo.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto);
    }

    @Override
    public Optional<RoleDto> findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleMapper::toDto);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        Role saved = roleRepository.save(role);
        return roleMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isPresent()) {
            Role role = roleOpt.get();
            role.setEstado("AN");
            roleRepository.save(role);
        }
    }

    @Override
    public Page<RoleDto> findAllPageable(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(roleMapper::toDto);
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public Optional<Role> findEntityById(Long id) {
        return roleRepository.findById(id);
    }
}
