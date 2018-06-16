package br.com.rbaselio.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:pedidos?delay=5s&noop=true").
				split().xpath("/pedido/itens/item").
				filter().xpath("/item/formato[text()='EBOOK']").
				marshal().xmljson().
				setHeader("CamelFileName", simple("${file:name.noext}.json")).
				to("file:saida");
			}
		});

		context.start(); // aqui camel realmente começa a trabalhar
		Thread.sleep(20000); // esperando um pouco para dar um tempo para camel
		context.stop();

	}
}
