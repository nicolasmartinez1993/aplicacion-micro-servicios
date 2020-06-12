package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

//Una de las caractereisticas de Zuul son los filtros, dispone de varias especificacion, 
//en esta clase es Post lo que quiere que se ejecute despues de la ejecucion del servicio.

@Component //Indicamos que es un componente en spring para poder inyectarlo a otros beans
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		//Este objeto lo que ara es indicar la duracion del servicio en atender la peticion.
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Entrando a post filter");
		
		
		Long tiempoInicio = (Long)request.getAttribute("tiempoInicio");
		Long tiempoFinal=System.currentTimeMillis();
		Long tiempoTranscurrido=tiempoFinal - tiempoInicio;
		
		
		log.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTranscurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en milisegundos %s ms.", tiempoTranscurrido));
		
		return null;
	}

	@Override
	public String filterType() {	
		return "post";
	}

	@Override
	public int filterOrder() {
		
		return 1;
	}
	

}
