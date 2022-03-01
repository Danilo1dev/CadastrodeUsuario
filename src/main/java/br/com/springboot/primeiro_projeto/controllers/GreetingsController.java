package br.com.springboot.primeiro_projeto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.primeiro_projeto.model.Usuario;
import br.com.springboot.primeiro_projeto.repository.UsuarioRepository;

@RestController
public class GreetingsController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/*
	 * @RequestMapping(value = "/{name}", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String greetingText(@PathVariable
	 * String name) { return "Teste do Crud " + name + "!";
	 */

	/* Exemplo de URL abaixo - Seguir sempre o mesmo metodo(Ficar atento) */
	@RequestMapping(value = "/crud/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String metodocrud2(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario); /* grava no banco de dados */
		return "Crud feito: " + nome + "!";
	}

	@GetMapping(value = "listatodos") /* GET = Visualizar dados */
	@ResponseBody /* Retorna os dados dos usuarios */
	public ResponseEntity<List<Usuario>> listaUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll();/* Faz a consulta no banco de dados */

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PostMapping(value = "salvar") /* mapea a url / POST = para salvar */
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { /* receber os dados para salvar */
		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "atualizar") /* mapea a url / PUT = para atualizar */
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { /* receber os dados para atualizar */
		
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("ID não foi informado para atualização", HttpStatus.OK);	
		}
		
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "delete") /* mapea a url / DELETE = para deletar */
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* receber os dados para salvar */
		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("Deletado com Sucesso", HttpStatus.OK);
	}

	@GetMapping(value = "buscaruserid") /* mapea a url */
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) { /* Busca por ID */
		Usuario usuario = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@GetMapping(value = "buscarpornome") /* mapea a url / tem que fazer metodo no Repository*/
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { /* Busca por nome */
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());/*ignora=(espaço e se maiuscula)*/

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK); /*Atenção *Lista de usuario <List<Usuario>>*/
	}
}
