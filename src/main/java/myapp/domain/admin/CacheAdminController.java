package myapp.domain.admin;

import java.util.Map;

import org.infinispan.spring.common.provider.SpringCache;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class CacheAdminController {

	@Autowired
	CacheManager cacheManager;

	@RequestMapping(value = "/caches", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCacheNames() {
		return ResponseEntity.ok(cacheManager.getCacheNames());
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/caches/status", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCacheStatus() {
		return ResponseEntity.ok(((SpringEmbeddedCacheManager) cacheManager).getNativeCacheManager().getStats());
	}

	@RequestMapping(value = "/caches/{cacheName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCacheData(@PathVariable("cacheName") String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new ResourceNotFoundException("Cache( " + cacheName + " ) not found.");
		}

		Map<String, Object> entries = Maps.newHashMap();
		SpringCache springCache = ((SpringEmbeddedCacheManager) cacheManager).getCache(cacheName);
		if (springCache != null) {
			springCache.getNativeCache().forEach((o, o2) -> entries.put(String.valueOf(o), o2));
		}

		return ResponseEntity.ok(entries);
	}

	@RequestMapping(value = "/caches/{cacheName}/{cacheKey}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCacheData(@PathVariable("cacheName") String cacheName, @PathVariable("cacheKey") String cacheKey) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new ResourceNotFoundException("Cache( " + cacheName + " ) not found.");
		}

		ValueWrapper valueWrapper = cache.get(cacheKey);
		if (valueWrapper == null) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(valueWrapper);
		}
	}

	@RequestMapping(value = "/caches/{cacheName}/{cacheKey}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteCacheData(@PathVariable("cacheName") String cacheName, @PathVariable("cacheKey") String cacheKey) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new ResourceNotFoundException("Cache( " + cacheName + " ) not found.");
		}

		cache.evict(cacheKey);
		log.info("cacheName({}) - cacheKey({}) is deleted", cacheName, cacheKey);

		return ResponseEntity.noContent().build();
	}

}
