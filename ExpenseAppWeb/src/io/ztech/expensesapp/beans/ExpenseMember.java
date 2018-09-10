package io.ztech.expensesapp.beans;

public class ExpenseMember extends User {
	boolean pending;
	float totalAmount;
	float amountPaid;

	

		public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isPending() {
		return pending;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}
	
}
