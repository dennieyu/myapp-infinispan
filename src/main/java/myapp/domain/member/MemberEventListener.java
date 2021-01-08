package myapp.domain.member;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

public class MemberEventListener extends AbstractRepositoryEventListener<Member> {

	@Override
	public void onBeforeSave(Member member) {
		// ...
	}

	@Override
	public void onAfterDelete(Member member) {
		// ...
	}

}
