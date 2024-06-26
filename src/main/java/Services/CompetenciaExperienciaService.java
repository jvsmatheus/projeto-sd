package Services;

import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import DAO.CompetenciaExperienciaDAO;
import Model.CompetenciaExperiencia;

public class CompetenciaExperienciaService {

	private final CompetenciaExperienciaDAO competenciaExperienciaDAO = new CompetenciaExperienciaDAO();

	public JSONObject cadastrarCompetenciaExperiencia(JSONObject json) throws JsonProcessingException {
	    try {
	        JSONObject response = new JSONObject();
	        String email = json.getString("email");

	        // Itera sobre a lista de competencias e experiencias e cadastra cada uma
	        JSONArray competenciasExperiencias = json.getJSONArray("competenciaExperiencia");
	        for (int i = 0; i < competenciasExperiencias.length(); i++) {
	            JSONObject compExpJson = competenciasExperiencias.getJSONObject(i);
	            String competencia = compExpJson.getString("competencia");
	            int experiencia = compExpJson.getInt("experiencia");

	            // Verifica se a combinação de email e competência já foi cadastrada
	            if (competenciaExperienciaDAO.getCompetenciaExperienciaByEmailAndCompetencia(email, competencia) != null) {
	                response.put("operacao", "cadastrarCompetenciaExperiencia");
	                response.put("status", 422);
	                response.put("mensagem", "Competência '" + competencia + "' já cadastrada para o e-mail fornecido");

	                return response;
	            }

	            CompetenciaExperiencia competenciaExperiencia = new CompetenciaExperiencia();
	            competenciaExperiencia.setCompetencia(competencia);
	            competenciaExperiencia.setExperiencia(experiencia);
	            competenciaExperiencia.setEmail(email);

	            competenciaExperienciaDAO.cadastrarCompetenciaExperiencia(competenciaExperiencia);
	        }

	        response.put("operacao", "cadastrarCompetenciaExperiencia");
	        response.put("status", 201);
	        response.put("mensagem", "Competencia/Experiencia cadastrada com sucesso");

	        return response;

	    } catch (Exception e) {
	        JSONObject response = new JSONObject();

	        response.put("operacao", "cadastrarCompetenciaExperiencia");
	        response.put("status", 404);
	        response.put("mensagem", "Erro ao cadastrar competenciaExperiencia");
	        e.printStackTrace();

	        return response;
	    }
	}

	public JSONObject visualizarCompetenciaExperiencia(JSONObject node) throws JsonProcessingException {
	    JSONObject response = new JSONObject();

	    if (node.getString("token") != null) {
	        String email = node.getString("email");
	        List<CompetenciaExperiencia> competenciasExperiencias = competenciaExperienciaDAO.getCompetenciasExperienciasByEmail(email);

	        if (competenciasExperiencias.isEmpty()) {
	            response.put("mensagem", "E-mail não encontrado");
	            response.put("status", 404);
	            response.put("operacao", "visualizarCompetenciaExperiencia");
	            return response;
	        }

	        JSONArray competenciasArray = new JSONArray();
	        for (CompetenciaExperiencia ce : competenciasExperiencias) {
	            JSONObject compExpJson = new JSONObject();
	            compExpJson.put("competencia", ce.getCompetencia());
	            compExpJson.put("experiencia", ce.getExperiencia());
	            competenciasArray.put(compExpJson);
	        }

	        response.put("email", email);
	        response.put("competenciaExperiencia", competenciasArray);
	        response.put("status", 201);
	        response.put("operacao", "visualizarCompetenciaExperiencia");

	        return response;
	    }

	    response.put("operacao", "visualizarCompetenciaExperiencia");
	    response.put("status", 404);
	    response.put("mensagem", "Sessão inválida");

	    return response;
	}


