package Services;

import java.util.Objects;
import java.util.UUID;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

//import DAO.EmpressDAO;
import DAO.CandidatoDAO;
import Model.Candidato;

public class CandidatoService {

    private final CandidatoDAO candidatoDAO = new CandidatoDAO();

    public JSONObject cadastrarCandidato(JSONObject json) throws JsonProcessingException {
    	
    	try {
    		
	    	JSONObject response = new JSONObject();
	        Candidato candidato = new Candidato();
	        
	        candidato.setNome(json.getString("nome"));
	        candidato.setEmail(json.getString("email"));
	        candidato.setSenha(json.getString("senha"));

	        if (!Objects.isNull(candidatoDAO.getCandidatoByEmail(json.getString("email")))) {
	        	
	        	response.put("operacao", "cadastrarCandidato");
	        	response.put("status", 422);
	        	response.put("mensagem", "E-mail já cadastrado");
	        	
	        	return response;
	        }
	        
	        candidatoDAO.cadastrarCandidato(candidato);
        	
        	response.put("operacao", "cadastrarCandidato");
        	response.put("status", 201);
        	response.put("token", UUID.randomUUID());
        	
        	return response;
        	
        } catch (Exception e) {
        	
        	JSONObject response = new JSONObject();
        	
        	response.put("operacao", "cadastrarCandidato");
        	response.put("status", 404);
        	response.put("mensagem", "Erro ao cadastrar candidato");
        	e.printStackTrace();
        	
        	return response;
        	
        }
    }

    public JSONObject vizualizarCandidato(JSONObject node) throws JsonProcessingException {
    	JSONObject response = new JSONObject();
    	
        if (node.getString("token") != null) {
        	Candidato candidato = candidatoDAO.getCandidatoByEmail(node.getString("email"));
            
            
            if (Objects.isNull(candidato)) {
            	
            	response.put("mensagem", "E-mail não encontrado");
            	response.put("status", 404);
            	response.put("operacao", "visualizarCandidato");
                
                return response;
                
            }
            
            response.put("nome", candidato.getNome());
            response.put("senha", candidato.getSenha());
            response.put("status", 201);
            response.put("operacao", "visualizarCandidato");
            
            return response;
        }
        
        response.put("operacao", "visualizarCandidato");
    	response.put("status", 404);
    	response.put("mensagem", "Sessão inválida");
    	
    	return response;
    }

    public JSONObject atualizarCandidato(JSONObject node) throws JsonProcessingException {
    	JSONObject response = new JSONObject();
    	
        if (node.getString("token") != null) {
        	var candidato = candidatoDAO.getCandidatoByEmail(node.getString("email"));

            if (Objects.isNull(candidato)) {
            	
                response.put("mensagem", "E-mail não encontrado");
                response.put("status", 404);
                response.put("operacao", "atualizarCandidato");
                
                return response;
            }
            
            Candidato novoCandidato = new Candidato();
            
            novoCandidato.setNome(node.getString("nome"));
            novoCandidato.setEmail(node.getString("email"));
            novoCandidato.setSenha(node.getString("senha"));
            
            candidatoDAO.atualizarCandidato(node.getString("email"), novoCandidato);

            response.put("status", 201);
            response.put("operacao", "atualizarCandidato");
            
            return response;
        }
        
        response.put("operacao", "atualizarCandidato");
    	response.put("status", 404);
    	response.put("mensagem", "Sessão inválida");
    	
    	return response;
    }

    public JSONObject apagarCandidato(JSONObject node) throws JsonProcessingException {
    	JSONObject response = new JSONObject();
    	
    	if (node.getString("token") != null) {
            Candidato candidato = candidatoDAO.getCandidatoByEmail(node.getString("email"));

            if (Objects.isNull(candidato)) {
            	
            	response.put("mensagem", "E-mail não encontrado");
                response.put("status", 404);
                response.put("operacao", "atualizarCandidato");
                
                return response;
            }

            candidatoDAO.apagarCandidato(node.getString("email"));

            response.put("status", 201);
            response.put("operacao", "atualizarCandidato");
            
            return response;
            
    	}
    	
    	response.put("operacao", "apagarCandidato");
    	response.put("status", 404);
    	response.put("mensagem", "Sessão inválida");
    	
    	return response;
    }
}
