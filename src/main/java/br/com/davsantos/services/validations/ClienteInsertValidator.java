package br.com.davsantos.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.davsantos.entities.dto.NewClienteDTO;
import br.com.davsantos.entities.enums.TipoCliente;
import br.com.davsantos.resources.exceptions.FieldMessage;
import br.com.davsantos.services.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, NewClienteDTO> {

	@Override
	public void initialize(ClienteInsert anotation) {
	}

	@Override
	public boolean isValid(NewClienteDTO newClienteDTO, ConstraintValidatorContext context) {

		List<FieldMessage> lista = new ArrayList<>();
		
		if(newClienteDTO.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(newClienteDTO.getIdLegal())) {
			lista.add(new FieldMessage("IdLegal", "CPF inválido"));
		}
		
		if(newClienteDTO.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(newClienteDTO.getIdLegal())) {
			lista.add(new FieldMessage("IdLegal", "CNPJ inválido"));
		}

		for (FieldMessage e : lista) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return lista.isEmpty();
	}
}