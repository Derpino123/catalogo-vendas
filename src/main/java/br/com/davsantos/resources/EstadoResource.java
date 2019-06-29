package br.com.davsantos.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.davsantos.entities.Cidade;
import br.com.davsantos.entities.Estado;
import br.com.davsantos.entities.dto.CidadeDTO;
import br.com.davsantos.entities.dto.EstadoDTO;
import br.com.davsantos.services.CidadeService;
import br.com.davsantos.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades (@PathVariable Integer estadoId){
		List<Cidade> cidades = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cidadesDTO);
	}
}
