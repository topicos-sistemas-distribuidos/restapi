package br.ufc.great.es.api.demo.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.great.es.api.demo.model.AbstractModel;

import java.io.Serializable;
import java.util.List;

/**
 * Classe abstrata base para a criação dos serviços manipulação dos repositórios de dados da aplicação
 * @author armandosoaressousa
 *
 * @param <T> tipo da Classe 
 * @param <Long> Chave primária da classe
 */
public abstract class AbstractService<T extends AbstractModel<Long>, Long extends Serializable> {

    private static final int PAGE_SIZE = 5;
    protected abstract JpaRepository<T, Long> getRepository();

    public Page<T> getList(Integer pageNumber) {
        PageRequest pageRequest =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "id");

        return getRepository().findAll(pageRequest);
    }
    
    public List<T> getListAll(){
    	return getRepository().findAll();
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public T get(Long id) {
        T entity = getRepository().findOne(id);
        return entity;
    }

    public void delete(Long id) {
        try {
            getRepository().delete(id);
        } catch (EmptyResultDataAccessException e) {}
    }

    public void update(T entity) {
        T getEntity = getRepository().findOne(entity.getId());
        getRepository().save(entity);
    }

}