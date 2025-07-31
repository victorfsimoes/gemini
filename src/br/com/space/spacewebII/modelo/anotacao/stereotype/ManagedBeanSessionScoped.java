package br.com.space.spacewebII.modelo.anotacao.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

/**
 * Anota��o que define um padr�o (Stereotype) usado para agrupar anota��es comuns
 * aos ManagerBean de sess�o
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
