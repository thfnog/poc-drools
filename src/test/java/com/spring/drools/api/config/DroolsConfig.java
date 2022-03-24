package com.spring.drools.api.config;

import java.io.IOException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class DroolsConfig {

	private static final String RULES_PATH = "rules/";
	private static final String ENCODING = "UTF-8";
	private static final String REGEX = "**/*.*";
	private static final String CLASSPATH = "classpath*:";

	private static final String CONTAINER_CREATED = "Container created...";
	private static final String SESSION_CREATED = "Session created...";

	private final KieServices kieServices = KieServices.Factory.get();

	@Bean
	public KieContainer getKieContainer() throws IOException {
		System.out.println(CONTAINER_CREATED);
		getKieRepository();
		KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
		kb.buildAll();
		KieModule kieModule = kb.getKieModule();
		return kieServices.newKieContainer(kieModule.getReleaseId());

	}

	@Bean
	public KieSession getKieSession() throws IOException {
		System.out.println(SESSION_CREATED);
		return getKieContainer().newKieSession();

	}

	private KieFileSystem getKieFileSystem() throws IOException {
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		for (Resource file : getRuleFiles()) {
			kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(),
					ENCODING));
		}
		return kieFileSystem;
	}

	private Resource[] getRuleFiles() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		return resourcePatternResolver.getResources(CLASSPATH + RULES_PATH + REGEX);
	}

	private void getKieRepository() {
		final KieRepository kieRepository = kieServices.getRepository();
		kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
	}

}