	public JSONObject atualizarCompetenciaExperiencia(JSONObject node) throws JsonProcessingException {
	    JSONObject response = new JSONObject();

	    if (node.has("token") && node.getString("token") != null) {
	        String email = node.getString("email");
	        String token = node.getString("token");
	        JSONArray competenciasArray = node.getJSONArray("competenciaExperiencia");
	        
	        if (competenciasArray.isEmpty()) {
	        	response.put("mensagem", "Selecione a(s) competencia(s) a serem atualizda(s)");
                response.put("status", 422);
                response.put("operacao", "atualizarCompetenciaExperiencia");

                return response;
	        }

	        for (int i = 0; i < competenciasArray.length(); i++) {
	            JSONObject competenciaExperiencia = competenciasArray.getJSONObject(i);

	            String competencia = competenciaExperiencia.getString("competencia");
	            int experiencia = competenciaExperiencia.getInt("experiencia");

	            List<CompetenciaExperiencia> existingCompetencias = competenciaExperienciaDAO.getCompetenciaExperienciaByEmailList(email);
	            
	            boolean success = false;
//
	            for (CompetenciaExperiencia existingCompetencia : existingCompetencias) {
	                if (existingCompetencia.getCompetencia().equals(competencia)) {
	                    existingCompetencia.setExperiencia(experiencia);
	                    success = competenciaExperienciaDAO.atualizarCompetenciaExperiencia(existingCompetencia.getId(), existingCompetencia);
	                    break;
	                }
	            }
//
	            if (!success) {
	                response.put("mensagem", "Erro ao tentar atualizar '" + competencia + "'");
	                response.put("status", 422);
	                response.put("operacao", "atualizarCompetenciaExperiencia");

	                return response;
	            }
	        }

	        response.put("mensagem", "Competencias/Experiencias atualizadas com sucesso");
	        response.put("status", 201);
	        response.put("operacao", "atualizarCompetenciaExperiencia");

	        return response;
	    }

	    response.put("operacao", "atualizarCompetenciaExperiencia");
	    response.put("status", 422);
	    response.put("mensagem", "Sessão inválida");

	    return response;
	}

    public JSONObject apagarCompetenciaExperiencia(JSONObject node) throws JsonProcessingException {
    	JSONObject response = new JSONObject();

	    if (node.has("token") && node.getString("token") != null) {
	        String email = node.getString("email");
	        String token = node.getString("token");
	        JSONArray competenciasArray = node.getJSONArray("competenciaExperiencia");
	        
	        if (competenciasArray.isEmpty()) {
	        	response.put("mensagem", "Selecione a(s) competencia(s) a serem apagada(s)");
                response.put("status", 422);
                response.put("operacao", "apagarCompetenciaExperiencia");

                return response;
	        }

	        for (int i = 0; i < competenciasArray.length(); i++) {
	            JSONObject competenciaExperiencia = competenciasArray.getJSONObject(i);

	            String competencia = competenciaExperiencia.getString("competencia");
	            int experiencia = competenciaExperiencia.getInt("experiencia");

	            List<CompetenciaExperiencia> existingCompetencias = competenciaExperienciaDAO.getCompetenciaExperienciaByEmailList(email);
	            
	            boolean success = false;
//
	            for (CompetenciaExperiencia existingCompetencia : existingCompetencias) {
	                if (existingCompetencia.getCompetencia().equals(competencia)) {
	                    existingCompetencia.setExperiencia(experiencia);
	                    success = competenciaExperienciaDAO.apagarCompetenciaExperiencia(existingCompetencia.getId());
	                    break;
	                }
	            }
//
	            if (!success) {
	                response.put("mensagem", "Erro ao tentar apagar '" + competencia + "'");
	                response.put("status", 422);
	                response.put("operacao", "apagarCompetenciaExperiencia");

	                return response;
	            }
	        }

	        response.put("mensagem", "Competencia(s)/Experiencia(s) apagada(s) com sucesso");
	        response.put("status", 201);
	        response.put("operacao", "apagarCompetenciaExperiencia");

	        return response;
	    }

	    response.put("operacao", "apagarCompetenciaExperiencia");
	    response.put("status", 422);
	    response.put("mensagem", "Sessão inválida");

	    return response;
    }
}
