package br.com.space.spacewebII.controle.aplicacao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.space.api.core.util.StringUtil;

public class MensagemSistema {

	/**
	 * 
	 * @author Ronie
	 * 
	 */
	public enum TipoMensagem {
		Erro, Alerta, Info, Fatal
	}

	public static boolean mensagemJaAdcionada(String mensagem) {

		List<FacesMessage> facesMessages = FacesContext.getCurrentInstance()
				.getMessageList();

		for (FacesMessage facesMessage : facesMessages) {

			if (facesMessage.getDetail().equals(mensagem)) {

				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param tipoMensagen
	 * @param titulo
	 * @param mensagem
	 */
	public static void exibir(TipoMensagem tipoMensagen, String titulo,
			String mensagem) {

		if ((StringUtil.isValida(mensagem) || StringUtil.isValida(titulo))
				&& !mensagemJaAdcionada(mensagem)) {

			Severity severity = null;

			switch (tipoMensagen) {
			case Erro:
				severity = FacesMessage.SEVERITY_ERROR;
				break;

			case Info:
				severity = FacesMessage.SEVERITY_INFO;
				break;

			case Fatal:
				severity = FacesMessage.SEVERITY_FATAL;
				break;

			case Alerta:
				severity = FacesMessage.SEVERITY_WARN;
				break;

			default:
				break;
			}

			FacesContext context = FacesContext.getCurrentInstance();

			if (context != null && severity != null) {
				if (severity == FacesMessage.SEVERITY_ERROR
						|| severity == FacesMessage.SEVERITY_FATAL
						|| severity == FacesMessage.SEVERITY_WARN) {
					context.validationFailed();
				}

				if (titulo == null) {
					titulo = "";
				}

				if (mensagem == null) {
					mensagem = "";
				}

				context.addMessage(null, new FacesMessage(severity, titulo,
						mensagem));

			}
		}
	}
}
