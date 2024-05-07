package com.viewnext.batchexcel.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

import com.viewnext.batchexcel.model.Producto;

@Component
public class Writer {

	/**
	 * Es para hacer un writer de producto para hacerlo en csv
	 * 
	 * @return un FlatFileItemWriter<Producto>
	 */
	@Bean
	public FlatFileItemWriter<Producto> stockWriter() {

		FlatFileItemWriter<Producto> writer = new FlatFileItemWriter<>();
		writer.setShouldDeleteIfExists(true);
		writer.setEncoding("UTF-8");
		writer.setResource(new PathResource("terminalesNuevo.csv"));
		writer.setAppendAllowed(true);
		DelimitedLineAggregator<Producto> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(";");
		BeanWrapperFieldExtractor<Producto> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[] { "id", "name", "descripcion", "code" });
		aggregator.setFieldExtractor(extractor);
		writer.setLineAggregator(aggregator);
		return writer;

	}

}
