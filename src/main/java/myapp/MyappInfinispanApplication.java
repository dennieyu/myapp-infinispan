package myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import myapp.domain.member.Member;
import myapp.domain.member.MemberRepository;

@SpringBootApplication
public class MyappInfinispanApplication implements CommandLineRunner {

	@Autowired
	private MemberRepository memberRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyappInfinispanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		memberRepository.save(Member.join("member1", 20));
		memberRepository.save(Member.join("member2", 21));
		memberRepository.save(Member.join("member3", 22));
		memberRepository.save(Member.join("member4", 23));
		memberRepository.save(Member.join("member5", 24));
	}

}
