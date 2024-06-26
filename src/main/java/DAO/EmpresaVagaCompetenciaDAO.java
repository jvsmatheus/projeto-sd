//package DAO;
//
//import Model.EmpresaVagaCompetencia;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class EmpresaVagaCompetenciaDAO {
//
//    private Connection connection;
//
//    public EmpresaVagaCompetenciaDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    public void addEmpresaVagaCompetencia(EmpresaVagaCompetencia empresaVagaCompetencia) throws SQLException {
//        String sql = "INSERT INTO empresa_vaga_competencia (empresa_id, vaga_id) VALUES (?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, empresaVagaCompetencia.getEmpresaId());
//            stmt.setLong(2, empresaVagaCompetencia.getVagaId());
//            stmt.executeUpdate();
//        }
//    }
//
//    public EmpresaVagaCompetencia getEmpresaVagaCompetencia(int empresaId, long vagaId) throws SQLException {
//        String sql = "SELECT * FROM empresa_vaga_competencia WHERE empresa_id = ? AND vaga_id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, empresaId);
//            stmt.setLong(2, vagaId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return new EmpresaVagaCompetencia(rs.getInt("empresa_id"), rs.getLong("vaga_id"));
//                }
//            }
//        }
//        return null;
//    }
//
//    // Métodos adicionais para atualizar e excluir podem ser adicionados aqui
//}
