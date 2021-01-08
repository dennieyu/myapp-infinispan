package myapp.domain.scheduling;

import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheClusterMonitoringJob {

	@Autowired
	CacheManager cacheManager;

	@Scheduled(initialDelay = 60_000, fixedRate = 60_000_000)
	public void cacheManager() throws InterruptedException {
		EmbeddedCacheManager ecm = ((SpringEmbeddedCacheManager) cacheManager).getNativeCacheManager();

		log.info("   clusterName={}", ecm.getClusterName()); // some-cache-cluster
		log.info("        status={}", ecm.getStatus()); // RUNNING
		log.info("       address={}", ecm.getAddress()); // node-1
		log.info("       members={}", ecm.getMembers()); // [node-1, node-2, node-3]
		log.info("    numOfNodes={}", ecm.getHealth().getClusterHealth().getNumberOfNodes()); // 3
	}

}
