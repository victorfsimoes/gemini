package br.com.space.spacewebII.modelo.anotacao.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

/**
 * Anotação que define um padrão (Stereotype) usado para agrupar anotações comuns
 * aos ManagerBean de sessão
 * 
 * @author Desenvolvimento
 * 
 */
@Stereotype
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Named
@SessionScoped
public @interface ManagedBeanSessionScoped {

}
