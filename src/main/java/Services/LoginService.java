package Services;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import DAO.CandidatoDAO;
import DAO.EmpresaDAO;

public class LoginService {
	
	private CandidatoDAO candidatoDAO = new CandidatoDAO();
	private EmpresaDAO empresaDAO = new EmpresaDAO();

	public JSONObject login(JSONObject node) throws JSONException {

		JSONObject response = new JSONObject();
	    	
    	if (node.getString("operacao").equals("loginCandidato")) {
    		
	        var candidato = candidatoDAO.getCandidatoByEmail(node.getString("email"));

	        if (!candidato.getEmail().equals(node.getString("email")) || !candidato.getSenha().equals(node.getString("senha"))) {

	        	response.put("mensagem", "Login ou senha incorretos");
	        	response.put("operacao", "loginCandidato");
	        	response.put("status", 401);
	        	
	        	return response;
	        }
	        
	        response.put("token", UUID.randomUUID().toString());
	    	response.put("operacao", "loginCandidato");
	    	response.put("status", 200);
	    	
    	} else if (node.getString("operacao").equals("loginEmpresa")) {
    		
    		var empresa = empresaDAO.getEmpresaByEmail(node.getString("email"));

	        if (!Objects.isNull(empresa)) {
	        	if (!empresa.getEmail().equals(node.getString("email")) || !empresa.getSenha().equals(node.getString("senha"))) {
	        		
	        		response.put("mensagem", "Login ou senha incorretos");
	            	response.put("operacao", "loginEmpresa");
	            	response.put("status", 401);
		        	
		        	return response;
		        }
	        }
        	
        	response.put("token", UUID.randomUUID().toString());
	    	response.put("operacao", "loginEmpresa");
	    	response.put("status", 200);
    	}
	    	
	    	return response;
	}
	
	public JSONObject logout(JSONObject node, HashMap<String, String> session) {
		
		JSONObject response = new JSONObject();
		
		if (!session.get("token").equals(node.getString("token"))) {
			
			response.put("operacao", "logout");
			response.put("status", 401);
			response.put("mensagem", "Token inválido");
			
			return response;
		}
		
		response.put("operacao", "logout");
		response.put("status", 204);
		
		return response;
		
	}
}
