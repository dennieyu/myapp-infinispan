package myapp.common.cache;

import java.util.concurrent.CountDownLatch;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Listener
public class InfinispanClusterListener {

	CountDownLatch clusterFormedLatch = new CountDownLatch(1);
	CountDownLatch shutdownLatch = new CountDownLatch(1);

	private final int expectedNodes;

	public InfinispanClusterListener(int expectedNodes) {
		this.expectedNodes = expectedNodes;
	}

	@ViewChanged
	public void viewChanged(ViewChangedEvent event) {
		log.info("-> viewChanged={}", event.getNewMembers());
		log.info("-> newMembersSize={}", event.getNewMembers().size());
		log.info("-> oldMembersSize={}", event.getOldMembers().size());

		if (event.getCacheManager().getMembers().size() == expectedNodes) {
			clusterFormedLatch.countDown();
			if (clusterFormedLatch.getCount() == 0) {
				log.info("expected nodes are reached");
			}
		} else if (event.getNewMembers().size() < event.getOldMembers().size()) {
			shutdownLatch.countDown();
			if (shutdownLatch.getCount() == 0) {
				log.info("nodes are reduced after being reached");
			}
		}
	}

}
