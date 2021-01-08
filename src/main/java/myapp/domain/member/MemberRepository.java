package myapp.domain.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;

@CacheConfig(cacheNames = "member-cache")
@RepositoryRestResource
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Cacheable(key = "#name")
	Member findByName(@Param("name") String name);

	Page<Member> findAllByName(@Param("name") String name, Pageable pageable);

}

/* @formatter:off */
@Projection(name = "only-name", types = {Member.class})
interface OnlyName {

    String getName();

}

@Projection(name = "how-old", types = { Member.class })
interface HowOldProjection {
	
  Integer getAge();
  
}

@Projection(name = "brief", types = { Member.class })
interface BriefProjection {

  @Value("name=#{target.name}, age=#{target.age}") 
  String getBrief();
  
}
/* @formatter:on */
