package br.com.davsantos.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.davsantos.entities.Cidade;
import br.com.davsantos.entities.Cliente;
import br.com.davsantos.entities.Endereco;
import br.com.davsantos.entities.dto.ClienteDTO;
import br.com.davsantos.entities.dto.NewClienteDTO;
import br.com.davsantos.entities.enums.Perfil;
import br.com.davsantos.entities.enums.TipoCliente;
import br.com.davsantos.repositories.ClienteRepository;
import br.com.davsantos.repositories.EnderecoRepository;
import br.com.davsantos.security.User;
import br.com.davsantos.services.exceptions.AuthorizationException;
import br.com.davsantos.services.exceptions.DataIntegrityViolationException;
import br.com.davsantos.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private S3Service s3Service;

	public Cliente findById(Integer id) {
		User user = UserS.authenticate();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado!");
		}

		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado!  ID : " + id + ", TIPO : " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(Cliente cliente) {
		Cliente newCliente = findById(cliente.getId());
		updateData(newCliente, cliente);
		return clienteRepository.save(cliente);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			clienteRepository.deleteById(id);
		} catch (Exception e) {
			throw new DataIntegrityViolationException("Não é possível excluir um cliente que possuí pedidos");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest request = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(request);
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(NewClienteDTO newClienteDTO) {
		Cliente cliente = new Cliente(null, newClienteDTO.getNome(), newClienteDTO.getEmail(),
				newClienteDTO.getIdLegal(), TipoCliente.toEnum(newClienteDTO.getTipoCliente()),
				bCrypt.encode(newClienteDTO.getSenha()));
		Cidade cidade = new Cidade(newClienteDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, newClienteDTO.getLogradouro(), newClienteDTO.getNumero(),
				newClienteDTO.getComplemento(), newClienteDTO.getBairro(), newClienteDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(newClienteDTO.getTelefone());

		return cliente;
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		return s3Service.uploadFile(multipartFile);
	}
}
