package edu.ubbcluj.backend.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;

public class HibernateDAO<T> {
	
	@SuppressWarnings("unchecked")
	public List<T> getQueryResult(final Criteria q) {
		final List<T> list = q.list();
		return list;
	}
}
