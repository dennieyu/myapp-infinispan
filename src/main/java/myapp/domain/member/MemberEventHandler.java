package myapp.domain.member;

import org.apache.tomcat.jni.Address;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
public class MemberEventHandler {

	@HandleBeforeSave
	public void handleMemberSave(Member p) {
		// ...
	}

	@HandleBeforeSave
	public void handleAddressSave(Address p) {
		// ...
	}

}
