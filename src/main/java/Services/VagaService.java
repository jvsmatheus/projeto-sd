//package Services;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import DAO.EmpresaDAO;
//import DAO.VagaDAO;
//import Model.Empresa;
//import Model.Vaga;
//
//public class VagaService {
//
//	private final VagaDAO vagaDAO = new VagaDAO();
//	private final EmpresaDAO empresaDAO = new EmpresaDAO();
//
//
//	public JSONObject cadastrarVaga(JSONObject node) throws JsonProcessingException {
//	    JSONObject response = new JSONObject();
//	    try {
//	        // Extrai os dados do JSON
//	        String nome = node.getString("nome");
//	        String email = node.getString("email");
//	        double faixaSalarial = node.getDouble("faixaSalarial");
//	        String descricao = node.getString("descricao");
//	        String estado = node.getString("estado");
//	        JSONArray competenciasArray = node.getJSONArray("competencias");
//
//	        // Verifica se a vaga já existe (por exemplo, usando o nome e o email)
//	        if (vagaDAO.getVagaByNome(nome) != null) {
//	            response.put("operacao", "cadastrarVaga");
//	            response.put("status", 422);
//	            response.put("mensagem", "Vaga com o nome '" + nome + "' já cadastrada");
//
//	            return response;
//	        }
//
//	        // Cria uma nova instância de Vaga
//	        Vaga vaga = new Vaga();
//	        vaga.setNome(nome);
//	        vaga.setFaixaSalarial(faixaSalarial);
//	        vaga.setDescricao(descricao);
//	        vaga.setEstado(estado);
//	        // Adiciona as competências
//	        List<String> competencias = new ArrayList<>();
//	        for (int i = 0; i < competenciasArray.length(); i++) {
//	            competencias.add(competenciasArray.getString(i));
//	        }
//	        vaga.setCompetencias(competencias);
//
//	        // Obtém a empresa pelo email e define na vaga
//	        Empresa empresa = empresaDAO.getEmpresaByEmail(email);
//	        if (empresa == null) {
//	            response.put("operacao", "cadastrarVaga");
//	            response.put("status", 404);
//	            response.put("mensagem", "Empresa não encontrada para o email fornecido");
//	            return response;
//	        }
//	        vaga.setEmpresa(empresa);
//
//	        // Cadastra a vaga
//	        boolean sucesso = vagaDAO.cadastrarVaga(vaga);
//
//	        if (sucesso) {
//	            response.put("operacao", "cadastrarVaga");
//	            response.put("status", 201);
//	            response.put("mensagem", "Vaga cadastrada com sucesso");
//	        } else {
//	            response.put("operacao", "cadastrarVaga");
//	            response.put("status", 500);
//	            response.put("mensagem", "Erro ao cadastrar vaga");
//	        }
//
//	        return response;
//
//	    } catch (Exception e) {
//	        response.put("operacao", "cadastrarVaga");
//	        response.put("status", 500);
//	        response.put("mensagem", "Erro ao cadastrar vaga: " + e.getMessage());
//	        e.printStackTrace();
//	        return response;
//	    }
//	}
//
//
//	public JSONObject visualizarVaga(JSONObject node) throws JsonProcessingException {
//	    JSONObject response = new JSONObject();
//
//	    if (node.getString("token") != null) {
//	        String email = node.getString("email");
//	        List<Vaga> competenciasExperiencias = vagaDAO.getCompetenciasExperienciasByEmail(email);
//
//	        if (competenciasExperiencias.isEmpty()) {
//	            response.put("mensagem", "E-mail não encontrado");
//	            response.put("status", 404);
//	            response.put("operacao", "visualizarVaga");
//	            return response;
//	        }
//
//	        JSONArray competenciasArray = new JSONArray();
//	        for (Vaga ce : competenciasExperiencias) {
//	            JSONObject compExpJson = new JSONObject();
//	            compExpJson.put("competencia", ce.getCompetencia());
//	            compExpJson.put("experiencia", ce.getExperiencia());
//	            competenciasArray.put(compExpJson);
//	        }
//
//	        response.put("email", email);
//	        response.put("competenciaExperiencia", competenciasArray);
//	        response.put("status", 201);
//	        response.put("operacao", "visualizarVaga");
//
//	        return response;
//	    }
//
//	    response.put("operacao", "visualizarVaga");
//	    response.put("status", 404);
//	    response.put("mensagem", "Sessão inválida");
//
//	    return response;
//	}
////
////
////	public JSONObject atualizarVaga(JSONObject node) throws JsonProcessingException {
////	    JSONObject response = new JSONObject();
////
////	    if (node.has("token") && node.getString("token") != null) {
////	        String email = node.getString("email");
////	        String token = node.getString("token");
////	        JSONArray competenciasArray = node.getJSONArray("competenciaExperiencia");
////	        
////	        if (competenciasArray.isEmpty()) {
////	        	response.put("mensagem", "Selecione a(s) competencia(s) a serem atualizda(s)");
////                response.put("status", 422);
////                response.put("operacao", "atualizarVaga");
////
////                return response;
////	        }
////
////	        for (int i = 0; i < competenciasArray.length(); i++) {
////	            JSONObject competenciaExperiencia = competenciasArray.getJSONObject(i);
////
////	            String competencia = competenciaExperiencia.getString("competencia");
////	            int experiencia = competenciaExperiencia.getInt("experiencia");
////
////	            List<Vaga> existingCompetencias = vagaDAO.getVagaByEmailList(email);
////	            
////	            boolean success = false;
//////
////	            for (Vaga existingCompetencia : existingCompetencias) {
////	                if (existingCompetencia.getCompetencia().equals(competencia)) {
////	                    existingCompetencia.setExperiencia(experiencia);
////	                    success = vagaDAO.atualizarVaga(existingCompetencia.getId(), existingCompetencia);
////	                    break;
////	                }
////	            }
//////
////	            if (!success) {
////	                response.put("mensagem", "Erro ao tentar atualizar '" + competencia + "'");
////	                response.put("status", 422);
////	                response.put("operacao", "atualizarVaga");
////
////	                return response;
////	            }
////	        }
////
////	        response.put("mensagem", "Competencias/Experiencias atualizadas com sucesso");
////	        response.put("status", 201);
////	        response.put("operacao", "atualizarVaga");
////
////	        return response;
////	    }
////
////	    response.put("operacao", "atualizarVaga");
////	    response.put("status", 422);
////	    response.put("mensagem", "Sessão inválida");
////
////	    return response;
////	}
////
////    public JSONObject apagarVaga(JSONObject node) throws JsonProcessingException {
////    	JSONObject response = new JSONObject();
////
////	    if (node.has("token") && node.getString("token") != null) {
////	        String email = node.getString("email");
////	        String token = node.getString("token");
////	        JSONArray competenciasArray = node.getJSONArray("competenciaExperiencia");
////	        
////	        if (competenciasArray.isEmpty()) {
////	        	response.put("mensagem", "Selecione a(s) competencia(s) a serem apagada(s)");
////                response.put("status", 422);
////                response.put("operacao", "apagarVaga");
////
////                return response;
////	        }
////
////	        for (int i = 0; i < competenciasArray.length(); i++) {
////	            JSONObject competenciaExperiencia = competenciasArray.getJSONObject(i);
////
////	            String competencia = competenciaExperiencia.getString("competencia");
////	            int experiencia = competenciaExperiencia.getInt("experiencia");
////
////	            List<Vaga> existingCompetencias = vagaDAO.getVagaByEmailList(email);
////	            
////	            boolean success = false;
//////
////	            for (Vaga existingCompetencia : existingCompetencias) {
////	                if (existingCompetencia.getCompetencia().equals(competencia)) {
////	                    existingCompetencia.setExperiencia(experiencia);
////	                    success = vagaDAO.apagarVaga(existingCompetencia.getId());
////	                    break;
////	                }
////	            }
//////
////	            if (!success) {
////	                response.put("mensagem", "Erro ao tentar apagar '" + competencia + "'");
////	                response.put("status", 422);
////	                response.put("operacao", "apagarVaga");
////
////	                return response;
////	            }
////	        }
////
////	        response.put("mensagem", "Competencia(s)/Experiencia(s) apagada(s) com sucesso");
////	        response.put("status", 201);
////	        response.put("operacao", "apagarVaga");
////
////	        return response;
////	    }
////
////	    response.put("operacao", "apagarVaga");
////	    response.put("status", 422);
////	    response.put("mensagem", "Sessão inválida");
////
////	    return response;
////    }
//}
