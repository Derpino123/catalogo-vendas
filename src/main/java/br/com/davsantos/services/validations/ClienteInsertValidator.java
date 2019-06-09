package br.com.davsantos.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.davsantos.entities.Cliente;
import br.com.davsantos.entities.dto.NewClienteDTO;
import br.com.davsantos.entities.enums.TipoCliente;
import br.com.davsantos.repositories.ClienteRepository;
import br.com.davsantos.resources.exceptions.FieldMessage;
import br.com.davsantos.services.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, NewClienteDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(NewClienteDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo())
				&& !BR.isValidCPF(objDto.getIdLegal())) {
			list.add(new FieldMessage("idLegal", "CPF inválido"));
		}

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo())
				&& !BR.isValidCNPJ(objDto.getIdLegal())) {
			list.add(new FieldMessage("idLegal", "CNPJ inválido"));
		}
		
		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
