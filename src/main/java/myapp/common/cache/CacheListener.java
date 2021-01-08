package myapp.common.cache;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntriesEvicted;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryActivated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryExpired;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryLoaded;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryPassivated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryVisited;
import org.infinispan.notifications.cachelistener.event.CacheEntriesEvictedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryActivatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryExpiredEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryLoadedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryPassivatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryVisitedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Listener
public class CacheListener {

	@CacheEntryCreated
	public void entryCreated(CacheEntryCreatedEvent<String, String> event) {
		this.printLog("Add key '" + event.getKey() + "' to cache", event);
	}

	@CacheEntryExpired
	public void entryExpired(CacheEntryExpiredEvent<String, String> event) {
		this.printLog("Expire key '" + event.getKey() + "' from cache", event);
	}

	@CacheEntryVisited
	public void entryVisited(CacheEntryVisitedEvent<String, String> event) {
		this.printLog("Key '" + event.getKey() + "' was visited", event);
	}

	@CacheEntryActivated
	public void entryActivated(CacheEntryActivatedEvent<String, String> event) {
		this.printLog("Activate key '" + event.getKey() + "' on cache", event);
	}

	@CacheEntryPassivated
	public void entryPassivated(CacheEntryPassivatedEvent<String, String> event) {
		this.printLog("Passivate key '" + event.getKey() + "' from cache", event);
	}

	@CacheEntryLoaded
	public void entryLoaded(CacheEntryLoadedEvent<String, String> event) {
		this.printLog("Load key '" + event.getKey() + "' to cache", event);
	}

	@CacheEntriesEvicted
	public void entriesEvicted(CacheEntriesEvictedEvent<String, String> event) {
		StringBuilder builder = new StringBuilder();
		event.getEntries().forEach((key, value) -> builder.append(key).append(", "));
		log.info("Evict following entries from cache: " + builder.toString());
	}

	private void printLog(String s, CacheEntryEvent event) {
		if (!event.isPre()) {
			log.info(s);
		}
	}

}
