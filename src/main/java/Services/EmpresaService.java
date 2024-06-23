package Services;

import java.util.Objects;
import java.util.UUID;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import DAO.EmpresaDAO;
import Model.Empresa;

public class EmpresaService {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    public JSONObject cadastarEmpresa(JSONObject node) throws JsonProcessingException {
    	
    	try {
    		
	    	JSONObject response = new JSONObject();
	        Empresa empresa = new Empresa();
	        
	        empresa.setRazaoSocial(node.getString("razaoSocial"));
	        empresa.setEmail(node.getString("email"));
	        empresa.setSenha(node.getString("senha"));
	        empresa.setCnpj(node.getString("cnpj"));
	        empresa.setDescricao(node.getString("descricao"));
	        empresa.setRamo(node.getString("ramo"));

	        if (!Objects.isNull(empresaDAO.getEmpresaByEmail(node.getString("email")))) {
	        	
	        	response.put("operacao", "cadastrarEmpresa");
	        	response.put("status", 422);
	        	response.put("mensagem", "E-mail já cadastrado");
	        	
	        	return response;
	        }
	        
	        empresaDAO.createEmpresa(empresa);
        	
        	response.put("operacao", "cadastrarEmpresa");
        	response.put("status", 201);
        	response.put("token", UUID.randomUUID());
        	
        	return response;
        	
        } catch (Exception e) {
        	
        	JSONObject response = new JSONObject();
        	
        	response.put("operacao", "cadastrarEmpresa");
        	response.put("status", 404);
        	response.put("mensagem", "Erro ao cadastrar empresa");
        	e.printStackTrace();
        	
        	return response;
        	
        }

        
    }
    
    public JSONObject getEmpresaByEmail(JSONObject node) throws JsonProcessingException {
    	JSONObject response = new JSONObject();
    	
    	if (node.getString("token") != null) {
    		Empresa empresa = empresaDAO.getEmpresaByEmail(node.getString("email"));

            if (Objects.isNull(empresa)) {
            	response.put("mensagem", "E-mail não encontrado");
            	response.put("status", 404);
            	response.put("operacao", "visualizarEmpresa");
                
                return response;
            }

            response.put("razaoSocial", empresa.getRazaoSocial());
            response.put("email", empresa.getEmail());
            response.put("senha", empresa.getSenha());
            response.put("cnpj", empresa.getCnpj());
            response.put("descricao", empresa.getDescricao());
            response.put("ramo", empresa.getRamo());
            response.put("status", 201);
            response.put("operacao", "visualizarEmpresa");
            
            return response;

    	}
    	
    	response.put("operacao", "visualizarEmpresa");
    	response.put("status", 404);
    	response.put("mensagem", "Sessão inválida");
    	
    	return response;
        
    }

    public JSONObject atualizarEmpresa(JSONObject node) {
    	JSONObject response = new JSONObject();
    	
        if (node.getString("token") != null) {
        	var empresa = empresaDAO.getEmpresaByEmail(node.getString("email"));

            if (Objects.isNull(empresa)) {
            	
                response.put("mensagem", "E-mail não encontrado");
                response.put("status", 404);
                response.put("operacao", "atualizarEmpresa");
                
                return response;
            }
            
            Empresa novaEmpresa = new Empresa();
            
            novaEmpresa.setRazaoSocial(node.getString("razaoSocial"));
            novaEmpresa.setEmail(node.getString("email"));
            novaEmpresa.setSenha(node.getString("senha"));
            novaEmpresa.setCnpj(node.getString("cnpj"));
            novaEmpresa.setDescricao(node.getString("descricao"));
            novaEmpresa.setRamo(node.getString("ramo"));
            
            empresaDAO.atualizarEmpresa(node.getString("email"), novaEmpresa);

            response.put("status", 201);
            response.put("operacao", "atualizarEmpresa");
            
            return response;
        }
        
        response.put("operacao", "atualizarEmpresa");
    	response.put("status", 404);
    	response.put("mensagem", "Sessão inválida");
    	
    	return response;
    }

    public JSONObject apagarEmpresa(JSONObject node) throws JsonProcessingException {
    	JSONObject response = new JSONObject();
    	
    	if (node.getString("token") != null) {
            Empresa empresa = empresaDAO.getEmpresaByEmail(node.getString("email"));

            if (Objects.isNull(empresa)) {
            	
            	response.put("mensagem", "E-mail não encontrado");
                response.put("status", 404);
                response.put("operacao", "atualizarCandidato");
                
                return response;
            }

            empresaDAO.apagarEmpresa(node.getString("email"));

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
