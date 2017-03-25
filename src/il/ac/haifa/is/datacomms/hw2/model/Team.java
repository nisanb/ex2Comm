package il.ac.haifa.is.datacomms.hw2.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Class representation of a team in the amazing race.
 */
public final class Team implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//-------------------------------------------------------------------
	//-----------------------------fields--------------------------------
	//-------------------------------------------------------------------

	//**team's id.*/
	private final int teamId;
	
	/**team's login password.*/
	private String password;
	
	/**team's current budget.*/
	private BigDecimal budget;

	//-------------------------------------------------------------------
	//-------------------------constructors------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @param id team's id.
	 */
	protected Team(int id) {
		teamId = id;
	}
	
	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	
	/**
	 * spend given sum of team's budget.
	 * @param sum sum to be spent.
	 * @return reference to this instance.
	 */
	protected void spendMoney(BigDecimal sum) {
		budget = budget.subtract(sum);
	}
	
	//-------------------------------------------------------------------
	//----------------------------utility--------------------------------
	//-------------------------------------------------------------------	
	
	/**
	 * @param sum sum to be tested.
	 * @return true if can afford, false otherwise.
	 */
	protected boolean canAfford(BigDecimal sum) {
		return (budget.compareTo(sum) >= 0);
	}
	
	//-------------------------------------------------------------------
	//----------------------------getters--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @return team's id.
	 */
	public int getId() {
		return teamId;
	}
	
	/**
	 * @return team's password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return team's budget.
	 */
	public BigDecimal getBudget() {
		return budget;
	}
	
	//-------------------------------------------------------------------
	//----------------------------setters--------------------------------
	//-------------------------------------------------------------------
	
	/**
	 * @param password new password.
	 * @return reference to this instance.
	 */
	protected Team setPassword(String password) {
		if (password == null)
			throw new NullPointerException();
		this.password = password;
		return this;
	}
	
	/**
	 * important: use spendMoney for altering budget after object creation.
	 * @param budget budget to be set. 
	 * @return reference to this instance;
	 */
	protected Team setBudget(BigDecimal budget) {
		if (this.budget != null)
			throw new UnsupportedOperationException();
		if (budget == null)
			throw new NullPointerException();
		this.budget = budget;
		return this;
	}
	
	//-------------------------------------------------------------------
	//---------------------------overrides-------------------------------
	//-------------------------------------------------------------------
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		final Team other = (Team) obj;
		return (other.teamId == this.teamId);
	}
	
	@Override
	public String toString() {
		return String.format("Team [ id=%d, password=%s, budget=%s ]\n", 
				teamId, (password != null) ? password : "N/A", 
				(budget != null) ? NumberFormat.getCurrencyInstance().format(budget) : "N/A");
	}
}
