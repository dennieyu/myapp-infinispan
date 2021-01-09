# 다루는 내용
1. 분산 캐시  
`jgroups`  
`UDP`  
`Java Marshalling`  
`클러스터 모니터링 Scheduler`  
`캐시 REST API`  
  
1. SDR  
`이름으로 멤버 조회 시 캐싱 적용`  
`@RepositoryRestResource`
  
1. Swagger  
`http://localhost:8080/swagger-ui/`  

| No | 메소드 | URL |
|:---:|:---:|:---|
| 1 | GET | ​/api​/members |
| 2 | POST | ​/api​/members |
| 3 | GET | /api​/members​/{id} |
| 4 | PUT | /api​/members​/{id} |
| 5 | DELETE | ​/api​/members​/{id} |
| 6 | PATCH | /api​/members​/{id} |
| 7 | GET | /api​/members​/search​/findAllByName |
| 8 | GET | ​/api​/members​/search​/findByName |
  
# 개발 환경
`STS 4.9.0 (2020)`  
`Spring Boot v2.4.1 (2020)`  
`JDK 15 (2020)`  
`infinispan v.11.0.6`  
`Swagger v.3.0.0`  
`Spring-Data-JPA`  
`Spring-Data-REST`  
