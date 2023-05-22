package beans;

public class User {
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String bio;
	private int score ;
	private int nbPartie ;

	private String image;
	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public User() {
		
	}
	
	

	public User(String username, String email, String password, String firstName, String lastName, String bio, int score  ) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.score=score ;
		this.bio = bio;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public int getNbPartie() {
		return nbPartie;
	}



	public void setNbPartie(int nbPartie) {
		this.nbPartie = nbPartie;
	}

	
	
}
