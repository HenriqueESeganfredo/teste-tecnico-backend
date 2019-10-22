package teste_tecnico_livelo.teste_back_end.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import teste_tecnico_livelo.teste_back_end.model.Cidade;
import teste_tecnico_livelo.teste_back_end.model.Cliente;
import teste_tecnico_livelo.teste_back_end.utils.ValidationUtils;

@RestController
public class ClienteResource extends ValidationUtils {
	private Map<Integer, Cliente> clientes;
	private CidadeResource cidadeResource = new CidadeResource();

	// Método construtor, com criação de alguns objetos para teste de uso das
	// APIs
	public ClienteResource() {
		clientes = new HashMap<Integer, Cliente>();

		Cliente c1 = new Cliente(1, "Henrique Ernesto Seganfredo", "M", "24/01/1994", 25, cidadeResource.findById(1));
		Cliente c2 = new Cliente(2, "Cliente Teste", "F", "29/03/1997", 22, cidadeResource.findById(4));
		Cliente c3 = new Cliente(3, "Cliente Teste 2", "M", "09/02/1984", 22, cidadeResource.findById(2));

		clientes.put(1, c1);
		clientes.put(2, c2);
		clientes.put(3, c3);
	}

	// Lista todos os Clientes
	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> listar() {
		return new ResponseEntity<List<Cliente>>(new ArrayList<Cliente>(clientes.values()), HttpStatus.OK);
	}

	// Cadastra um novo Cliente
	@RequestMapping(value = "/clientes/CadastrarCliente", method = RequestMethod.POST)
	public ResponseEntity<Cliente> cadastrarCliente(@RequestParam("id") Integer id, @RequestParam("nome") String nome,
			@RequestParam("sexo") String sexo, @RequestParam("dataNascimento") String dataNascimento,
			@RequestParam("idade") Integer idade, @RequestParam("idCidade") Integer idCidade) {
		Cliente cliente = new Cliente();
		Cidade cidade = new Cidade();
		// Verificação de parâmetros

		if (isNull(id) || isNullOrEmpty(nome) || isNullOrEmpty(sexo) || isNullOrEmpty(dataNascimento) || isNull(idade)
				|| isNull(idCidade)) {
			return new ResponseEntity<Cliente>(cliente, HttpStatus.BAD_REQUEST);
		} else {
			cidade = cidadeResource.findById(idCidade);
			if (isNull(cidade)) {
				return new ResponseEntity<Cliente>(cliente, HttpStatus.BAD_REQUEST);
			}
			// verifica se já existe um cliente com o ID informado
			cliente = findById(id);
			if (isNull(cliente.getId())) {
				// Se todos os parâmetros estiverem ok, adiciona na lista
				cliente = new Cliente(id, nome, sexo, dataNascimento, idade, cidade);
				clientes.put(id, cliente);
			} else {
				return new ResponseEntity<Cliente>(HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	// Remove um Cliente
	@RequestMapping(value = "/clientes/RemoverCliente", method = RequestMethod.DELETE)
	public ResponseEntity<Cliente> removerCliente(@RequestParam("id") Integer id) {
		Cliente cliente = clientes.remove(id);

		if (isNull(cliente)) {
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.NO_CONTENT);
	}

	// Busca um Cliente pelo ID
	@RequestMapping(value = "/clientes/BuscarPorId", method = RequestMethod.GET)
	public ResponseEntity<Cliente> buscarPorId(@RequestParam("id") Integer id) {
		Cliente cliente = findById(id);

		if (isNull(id)) {
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	// Busca um ou mais Clientes pelo Nome
	@RequestMapping(value = "/clientes/BuscarPorNome", method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam("nome") String nome) {
		List<Cliente> listClientes = findByNome(nome);

		if (isNullOrEmpty(listClientes)) {
			return new ResponseEntity<List<Cliente>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Cliente>>(listClientes, HttpStatus.OK);
	}

	@RequestMapping(value = "/clientes/AlterarNome", method = RequestMethod.PATCH)
	public ResponseEntity<Cliente> alterarNome(@RequestParam("id") Integer id,
			@RequestParam("novoNome") String novoNome) {
		Cliente cliente = findById(id);
		if (isNull(cliente)) {
			return new ResponseEntity<Cliente>(cliente, HttpStatus.NOT_FOUND);
		} else {
			cliente.setNome(novoNome);
			clientes.put(id, cliente);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	// Métodos de busca
	public Cliente findById(Integer id) {
		Cliente cliente = new Cliente();
		List<Cliente> listClientes = new ArrayList<Cliente>(clientes.values());
		for (Cliente c : listClientes) {
			if (c.getId() == id) {
				cliente = new Cliente(c.getId(), c.getNome(), c.getSexo(), c.getDataNascimento(), c.getIdade(), c.getCidade());
				return cliente;
			}
		}

		return cliente;
	}

	public List<Cliente> findByNome(String nome) {
		List<Cliente> listClientes = new ArrayList<Cliente>(clientes.values());
		List<Cliente> listRetorno = new ArrayList<Cliente>();
		for (Cliente c : listClientes) {
			if (c.getNome().equals(nome) || c.getNome().contains(nome)) {
				Cliente cliente = new Cliente(c.getId(), c.getNome(), c.getSexo(), c.getDataNascimento(), c.getIdade(), c.getCidade());
				listRetorno.add(cliente);
			}
		}

		return listRetorno;
	}
}
