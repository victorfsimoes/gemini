package br.com.space.spacewebII.modelo.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD,
		ElementType.PACKAGE, ElementType.CONSTRUCTOR,
		ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjetoPesquisaSelecionado {

}
