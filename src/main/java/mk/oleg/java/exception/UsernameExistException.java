package mk.oleg.java.exception;

public class UsernameExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	public UsernameExistException(String username) {
		
		this.username = username;
	}

}
