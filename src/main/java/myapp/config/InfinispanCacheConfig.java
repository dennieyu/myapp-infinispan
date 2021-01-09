package myapp.config;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myapp.common.cache.CacheListener;
import myapp.common.cache.InfinispanClusterListener;

@EnableCaching
@Configuration
public class InfinispanCacheConfig {

	@Value("${myapp.cache.configFile:default-configs/default-jgroups-udp.xml}")
	String configFile;

	@Bean
	public SpringEmbeddedCacheManager springEmbeddedCacheManager() {
		return new SpringEmbeddedCacheManager(infinispanCacheManager());
	}

	@SuppressWarnings("deprecation")
	private EmbeddedCacheManager infinispanCacheManager() {
		// embedded cluster setting
		GlobalConfigurationBuilder globalBuilder = GlobalConfigurationBuilder.defaultClusteredBuilder();
		globalBuilder.clusteredDefault()
				.transport().defaultTransport()
				.addProperty("configurationFile", configFile)
				.clusterName("myapp-cluster-2020")
				.defaultCacheName("default-cache")
				.serialization()
				.marshaller(new JavaSerializationMarshaller())
				.whiteList()
				.addRegexps("myapp.domain.member.");

		ConfigurationBuilder config = new ConfigurationBuilder();
		config.expiration().lifespan(10, TimeUnit.SECONDS); // 10초
		config.clustering().cacheMode(CacheMode.DIST_SYNC);

		DefaultCacheManager cacheManager = new DefaultCacheManager(globalBuilder.build(), config.build());
		cacheManager.addListener(new InfinispanClusterListener(2));

		return cacheManager;
	}

	@Bean("memberCache")
	public Cache<String, String> tokenCache(SpringEmbeddedCacheManager springEmbeddedCacheManager) {
		ConfigurationBuilder config = new ConfigurationBuilder();
		config.expiration().lifespan(1, TimeUnit.MINUTES); // 1분
		config.clustering().cacheMode(CacheMode.DIST_SYNC);

		EmbeddedCacheManager cacheManager = springEmbeddedCacheManager.getNativeCacheManager();
		cacheManager.defineConfiguration("member-cache", config.build());
		Cache<String, String> tokenCache = cacheManager.getCache("member-cache");
		tokenCache.addListener(new CacheListener());

		return tokenCache;
	}

}
