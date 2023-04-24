package com.madeira.pagamentos.repositories;

import com.madeira.pagamentos.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepositoy extends JpaRepository<Pagamento, Long> {
}
