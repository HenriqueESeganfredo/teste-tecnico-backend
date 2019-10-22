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
import teste_tecnico_livelo.teste_back_end.utils.ValidationUtils;

@RestController
public class CidadeResource extends ValidationUtils {

	private Map<Integer, Cidade> cidades;

	// Método construtor, com criação de alguns objetos para teste de uso das
	// APIs
	public CidadeResource() {
		cidades = new HashMap<Integer, Cidade>();

		Cidade c1 = new Cidade(1, "Passo Fundo", "RS");
		Cidade c2 = new Cidade(2, "São Paulo", "SP");
		Cidade c3 = new Cidade(3, "Rio de Janeiro", "RJ");
		Cidade c4 = new Cidade(4, "Rio Grande", "RS");
		Cidade c5 = new Cidade(5, "Porto Alegre", "RS");

		cidades.put(1, c1);
		cidades.put(2, c2);
		cidades.put(3, c3);
		cidades.put(4, c4);
		cidades.put(5, c5);
	}

	// Lista todas as Cidades
	@RequestMapping(value = "/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<Cidade>> listar() {
		return new ResponseEntity<List<Cidade>>(new ArrayList<Cidade>(cidades.values()), HttpStatus.OK);
	}

	// Cadastra uma nova Cidade
	@RequestMapping(value = "/cidades/CadastrarCidade", method = RequestMethod.POST)
	public ResponseEntity<Cidade> cadastrarCidade(@RequestParam("id") Integer id, @RequestParam("nome") String nome,
			@RequestParam("estado") String estado) {

		Cidade cidade = new Cidade();
		// Verificação de parâmetros
		if (isNull(id) || isNullOrEmpty(nome) || isNullOrEmpty(estado)) {
			return new ResponseEntity<Cidade>(HttpStatus.BAD_REQUEST);
		} else {
			cidade = findById(id);
			if (isNull(cidade.getId())) {
				cidade = new Cidade(id, nome, estado);
				cidades.put(id, cidade);
			} else {
				return new ResponseEntity<Cidade>(HttpStatus.CONFLICT);
			}
		}

		return new ResponseEntity<Cidade>(cidade, HttpStatus.OK);
	}

	// Remove uma cidade
	@RequestMapping(value = "/cidades/RemoverCidade", method = RequestMethod.DELETE)
	public ResponseEntity<Cidade> removerCidade(@RequestParam("id") Integer id) {
		Cidade cidade = cidades.remove(id);

		if (isNull(cidade)) {
			return new ResponseEntity<Cidade>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cidade>(cidade, HttpStatus.NO_CONTENT);
	}

	// Busca uma ou mais cidades pelo nome
	@RequestMapping(value = "/cidades/BuscarPorNome", method = RequestMethod.GET)
	public ResponseEntity<List<Cidade>> buscarPorNome(@RequestParam("nome") String nome) {
		List<Cidade> listCidade = findByNome(nome);

		if (isNullOrEmpty(listCidade)) {
			return new ResponseEntity<List<Cidade>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Cidade>>(listCidade, HttpStatus.OK);
	}

	// Busca uma ou mais cidades pelo estado
	@RequestMapping(value = "/cidades/BuscarPorEstado", method = RequestMethod.GET)
	public ResponseEntity<List<Cidade>> buscarPorEstado(@RequestParam("estado") String estado) {
		List<Cidade> listCidade = findByEstado(estado);

		if (isNullOrEmpty(listCidade)) {
			return new ResponseEntity<List<Cidade>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Cidade>>(listCidade, HttpStatus.OK);
	}

	// Métodos de busca
	public Cidade findById(Integer id) {
		Cidade cidade = new Cidade();
		List<Cidade> listCidades = new ArrayList<Cidade>(cidades.values());
		for (Cidade c : listCidades) {
			if (c.getId() == id) {
				cidade = new Cidade(c.getId(), c.getNome(), c.getEstado());
				return cidade;
			}
		}

		return cidade;
	}

	public List<Cidade> findByNome(String nome) {
		List<Cidade> listCidades = new ArrayList<Cidade>(cidades.values());
		List<Cidade> listRetorno = new ArrayList<Cidade>();
		for (Cidade c : listCidades) {
			if (c.getNome().equals(nome) || c.getNome().contains(nome)) {
				Cidade cidade = new Cidade(c.getId(), c.getNome(), c.getEstado());
				listRetorno.add(cidade);
			}
		}

		return listRetorno;
	}

	public List<Cidade> findByEstado(String estado) {
		List<Cidade> listCidades = new ArrayList<Cidade>(cidades.values());
		List<Cidade> listRetorno = new ArrayList<Cidade>();
		for (Cidade c : listCidades) {
			if (c.getEstado().equals(estado) || c.getEstado().contains(estado)) {
				Cidade cidade = new Cidade(c.getId(), c.getNome(), c.getEstado());
				listRetorno.add(cidade);
			}
		}

		return listRetorno;
	}

}
