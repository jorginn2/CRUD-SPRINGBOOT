package br.com.springboot.curso_jdev_treinamento.controllers;

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

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired // injeção de dependência
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	/*
	 * @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String greetingText(@PathVariable
	 * String name) { return "Olá " + name +
	 * ", você está no curso JDEV treinamento Spring."; }
	 * 
	 * @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String metodoRetornoHW(@PathVariable
	 * String nome) {
	 * 
	 * Usuario usuario = new Usuario(); usuario.setNome(nome);
	 * usuarioRepository.save(usuario);
	 * 
	 * return "Olá mundo " + nome; }
	 */

	@GetMapping(value = "listaTodos")
	@ResponseBody // retorna os dados para o corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario() { // busca todos os usuários do BD

		List<Usuario> usuarios = usuarioRepository.findAll(); // faz a consulta no BD

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PostMapping(value = "salvar") // método post para salvar usuários no BD
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "deletar") // método delete para deletar o usuário por ID
	@ResponseBody
	public ResponseEntity<String> deletar(@RequestParam Long iduser) {

		usuarioRepository.deleteById(iduser);
		return new ResponseEntity<String>("User deletado com sucesso.", HttpStatus.OK);
	}

	@GetMapping(value = "buscarUserId") // método get para buscar usuário por ID
	@ResponseBody
	public ResponseEntity<Usuario> buscarUser(@RequestParam(name = "iduser") Long iduser) {

		Usuario usuario = usuarioRepository.findById(iduser).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PutMapping(value = "atualizar") // método put para atualizar usuários salvos no BD
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

		if (usuario.getId() == null) {
			return new ResponseEntity<String>("É necessário um ID para atualizar!", HttpStatus.UNAUTHORIZED);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario); // atualiza e roda diretamente no BD
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@GetMapping(value = "buscarPorNome") // metodo get para buscar usuario por nome ou parte do nome
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String nome) {

		List<Usuario> usuario = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);

		/*
		 * -trim- usado para não aceitar espaços na requisição -upper- traz tanto
		 * maiúsculo quanto minúsculo
		 */
	}
}
