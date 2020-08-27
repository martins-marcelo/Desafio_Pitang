package com.marcelomartins.desafiopitang.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marcelomartins.desafiopitang.model.Usuario;
import com.marcelomartins.desafiopitang.repository.UsuarioRepository;
import com.marcelomartins.desafiopitang.service.exceptions.DataIntegrityException;
import com.marcelomartins.desafiopitang.service.exceptions.ObjectNotFoundException;




@Service
public class UsuarioService {
	EntityManagerFactory emf;
	EntityManager em;
	public UsuarioService() {
		emf = Persistence.createEntityManagerFactory("pitang");
		em = emf.createEntityManager();
	}
	
	@Autowired
	private UsuarioRepository repository;
	
	
	public Usuario find(Integer codUser) {
		Optional<Usuario> obj = repository.findById(codUser);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Usuário não encontrado: "+codUser));
	}
	
	public List<Usuario> findAll(){
		return repository.findAll();
	}
	
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Usuario update(Usuario obj) {
		Usuario temp = find(obj.getId());
		updateData(temp, obj);
		return repository.save(temp);
	}
	
	public void delete(Integer id){
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException dive) {
			throw new DataIntegrityException("Não é possivel excluir um usuario com telefones associados");
		}

	}
	
	
	private void updateData(Usuario temp, Usuario obj){
		temp.setNome(obj.getNome());
		temp.setEmail(obj.getEmail());
		temp.setSenha(obj.getSenha());
		temp.setTelefones(obj.getTelefones());
	}
	
}
