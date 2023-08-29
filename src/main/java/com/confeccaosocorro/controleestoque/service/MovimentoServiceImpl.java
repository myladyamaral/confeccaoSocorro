package com.confeccaosocorro.controleestoque.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.confeccaosocorro.controleestoque.model.Movimento;
import com.confeccaosocorro.controleestoque.repository.MovimentoRepository;
import com.confeccaosocorro.controleestoque.tipo.ReferenciaEnum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MovimentoServiceImpl implements MovimentoService {

	@Autowired
	private MovimentoRepository movimentoRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Movimento> listarTodosMovimentos() {
		return movimentoRepository.findAll();
	}

	@Override
	public Movimento obterMovimentoPorId(Integer id) {
		return movimentoRepository.getReferenceById(id);
	}

	@Override
	public Movimento salvarMovimento(Movimento movimento) {
		return movimentoRepository.saveAndFlush(movimento);
	}


	@Override
	public List<Movimento> buscarMovimentosComFiltros(String produto, String tipoMovimento, String dataEntrada, String dataSaida) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();		
		CriteriaQuery<Movimento> cq = cb.createQuery(Movimento.class);
		Root<Movimento> root = cq.from(Movimento.class);
		
		Predicate produtoPredicate = cb.conjunction();;
		Predicate tipoMovimentoPredicate = cb.conjunction();;
		Predicate dataPredicate = cb.conjunction();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		if (produto!=null && !produto.isEmpty()) {
			produtoPredicate = cb.like(root.get("produto").get("descricao"), "%"+produto+"%");
		}
		if (tipoMovimento!=null && !tipoMovimento.isEmpty()) {
			tipoMovimentoPredicate = cb.equal(root.get("tipoMovimento"), tipoMovimento);
		}
		
		if (dataEntrada!=null && dataSaida!=null 
				&& !dataEntrada.isEmpty() && !dataSaida.isEmpty()) {
			LocalDate date = LocalDate.parse(dataEntrada, formatter);
			Predicate dataEntradaPredicate = cb.greaterThanOrEqualTo(root.get("dataMovimento"), date);
			date = LocalDate.parse(dataSaida, formatter);
		    Predicate dataSaidaPredicate = cb.lessThanOrEqualTo(root.get("dataMovimento"), date);
			dataPredicate = cb.and(dataEntradaPredicate, dataSaidaPredicate);
		}
		else if (dataEntrada!=null &&!dataEntrada.isEmpty()) {
			LocalDate date = LocalDate.parse(dataEntrada, formatter);
			dataPredicate = cb.equal(root.get("dataMovimento"), date);
		}
		else if (dataSaida!=null &&!dataSaida.isEmpty()) {
			
			LocalDate date = LocalDate.parse(dataSaida, formatter);
			dataPredicate = cb.equal(root.get("dataMovimento"), date);
		}
		
		cq.where(cb.and(produtoPredicate,tipoMovimentoPredicate, dataPredicate));
		cq.select(root)
	      .orderBy(cb.desc(root.get("dataMovimento")));
		
		TypedQuery<Movimento> query = entityManager.createQuery(cq);

		return query.getResultList();

	}

	@Override
	public List<Movimento> gerarRelatorioMovimentos(String filtro, Integer referencia) {
		String hql =  "SELECT m FROM Movimento m WHERE 1=1 ";
		List<Movimento> movimentos = new ArrayList<Movimento>();
		if(filtro!=null && !filtro.isEmpty()) {
			if (referencia.equals(ReferenciaEnum.DIA.getId())) {
				hql+="AND EXTRACT(day from m.dataMovimento) = :filtro";		
			}else if (referencia.equals(ReferenciaEnum.MES.getId())) {
				hql+="AND EXTRACT(month from m.dataMovimento) = :filtro";
			}else if (referencia.equals(ReferenciaEnum.ANO.getId())) {
				hql+="AND EXTRACT(year from m.dataMovimento) = :filtro";
			}
			TypedQuery<Movimento> query = entityManager.createQuery(hql, Movimento.class);
			query.setParameter("filtro", filtro);
			movimentos = query.getResultList();
		}
		
		return movimentos;
	}

}
