//package com.spring.drools.api.config;
//
//import java.io.IOException;
//import org.kie.api.KieServices;
//import org.kie.api.builder.KieBuilder;
//import org.kie.api.builder.KieFileSystem;
//import org.kie.api.builder.KieModule;
//import org.kie.api.builder.KieRepository;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//import org.kie.internal.io.ResourceFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//@Configuration
//public class DroolConfig {
//
//	private static final String RULES_PATH = "rules/";
//
//	private final KieServices kieServices = KieServices.Factory.get();
//
//	private KieFileSystem getKieFileSystem() throws IOException {
//		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//		for (Resource file : getRuleFiles()) {
//			kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
//		}
//		return kieFileSystem;
//	}
//
//	private Resource[] getRuleFiles() throws IOException {
//		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//		return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
//	}
//
//	@Bean
//	public KieContainer getKieContainer() throws IOException {
//		System.out.println("Container created...");
//		getKieRepository();
//		KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
//		kb.buildAll();
//		KieModule kieModule = kb.getKieModule();
//		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
//		return kContainer;
//
//	}
//
//	private void getKieRepository() {
//		final KieRepository kieRepository = kieServices.getRepository();
//		kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
//	}
//
//	@Bean
//	public KieSession getKieSession() throws IOException {
//		System.out.println("session created...");
//		return getKieContainer().newKieSession();
//
//	}
//
//}
